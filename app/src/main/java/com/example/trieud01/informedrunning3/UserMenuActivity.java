package com.example.trieud01.informedrunning3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.trieud01.informedrunning3.Activity.LoginActivity;
import com.example.trieud01.informedrunning3.Activity.RunActivity;
import com.example.trieud01.informedrunning3.Activity.RunningLogActivity;

import java.util.List;

public class UserMenuActivity extends AppCompatActivity {

    private TextView welcomeTextView, createTextView, viewTextView, sharedTextView;
    private List<Run> runs;
    private ArrayAdapter<Run> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        welcomeTextView = findViewById(R.id.welcome_text_view);
        createTextView = findViewById(R.id.create_text_view);
        viewTextView = findViewById(R.id.view_text_view);
        sharedTextView = findViewById(R.id.shared_text_view);

        String owerId = Backendless.UserService.CurrentUser().getObjectId();
        DataQueryBuilder query = DataQueryBuilder.create();


        createTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, RunActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Going to RunActivity");

            }
        });


        viewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, RunningLogActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Going to RunningLogActivity");

            }
        });


        sharedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserMenuActivity.this, UserMenuActivity.class);
                startActivity(intent);
                Log.e("UserMenuActivity", "Staying in Screen");

                createTextView.setVisibility(View.GONE);
                viewTextView.setVisibility(View.GONE);
                sharedTextView.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_options, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.options_change_username) {
            Toast.makeText(this, "Change username", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.options_set_notifications_toggle) {
            Toast.makeText(this, "Notification Toggle", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.options_change_language) {
            Toast.makeText(this, "Change Language", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.options_send_feedback) {
            Toast.makeText(this, "Send Feedback", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.options_password_recovery) {
            Toast.makeText(this, "Password Recovery", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.options_help) {
            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.options_log_out) {


            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Intent intent = new Intent(UserMenuActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Log.e("UserMenuActivity", "Log out Successful");
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("UserMenuActivity", "Could not logout User:");

                }

            });

        }


        return true;
    }



}