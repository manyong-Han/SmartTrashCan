package com.manyong.smarttrashcan_final;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ain53 on 2017-06-09.
 */

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        // 2초 후 사용자 등록 액티비티 실행
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean check_db = check_DB();

                if(check_db == false) { // db에 정보가 없는 경우
                    Intent intent = new Intent(IntroActivity.this, JoinActivity.class);
                    startActivity(intent);
                    finish();
                }
                else { // db에 정보가 있는 경우
                    Intent intent = new Intent(IntroActivity.this, LogActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    boolean check_DB() {
        //디비 체크
        dbHelper helper;
        SQLiteDatabase sql_db;
        helper = new dbHelper(this);
        try {
            sql_db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            sql_db = helper.getReadableDatabase();
        }

        Cursor cursor;
        cursor = sql_db.rawQuery("Select * from member", null);
        while (cursor.moveToNext()) {
            if (cursor != null) {
                return true;
            }
        }
        return false;
    }
}