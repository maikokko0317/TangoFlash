package com.ict4everyone.tangoflash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private ListView lvQuiz;
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // データベースヘルパーオブジェクトを作成
        if(helper == null) {
            helper = new DatabaseHelper(getApplicationContext());
        }

        readData();
    }

    /**
     * DBから全件取得し画面表示
     */
    public void readData(){

        // ListViewオブジェクトを取得
        lvQuiz = findViewById(R.id.lvQuiz);
        // リストビューに表示するリストデータ用Listオブジェクトを作成
        List<Map<String, String>> quizList = new ArrayList<>();
        Map<String, String> quiz;

        // リストデータの登録
 /*     quiz = new HashMap<>();
        quiz.put("quiz", "おはよう");
        quiz.put("answer", "Good Morning!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "こんにちは");
        quiz.put("answer", "Good Afternoon!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "こんばんは");
        quiz.put("answer", "Good Evening!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "ありがとう");
        quiz.put("answer", "Thank you!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "ごめんなさい");
        quiz.put("answer", "Sorry!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "美味しい");
        quiz.put("answer", "Yammy!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "いいね");
        quiz.put("answer", "Good!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "元気？");
        quiz.put("answer", "What's up!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "うれしい");
        quiz.put("answer", "I'm glad!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "ねむい");
        quiz.put("answer", "I'm sleepy!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "あそぼう");
        quiz.put("answer", "Let's have a fun!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "またね");
        quiz.put("answer", "See you later!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "ちょうだい");
        quiz.put("answer", "Let me hava one!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "風くんかっこいい");
        quiz.put("answer", "Kaze is sooo coool!!");
        quizList.add(quiz);
        quiz = new HashMap<>();
        quiz.put("quiz", "風くんに会いたい");
        quiz.put("answer", "I'm miss Kaze-kun!!");
        quizList.add(quiz);

  */


        // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        if(db == null) {
            db = helper.getWritableDatabase();
        }
        Cursor cursor = db.query(
                "m_quiz",
                new String[] {"rowid","quiz", "answer"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        for (int i=0; i<cursor.getCount(); i++) {
            quiz = new HashMap<>();
            Log.d("debug","まいこcursor.getString(1)"+cursor.getString(1));
            quiz.put("rowid", cursor.getString(0));
            quiz.put("quiz", cursor.getString(1));
            quiz.put("answer", cursor.getString(2));
            quizList.add(quiz);

            cursor.moveToNext();
        }
        cursor.close();
//        // データベース接続オブジェクトの解放
 //       db.close();

        // SimpleAdapter第4引数from用データの用意
        String[] from = {"quiz", "answer"};
        // SimpleAdapter第5引数to用データの用意
        int[] to = {android.R.id.text1, android.R.id.text2};
        // SimpleAdapterを生成
        SimpleAdapter adapter = new SimpleAdapter(ListActivity.this, quizList, android.R.layout.simple_list_item_2, from, to);
        // アダプタの登録
        lvQuiz.setAdapter(adapter);

        // リスナ設定（タップした時）
        lvQuiz.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // タップされた問題を取得
                Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
                String rowid = item.get("rowid");
                String quiz = item.get("quiz");
                String answer = item.get("answer");
                // インテントオブジェクトを生成
                Intent intent = new Intent(ListActivity.this, InsUpdateActivity.class);
                // 次画面に贈るデータを格納
                intent.putExtra("rowid", rowid);
                intent.putExtra("quiz", quiz);
                intent.putExtra("answer", answer);

                startActivity(intent);
            }
        });

        // リスナ設定（長押しした時）
        lvQuiz.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // タップされた問題を取得
                Map<String, String> item = (Map<String, String>) parent.getItemAtPosition(position);
                String rowid = item.get("rowid");
                String quiz = item.get("quiz");

                // ダイアログを表示
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);

                builder.setTitle("削除");
                builder.setMessage(rowid + ":" + quiz + "を削除しますか");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(helper == null){
                            helper = new DatabaseHelper(getApplicationContext());
                        }
                        if(db == null){
                            db = helper.getReadableDatabase();
                        }
                        db.delete("m_quiz", "_id = " + rowid,null);
                        // ★削除しました、のtoast出したい
                        onStart();
                    }
                });
                builder.setNegativeButton("CANCEL", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    public void moveInsert(View v){
        Intent intent = new Intent(getApplication(), InsUpdateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        readData();
    }

    //    @Override
//    public void onBackPressed() {
//    }
}