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

public class SignUpActivity extends AppCompatActivity {


    public static final String APPLICATION_ID = "78A380B4-59ED-0E89-FFD4-27D6954FDA00";
    public static final String SECRET_KEY = "583D8783-FF22-BB1F-FF89-DECDED25CD00";
    private EditText firstNameEditText, lastNameEditText, usernameEditText, emailEditText, passwordEditText;
    private Button signUpButton, cancelSignUpButton;
    private CheckBox stayLoggedInCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Backendless.initApp(this, APPLICATION_ID, SECRET_KEY);


        firstNameEditText = findViewById(R.id.firstName_Edit_Text);
        lastNameEditText = findViewById(R.id.lastName_Edit_Text);
        usernameEditText = findViewById(R.id.username_Edit_Text);
        emailEditText = findViewById(R.id.email_Edit_Text);
        passwordEditText = findViewById(R.id.password_Edit_Text);

        signUpButton = findViewById(R.id.sign_Up_Button);
        cancelSignUpButton = findViewById(R.id.cancel_Sign_Up_Button);

        stayLoggedInCheckBox = findViewById(R.id.stay_Logged_In_Check_Box);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();


                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !username.isEmpty()) {
                    BackendlessUser user = new BackendlessUser();

                    user.setProperty("firstName", firstName);
                    user.setProperty("lastName", lastName);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setProperty("username", username);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            Intent intent = new Intent(SignUpActivity.this, UserMenuActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUpActivity.this, response.getEmail() + "was registered", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Log.e("SignUpActivity", fault.toString());


                        }
                    });


                }

                emailEditText.getText().clear();
                passwordEditText.getText().clear();
                usernameEditText.getText().clear();

            }
        });


        cancelSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(SignUpActivity.this, "Going back to Login", Toast.LENGTH_SHORT).show();
            }
        });

    }

}



