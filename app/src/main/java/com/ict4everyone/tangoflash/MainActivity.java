package com.ict4everyone.tangoflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView japaneseLabel;
    private TextView englishLabel;
    private Button turnBtn;
    private Button nextBtn;

    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String[][] quizData = {
            // {"日本語", "英訳"}
            {"おはよう", "Good Morning!!"},
            {"こんにちは", "Good Afternoon!!"},
            {"こんばんわ", "Good Evening!!"},
            {"元気ですか？", "How are you?"},
            {"おなかすいた", "I'm hungry :("},
            {"いま何時？", "What time is it now?"},
            {"風くんすきー", "I love Kaze <3"},
            {"風くんかっくいー", "Kaze looks soooo cooool!!!!"},
            {"エレクトーン上手くなりたいな", "I want to play a electone much better than now ♪"},
            {"風くんに会いたい", "I miss Kaze so much!!"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        japaneseLabel = findViewById(R.id.japaneseLabel);
        englishLabel = findViewById(R.id.englishLabel);
        turnBtn = findViewById(R.id.turnBtn);
        nextBtn = findViewById(R.id.nextBtn);

        // quizDataから単語カード出題用のquizArrayを作成する
        for (int i = 0; i< quizData.length; i++) {

            // 新しいArraylistを準備
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを作成
            tmpArray.add(quizData[i][0]); // 日本語
            tmpArray.add(quizData[i][1]); // 英訳

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);
        }
        showNextQuiz();
    }

    public void showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel.setText(getString(R.string.count_label, quizCount));

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 日本語を表示
        japaneseLabel.setText(quiz.get(0));

        // 英訳を非表示
        englishLabel.setText((quiz.get(1)));
        englishLabel.setVisibility(View.INVISIBLE);

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
    }

    public void nextCard(View view) {
        if (quizCount == QUIZ_COUNT) {
            // 結果画面へ移動
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(intent);
        } else {
            quizCount++;
            showNextQuiz();
        }
    }

    public void turnCard(View view) {
        // 英訳を表示
        englishLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
    }
}