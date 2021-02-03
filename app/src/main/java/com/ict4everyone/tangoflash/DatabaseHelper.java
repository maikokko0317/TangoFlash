package com.ict4everyone.tangoflash;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * データベースファイル名の定数フィールド
     */
    private static final String DATABASE_NAME = "quiz.db";

    /**
     * バージョン情報の定数フィールド
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * コンストラクタ
     */
    public DatabaseHelper(Context context) {
        // 親コンストラクタの呼び出し
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // テーブル作成用SQL文字列の作成
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE quiz(");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("quiz TEXT,");
        sb.append("answer TEXT");
        sb.append(");");
        String sql = sb.toString();

        // SQLの実行
        db.execSQL(sql);

        //初期データ投入
        saveData(db,"風くんすきー", "I love KAZE<3");
        saveData(db,"風くんこっちきてー", "Come here, KAZE<3");
        saveData(db,"風くん風くん", "KAZE, KAZE<3<3");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void saveData(SQLiteDatabase db, String quiz, String answer) {
        ContentValues values = new ContentValues();
        values.put("quiz", quiz);
        values.put("answer", answer);

        db.insert(DATABASE_NAME, null, values);
    }
}

