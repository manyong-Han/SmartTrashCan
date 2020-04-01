package com.manyong.smarttrashcan_final;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ain53 on 2017-05-30.
 */

public class JoinActivity extends AppCompatActivity {
    dbHelper helper;
    SQLiteDatabase sql_db;
    EditText id, password, name, address, password2;
    ImageView setImage, unsetImage;
    static int count=0;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        helper = new dbHelper(this);
        try {
            sql_db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            sql_db = helper.getReadableDatabase();
        }

        final Button join = (Button) findViewById(R.id.last_join);
        final Button id_chack = (Button) findViewById(R.id.id_chack);


        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        setImage = (ImageView) findViewById(R.id.Image_chack);
        unsetImage = (ImageView) findViewById(R.id.Image_chack);

        //////////////////////////////////
        //아이디 체크
        id_chack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(id);
                count++;

            }
        });

        //////////////////////////
        //페스워드 일치 확인
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().equals(password2.getText().toString())) {
                    setImage.setImageResource(R.drawable.chack);
                } else
                    unsetImage.setImageResource(R.drawable.unchack);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(join);
            }
        });

    }

    public void insert(View target) {
        String insert_id = id.getText().toString();
        String insert_password = password.getText().toString();
        String insert_password2 = password2.getText().toString();
        String insert_name = name.getText().toString();
        String insert_address = address.getText().toString();

        // 회원가입 양식에 하나라도 빈칸이 있는 경우
        if(insert_id.length() == 0 || insert_password.length() == 0 || insert_password2.length() == 0 ||
                insert_name.length() == 0 || insert_address.length() == 0) {
            Toast.makeText(getApplicationContext(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
        }

        else if(this.count != 1) {
            Toast.makeText(getApplicationContext(), "아이디 중복 확인 해주세요.", Toast.LENGTH_SHORT).show();
            this.count = 0;
        }

        else if (insert_password.equals(insert_password2) == false && this.count >= 1) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            password.setText("");
            password2.setText("");
            this.count = 0;
            Toast.makeText(getApplicationContext(), count, Toast.LENGTH_SHORT).show();
        }

        else if (insert_password.equals(insert_password2) && this.count >=1) {
            sql_db.execSQL("INSERT INTO member VALUES ('" + insert_id + "','" +
                    insert_password + "','" + insert_name + "','" + insert_address + "');");
            Toast.makeText(getApplicationContext(), "성공적", Toast.LENGTH_SHORT).show();

            this.count = 0;
            finish();
        }
    }

    public void search(EditText editText) {
        String id_chack = id.getText().toString();
        Cursor cursor;
        boolean flag = true;
        cursor = sql_db.rawQuery("SELECT id FROM member WHERE id ='" + id_chack + "';", null);
        while (cursor.moveToNext()) {
            if (cursor != null) {
                Toast.makeText(getApplicationContext(), "아이디 중복", Toast.LENGTH_SHORT).show();
                id.setText("");
                flag = false;
            }
        }
        if(flag)
        Toast.makeText(getApplicationContext(), "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show();
    }

}
