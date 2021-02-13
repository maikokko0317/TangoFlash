package com.ict4everyone.tangoflash;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * データベースファイル名の定数フィールド
     */
    private static final String DATABASE_NAME = "Tango.db";
    private static final String TABLE_NAME = "m_quiz";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_QUIZ = "quiz";
    private static final String COLUMN_NAME_ANSWER = "answer";

    /**
     * バージョン情報の定数フィールド
     */
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("  +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_QUIZ + " TEXT," +
                    COLUMN_NAME_ANSWER + " TEXT)" ;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * コンストラクタ
     */
    public DatabaseHelper(Context context) {
        // 親コンストラクタの呼び出し
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // テーブル作成SQLの実行
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("debug", "onCreate(SQLiteDatabase db)");

        //初期データ投入
        saveData(db, null, "風くんすきー", "I love KAZE<3");
        saveData(db, null,"風くんこっちきてー", "Come here, KAZE<3");
        saveData(db, null,"風くん風くん", "KAZE, KAZE<3<3");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    // insertQuestion.javaから呼ばれるためにstatic追加
    public static void saveData(SQLiteDatabase db, String rowid, String quiz, String answer) {
        ContentValues values = new ContentValues();
        values.put("quiz", quiz);
        values.put("answer", answer);

        if(rowid == null || rowid.isEmpty()){
            //rowidない場合、Insert
            db.insert("m_quiz", null, values);
        } else {
            db.update("m_quiz", values, "_id = " + rowid, null);
        }

    }
}

