package com.example.alexundr64.tensorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexundr64 on 17.07.2015.
 */
public class TableControllerPeople extends DatabaseHandler {

    public TableControllerPeople(Context context) {
        super(context);
    }

    public boolean create(ObjectPeople objectPeople) {
        ContentValues values = new ContentValues();
        values.put("firstname", objectPeople.Firstname);
        values.put("lastname", objectPeople.Lastname);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("peoples", null, values) > 0;
        db.close();
        return createSuccessful;
    }
    /*public boolean eraseAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean eraseSuccessful = db.delete("peoples", null, null) > 0;
        b.delete("peoples", null, null);                                        //удаляет всю таблицу
        db.close();
        return eraseSuccessful;
    }*/

    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM peoples";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }

    public List<ObjectPeople> read() {
        List<ObjectPeople> recordsList = new ArrayList<ObjectPeople>();
        String sql = "SELECT * FROM peoples ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String studentFirstname = cursor.getString(cursor.getColumnIndex("firstname"));
                String studentEmail = cursor.getString(cursor.getColumnIndex("lastname"));
                ObjectPeople objectPeople = new ObjectPeople();
                objectPeople.id = id;
                objectPeople.Firstname = studentFirstname;
                objectPeople.Lastname = studentEmail;
                recordsList.add(objectPeople);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordsList;
    }

    public ObjectPeople readSingleRecord(int studentId) {

        ObjectPeople objectPeople = null;

        String sql = "SELECT * FROM peoples WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String firstname = cursor.getString(cursor.getColumnIndex("firstname"));
            String email = cursor.getString(cursor.getColumnIndex("lastname"));

            objectPeople = new ObjectPeople();
            objectPeople.id = id;
            objectPeople.Firstname = firstname;
            objectPeople.Lastname = email;

        }

        cursor.close();
        db.close();

        return objectPeople;

    }

    public boolean update(ObjectPeople objectPeople) {

        ContentValues values = new ContentValues();

        values.put("firstname", objectPeople.Firstname);
        values.put("lastname", objectPeople.Lastname);

        String where = "id = ?";

        String[] whereArgs = {Integer.toString(objectPeople.id)};

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("peoples", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(String id) {
        boolean deleteSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("peoples", "id ='" + id + "'", null) > 0;
        db.close();
        return deleteSuccessful;

    }


}