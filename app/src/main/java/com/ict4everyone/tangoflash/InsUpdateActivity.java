package com.ict4everyone.tangoflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.ict4everyone.tangoflash.DatabaseHelper.saveData;

public class InsUpdateActivity extends AppCompatActivity {

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private TextView tv_rowid;
    private EditText input_quiz_text, input_answer_text;
    private Button insUpdBtn;
    private String rowid, quiz, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_update);

        tv_rowid = findViewById(R.id.rowidTv);
        input_quiz_text = findViewById(R.id.question_input_text);
        input_answer_text = findViewById(R.id.answer_input_text);
        insUpdBtn = findViewById(R.id.insUpd_Btn);

        // 全画面からの値をセット
        //　★遷移されたidで再検索してセットしたい
        Intent intent = getIntent();
        rowid = intent.getStringExtra("rowid");
        quiz = intent.getStringExtra("quiz");
        answer = intent.getStringExtra("answer");

        tv_rowid.setText(rowid);
        input_quiz_text.setText(quiz);
        input_answer_text.setText(answer);

        InsUpdListener listener = new InsUpdListener();
        insUpdBtn.setOnClickListener(listener);
    }

    private class InsUpdListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(helper == null){
                helper = new DatabaseHelper(getApplicationContext());
            }

            if(db == null){
                db = helper.getReadableDatabase();
            }
            String rowid_input = tv_rowid.getText().toString();
            String quiz_input = input_quiz_text.getText().toString();
            String answer_input = input_answer_text.getText().toString();
            saveData(db, rowid_input, quiz_input, answer_input);
            finish();
        }
    }
}