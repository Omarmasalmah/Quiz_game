package com.example.a1200060_omar_masalmah;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView statusText;
    TextView scoreText;
    Button resetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        statusText = findViewById(R.id.textView_status);
        scoreText = findViewById(R.id.textView_final_score);
        resetButton = findViewById(R.id.button_reset);

        // Get the score from the previous activity
        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        // Display feedback
        if (score >= 4) {
            statusText.setText("You Won!");
        } else {
            statusText.setText("You Lost!");
        }

        // Display score
       scoreText.setText("Score: " + score + "/" +totalQuestions);

        // Reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the quiz or return to the main activity
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
