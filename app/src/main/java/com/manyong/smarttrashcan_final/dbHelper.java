package com.manyong.smarttrashcan_final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ain53 on 2017-05-30.
 */

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "smarttrash.db";
    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        // 회원 정보 테이블 생성
        db.execSQL("CREATE TABLE member ( id TEXT PRIMARY KEY," +
                "password TEXT NOT NULL, name TEXT NOT NULL, address TEXT);");

        // 쓰레기통 정보 테이블 생성
        db.execSQL("CREATE TABLE trash ( tid int PRIMARY KEY," +
                "tname TEXT not null, ttime time," +
                "tlat TEXT, tlon TEXT);");


        // 쓰레기통 정보 추가
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(1, '장영실관', '36.737425', '127.075984');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(2, '대학본부', '36.737680', '127.074972');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(3, '체육관', '36.737574', '127.072892');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(4, '제2공학관', '36.737128', '127.073495');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(5, '제1공학관', '36.736600', '127.074849');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(6, '강석규교육관', '36.735977', '127.074716');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(7, '우체국근처', '36.736247', '127.075738');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(8, '조형관', '36.735187', '127.074256');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(9, '예술관', '36.735169', '127.075037');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(10, '자연대', '36.736227', '127.072320');");
        db.execSQL("INSERT INTO trash(tid, tname, tlat, tlon) VALUES(11, '세출호', '36.739448041025646', '127.07539088909981');");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE if EXISTS member");
        db.execSQL("DROP TABLE if EXISTS trash");
        onCreate(db);
    }
}
