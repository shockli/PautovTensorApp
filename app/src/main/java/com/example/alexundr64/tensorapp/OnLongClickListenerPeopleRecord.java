package com.example.alexundr64.tensorapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Alexundr64 on 18.07.2015.
 */
public class OnLongClickListenerPeopleRecord implements View.OnLongClickListener {
    Context context;
    String id;
    final CharSequence[] items = { "Редактировать", "Удалить" };
    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();

        TableControllerPeople tempTableControllerPeople = new TableControllerPeople(context);
        ObjectPeople objectPeople = tempTableControllerPeople.readSingleRecord(Integer.parseInt(id));
        String name = objectPeople.Firstname;
        String lastname = objectPeople.Lastname;

        new AlertDialog.Builder(context).setTitle(name + " " + lastname)
        .setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {
                    editRecord(Integer.parseInt(id));

                } else if (item == 1) {
                    boolean deleteSuccessful = new TableControllerPeople(context).delete(id);
                    if (deleteSuccessful) {
                        Toast.makeText(context, "Запись удалена!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Запись удалить не удалось (", Toast.LENGTH_SHORT).show();
                    }

                    ((MainActivity) context).countRecords();
                    ((MainActivity) context).readRecords();
                }
                dialog.dismiss();
            }
        }).show();

        return false;
    }

    public void editRecord(final int PeopleId) {
        final TableControllerPeople tableControllerPeople = new TableControllerPeople(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.input_form, null, false);
        final EditText editTextStudentFirstname = (EditText) formElementsView.findViewById(R.id.editTextPeopleFirstname);
        final EditText editTextStudentEmail = (EditText) formElementsView.findViewById(R.id.editTextPeopleLastname);
        ObjectPeople objectPeople = tableControllerPeople.readSingleRecord(PeopleId);
        editTextStudentFirstname.setText(objectPeople.Firstname);
        editTextStudentEmail.setText(objectPeople.Lastname);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Редактировать запись")
                .setPositiveButton("Сохранить изменения",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ObjectPeople objectPeople = new ObjectPeople();
                                objectPeople.id = PeopleId;
                                objectPeople.Firstname = editTextStudentFirstname.getText().toString();
                                objectPeople.Lastname = editTextStudentEmail.getText().toString();
                                boolean updateSuccessful = tableControllerPeople.update(objectPeople);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Запись обновлена!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Запись обновить не удалось(", Toast.LENGTH_SHORT).show();
                                }
                                ((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();
                                dialog.cancel();
                            }

                        }).show();
    }
}
