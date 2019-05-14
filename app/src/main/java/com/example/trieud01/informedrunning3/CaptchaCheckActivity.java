package com.example.trieud01.informedrunning3.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trieud01.informedrunning3.LoginActivity;
import com.example.trieud01.informedrunning3.R;

import java.util.Arrays;

public class CaptchaCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons;
    private boolean[] selected;
    private boolean[] isBlue;

    private Button checkAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha_check);

        buttons = new Button[9];
        selected = new boolean[9];
        isBlue = new boolean[9];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = (Button) findViewById(getResources().getIdentifier("btn" + (i + 1),
                    "id", getPackageName()));
        }

        for (Button b : buttons) {
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            b.setOnClickListener(this);
        }

        setUpButtons();

        checkAnswerButton = findViewById(R.id.submit_captcha_button);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                if (checkSubmission()) {
                    message = "You got it right";
                    Intent intent = new Intent(CaptchaCheckActivity.this, LoginActivity.class);
                    startActivity(intent);
                }


                if (!checkSubmission()) {
                    message = "That is not right";
                }
                Toast.makeText(CaptchaCheckActivity.this, message, Toast.LENGTH_SHORT).show();
                setUpButtons();
            }
        });
    }

    private boolean checkSubmission() {
        for (int i = 0; i < isBlue.length; i++) {
            if (isBlue[i] != selected[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        int index = Arrays.asList(buttons).indexOf(button);
        button.setText(button.getText().length() == 0 ? "✔" : "");
        selected[index] = !selected[index];

    }

    private void setUpButtons() {

        for (int i = 0; i < selected.length; i++) {
            selected[i] = false;
            buttons[i].setText("");
            int chance = (int) (Math.random() * 100);
            if (chance < 33) {
                buttons[i].setBackgroundColor(Color.argb(205, 0, 0, 255));
                isBlue[i] = true;
            } else {
                buttons[i].setBackgroundColor(Color.argb(205, 0, 255, 0));
                isBlue[i] = false;
            }
        }


    }
}



