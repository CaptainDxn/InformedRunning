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
    private EditText firstNameEditText, lastNameEditText, usernameEditText, emailEditText, passwordEditText;
    private Button loginButton, cancelSignUpButton, signUpButton, signMeUpButton;
    private CheckBox stayLoggedInCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);

        String LoggedInUser = Backendless.UserService.loggedInUser();

        Log.e("LoginActivity", "Logged In User: " + LoggedInUser);


        firstNameEditText = findViewById(R.id.firstName_Edit_Text);
        lastNameEditText = findViewById(R.id.lastName_Edit_Text);
        usernameEditText = findViewById(R.id.username_Edit_Text);
        emailEditText = findViewById(R.id.email_Edit_Text);
        passwordEditText = findViewById(R.id.password_Edit_Text);

        loginButton = findViewById(R.id.logIn_Button);
        signUpButton = findViewById(R.id.sign_Up_Button);
        cancelSignUpButton = findViewById(R.id.cancel_Sign_Up_Button);
        signMeUpButton = findViewById(R.id.sign_Me_Up_Button);

        stayLoggedInCheckBox = findViewById(R.id.stay_Logged_In_Check_Box);

        loginButton.setVisibility(View.VISIBLE);
        signUpButton.setVisibility(View.VISIBLE);

        firstNameEditText.setVisibility(View.GONE);
        lastNameEditText.setVisibility(View.GONE);
        usernameEditText.setVisibility(View.GONE);
        cancelSignUpButton.setVisibility(View.GONE);
        signMeUpButton.setVisibility(View.GONE);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loginButton.setVisibility(View.GONE);
                signUpButton.setVisibility(View.GONE);

                firstNameEditText.setVisibility(View.VISIBLE);
                lastNameEditText.setVisibility(View.VISIBLE);
                usernameEditText.setVisibility(View.VISIBLE);
                cancelSignUpButton.setVisibility(View.VISIBLE);
                signMeUpButton.setVisibility(View.VISIBLE);

            }
        });


        signMeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();

                /*

                could do other checks here was well.
                if(email.contain('@'))
                if(password .length() >= 8)
                 */


                if (!email.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("username", username);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                             Intent intent = new Intent(LoginActivity.this, UserMenuActivity.class);
                             startActivity(intent);
                            Toast.makeText(LoginActivity.this, response.getEmail() + "was registered", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("LoginActivity", fault.toString());


                        }
                    });


                }

                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                usernameEditText.getText().clear();

            }
        });


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

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("LoginActivity", fault.toString());

                        Toast.makeText(LoginActivity.this, "Email or Password Was Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }, stayLoggedInCheckBox.isChecked());


                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                usernameEditText.getText().clear();


            }


        });


        cancelSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.VISIBLE);

                firstNameEditText.setVisibility(View.GONE);
                lastNameEditText.setVisibility(View.GONE);
                usernameEditText.setVisibility(View.GONE);
                cancelSignUpButton.setVisibility(View.GONE);
                signMeUpButton.setVisibility(View.GONE);


            }
        });


    }


}
