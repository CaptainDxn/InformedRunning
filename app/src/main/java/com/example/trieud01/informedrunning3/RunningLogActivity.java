package com.example.trieud01.informedrunning3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.trieud01.informedrunning3.Activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class RunningLogActivity extends AppCompatActivity {

    private ListView listView;
    private List<Run> runs;
    private ArrayAdapter<Run> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_log);

        runs = new ArrayList<>();
        listView = findViewById(R.id.run_ListView);

        adapter = new ArrayAdapter<Run>(this, R.layout.run_list_item, runs);
        listView.setAdapter(adapter);

        String ownrId = Backendless.UserService.CurrentUser().getObjectId();
        DataQueryBuilder query = DataQueryBuilder.create();

        Backendless.Persistence.of(Run.class).find(query, new AsyncCallback<List<Run>>() {
            @Override
            public void handleResponse(List<Run> response) {
                runs.clear();
                runs.addAll(response);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e("RunningLogActivity", fault.toString());

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

        if (id == R.id.options_log_out) {
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    Intent intent = new Intent(RunningLogActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Log.e("RunningLogActivity", "Could not log you out");

                }

            });


        }

        return true;
    }
}