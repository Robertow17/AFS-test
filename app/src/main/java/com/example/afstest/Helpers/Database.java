package com.example.afstest.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "tasks_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    private static final String COL2 = "status";


    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + " ("
                        +"ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                        +COL1 +" TEXT, "
                        +COL2 + " INTEGER);";
        db.execSQL(createTable);

        String taskName = "Task";
        String openStatus = "0";
        for (int i=1; i<25; i++){

            String fillTable = "INSERT INTO " + TABLE_NAME + " (" + COL1 +", "+COL2+") " +
                    "VALUES ('" + taskName+" "+i+ "','"+openStatus+"')";
            db.execSQL(fillTable);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateStatus(int newStatus, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newStatus + "' WHERE " + COL0 + " = '" + id + "'";

        db.execSQL(query);
    }
}
