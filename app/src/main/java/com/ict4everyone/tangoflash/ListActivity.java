package com.ict4everyone.tangoflash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // データベースヘルパーオブジェクトを作成
        helper = new DatabaseHelper(getApplicationContext());

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
        quiz = new HashMap<>();
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
 /*     quiz = new HashMap<>();
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
        SQLiteDatabase db = helper.getWritableDatabase();
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
    }

    @Override
    public void onBackPressed() {
    }
}