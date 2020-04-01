package com.manyong.smarttrashcan_final;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;

public class SecondTab extends AppCompatActivity {
    dbHelper helper;
    SQLiteDatabase sql_db;
    TextView text_id, name, name_first, name_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second_tab);

        helper = new dbHelper(this);
        try {
            sql_db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            sql_db = helper.getReadableDatabase();
        }
        MyInfo();
    }

    public void MyInfo() {
        My_id my_id = (My_id) getApplication();

        Cursor cursor, cursor1, cursor2;
        name = (TextView) findViewById(R.id.name);
        text_id = (TextView) findViewById(R.id.id);
        name_first = (TextView) findViewById(R.id.name_first);
        name_last = (TextView) findViewById(R.id.name_last);

        String My_name = name.getText().toString();
        String My_id = "";
        String select_id = "";
        select_id = my_id.getMain_id();
        String My_tname_first = name_first.getText().toString();
        String My_ttime_first = "";
        String My_tname_last = name_last.getText().toString();
        String My_ttime_last = "";
        cursor = sql_db.rawQuery("SELECT id, name FROM member where id = '" + select_id + "';", null);
        while (cursor.moveToNext()) {
            My_id = cursor.getString(0);
            My_name = cursor.getString(1);
        }

        cursor1 = sql_db.rawQuery("SELECT tname ,ttime FROM trash ORDER BY ttime desc;", null);
        while (cursor1.moveToNext()) {
            My_tname_first = cursor1.getString(0);
            My_ttime_first = cursor1.getString(1);
        }

        cursor2 = sql_db.rawQuery("SELECT tname ,ttime FROM trash ORDER BY ttime asc;", null);
        while (cursor2.moveToNext()) {
            My_tname_last = cursor2.getString(0);
            My_ttime_last = cursor2.getString(1);
        }

        text_id.setText(My_id);
        name.setText(My_name);
        name_first.setText(My_tname_first + " \n " + My_ttime_first);  //My_ttime_first);
        name_last.setText(My_tname_last + " \n " + My_ttime_last);
    }
}

