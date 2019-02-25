package com.example.trieud01.informedrunning3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    public static final String APPLICATION_ID = "78A380B4-59ED-0E89-FFD4-27D6954FDA00";
    public static final String SECRET_KEY = "583D8783-FF22-BB1F-FF89-DECDED25CD00";
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private CheckBox stayLoggedInCheckBox;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        counter = 0;

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);


        String LoggedInUser = Backendless.UserService.loggedInUser();

        Log.e("LoginActivity", "Logged In User:  " + LoggedInUser);


        emailEditText = findViewById(R.id.email_Edit_Text);
        passwordEditText = findViewById(R.id.password_Edit_Text);

        loginButton = findViewById(R.id.logIn_Button);
        signUpButton = findViewById(R.id.sign_Up_Button);


        stayLoggedInCheckBox = findViewById(R.id.stay_Logged_In_Check_Box);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {

                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(LoginActivity.this, "Login Accepted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, UserMenuActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                                Log.e("LoginActivity", "Log In Successful");

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Log.e("LoginActivity", fault.toString());
                                Log.e("LoginActivity", "Email or Password is wrong");
                                Toast.makeText(LoginActivity.this, "Email or Password Was Incorrect", Toast.LENGTH_SHORT).show();

                                counter += 1;
                                Log.e("LoginActivity", "Count at " + counter);

                                if (counter == 3) {

                                    Intent intent = new Intent(LoginActivity.this, CaptchaCheckActivity.class);
                                    startActivity(intent);
                                    counter = 0;
                                }

                            }


                        },
                        stayLoggedInCheckBox.isChecked());
                passwordEditText.getText().clear();


            }


        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Transferring to Sign Up screen", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }


}
