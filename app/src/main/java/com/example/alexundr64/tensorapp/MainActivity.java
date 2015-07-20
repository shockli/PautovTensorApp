package com.example.alexundr64.tensorapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Alexundr64 on 16.07.2015.
 */

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton buttonCreateLocation = (ImageButton) findViewById(R.id.imageButton);
        buttonCreateLocation.setOnClickListener(new OnClickListenerCreatePeoples());
        countRecords();
        readRecords();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void countRecords() {
        int recordCount = new TableControllerPeople(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText("количество записей - " + recordCount);

    }

    public class OnClickListenerCreatePeoples implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final Context context = view.getContext();     //контекст приложения
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   //Inflate the input_form.xml
            final View formElementsView = inflater.inflate(R.layout.input_form, null, false);                       //Inflate the input_form.xml

            final EditText editTextPeoplesFirstname = (EditText) formElementsView.findViewById(R.id.editTextPeopleFirstname);
            final EditText editTextPeoplesEmail = (EditText) formElementsView.findViewById(R.id.editTextPeopleLastname);


            new AlertDialog.Builder(context)
                    .setView(formElementsView)
                    .setTitle("Создать запись")
                    .setPositiveButton("Добавить",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String Firstname = editTextPeoplesFirstname.getText().toString();
                                    String LastName = editTextPeoplesEmail.getText().toString();
                                    ObjectPeople objectPeople = new ObjectPeople();
                                    objectPeople.Firstname = Firstname;
                                    objectPeople.Lastname = LastName;
                                    boolean createSuccessful = new TableControllerPeople(context).create(objectPeople);
                                  //boolean eraseSuccessful = new TableControllerPeople(context).eraseAll();
                                    if(createSuccessful){
                                        Toast.makeText(context, "Информация была сохранена.", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, "Не удалось сохранить информацию.", Toast.LENGTH_SHORT).show();
                                    }
                                    countRecords(); //для обновления записи количества профайлов
                                    ((MainActivity) context).readRecords();

                                    dialog.cancel();
                                }
                            }).show();

        }
    }

    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();
        List<ObjectPeople> peoples = new TableControllerPeople(this).read();

        if (peoples.size() > 0) {
            for (ObjectPeople obj : peoples) {
                int id = obj.id;
                String firstname = obj.Firstname;
                String lastname = obj.Lastname;
                String textViewContents = firstname + " - " + lastname;
                TextView textViewLocationItem = new TextView(this);
                textViewLocationItem.setOnLongClickListener(new OnLongClickListenerPeopleRecord());
                textViewLocationItem.setPadding(0, 10, 0, 10);
                textViewLocationItem.setText(textViewContents);
                textViewLocationItem.setTag(Integer.toString(id));
                linearLayoutRecords.addView(textViewLocationItem);
            }
        }
        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("Записей не обнаружено");

            linearLayoutRecords.addView(locationItem);
        }

    }
}
