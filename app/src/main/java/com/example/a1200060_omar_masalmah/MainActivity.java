package com.example.a1200060_omar_masalmah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView scoreText;
    TextView timerText;
    TextView question_number;
    TextView questionText;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    int score = 0;
    int totalQuestions = QuestionsAndAnswers.questions.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    private static final long COUNTDOWN_TIME = 10000; // 10 seconds
    private CountDownTimer countDownTimer;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.textView_score);
        timerText = findViewById(R.id.textView_timer);
        question_number = findViewById(R.id.textView_num);
        questionText = findViewById(R.id.textView_question);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        question_number.setText("Total Questions: " + totalQuestions);

        loadQuestion();
    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;

        if (v.getId() == R.id.button1 || v.getId() == R.id.button2 ||
                v.getId() == R.id.button3 || v.getId() == R.id.button4) {

            selectedAnswer = clicked.getText().toString();
            clicked.setBackgroundColor(Color.RED);
            if (selectedAnswer.equals(QuestionsAndAnswers.correctAnswers[currentQuestionIndex])) {
                score++;
                scoreText.setText("Score: " + score);
            }
            currentQuestionIndex++;
            loadQuestion();

            if (currentQuestionIndex == totalQuestions) {
                finishQuiz();
            }
        }
    }

    void loadQuestion() {
        if (currentQuestionIndex == totalQuestions) {
            finishQuiz();
            return;
        }

        // Reset the timer for each new question
        timeLeft = COUNTDOWN_TIME;

        // Start the timer
        startTimer();

        questionText.setText(QuestionsAndAnswers.questions[currentQuestionIndex]);
        button1.setText(QuestionsAndAnswers.answers[currentQuestionIndex][0]);
        button2.setText(QuestionsAndAnswers.answers[currentQuestionIndex][1]);
        button3.setText(QuestionsAndAnswers.answers[currentQuestionIndex][2]);
        button4.setText(QuestionsAndAnswers.answers[currentQuestionIndex][3]);
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateTimer();
                currentQuestionIndex++; // Move to next question
                loadQuestion();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeft / 1000) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(" " + timeLeftFormatted);
    }

    private void finishQuiz() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Quiz is finished, now start ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", totalQuestions);
        startActivity(intent);
        finish();
    }
}