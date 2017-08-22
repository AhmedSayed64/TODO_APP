package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import Model.myTodo;


public class DBHandler extends SQLiteOpenHelper {

    ArrayList<myTodo> todoArrayList = new ArrayList<>();

    public DBHandler(Context context) {
        super(context, data_Constants.DATABASE_NAME, null, data_Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + data_Constants.TABLE_NAME +
                "(" + data_Constants.KEY_ID + " INTEGER PRIMARY KEY, " + data_Constants.TITLE_NAME + " TEXT," +
                data_Constants.CONTENT_NAME + " TEXT," +
                data_Constants.DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop = "DROP TABLE IF EXISTS" + data_Constants.TABLE_NAME;
        db.execSQL(drop);
        onCreate(db);
    }

    public void add_Todo(myTodo todo) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(data_Constants.TITLE_NAME, todo.getTitle());
        values.put(data_Constants.CONTENT_NAME, todo.getContent());
        values.put(data_Constants.DATE_NAME, System.currentTimeMillis());

        db.insert(data_Constants.TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<myTodo> get_todos() {

        todoArrayList.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(data_Constants.TABLE_NAME, new String[]{data_Constants.KEY_ID,
                        data_Constants.TITLE_NAME, data_Constants.CONTENT_NAME, data_Constants.DATE_NAME}, null, null,
                null, null, data_Constants.DATE_NAME + " DESC");


        if (cursor.moveToFirst()) {

            do {
                myTodo db_todo = new myTodo();

                db_todo.setTitle(cursor.getString(cursor.getColumnIndex(data_Constants.TITLE_NAME)));
                db_todo.setContent(cursor.getString(cursor.getColumnIndex(data_Constants.CONTENT_NAME)));
                db_todo.setMy_id(cursor.getInt(cursor.getColumnIndex(data_Constants.KEY_ID)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(data_Constants.DATE_NAME))));

                db_todo.setRecordDate(date);

                todoArrayList.add(db_todo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todoArrayList;
    }

    public void delete_wish(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(data_Constants.TABLE_NAME, data_Constants.KEY_ID + " = ? ", new String[]{String.valueOf(id)});

        db.close();
    }
}
