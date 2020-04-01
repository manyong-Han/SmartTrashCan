package com.manyong.smarttrashcan_final;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ain53 on 2017-05-30.
 */

public class LogActivity extends AppCompatActivity {
    dbHelper helper;
    SQLiteDatabase sql_db;
    EditText id, password;
    Button log, join;

    String[] permission_list = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        helper = new dbHelper(this);
        try {
            sql_db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            sql_db = helper.getReadableDatabase();
        }

        checkPermission();

        //////////////////////////////////////////
        //입력값과 디비 매칭
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.pass);

        log = (Button) findViewById(R.id.login);//로그인 버튼
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LogActivity.this, MainActivity.class);
                if (true == search(id, password)) {
                    startActivity(in);
                }
            }
        });

        join = (Button) findViewById(R.id.memer);//회원 가입 버튼
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LogActivity.this, JoinActivity.class);
                startActivity(in);
            }
        });

    }

    public boolean search(EditText id, EditText password) {
        String id_chack = id.getText().toString();
        String password_chack = password.getText().toString();
        boolean id_true = false;
        boolean password_true = false;
        boolean result = false;
        Cursor cursor, cursor1;

        cursor = sql_db.rawQuery("SELECT id FROM member WHERE id ='" + id_chack + "';", null);
        while (cursor.moveToNext()) {
            if (cursor != null)
                id_true = true;
        }
        cursor1 = sql_db.rawQuery("SELECT password FROM member where password ='" + password_chack + "';", null);
        while (cursor1.moveToNext()) {
            if (cursor1 != null)
                password_true = true;
        }
        String main_id = "";
        main_id = id_chack;

        My_id my_id = (My_id) getApplication();
        my_id.setMain_id(main_id);

        if (id_true == true && password_true == true) {
            result = true;
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
            id.setText("");
            password.setText("");
        } else if (id_true == true && password_true == false) {
            Toast.makeText(getApplicationContext(), "비밀번호 틀림", Toast.LENGTH_SHORT).show();
            id.setText("");
            password.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "아이디 틀림", Toast.LENGTH_SHORT).show();
            id.setText("");
            password.setText("");
        }
        return result;
    }

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱권한설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

}
