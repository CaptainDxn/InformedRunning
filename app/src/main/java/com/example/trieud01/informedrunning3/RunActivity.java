package com.example.trieud01.informedrunning3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;


public class RunActivity extends AppCompatActivity {
//        implements AdapterView.OnItemSelectedListener{


    private EditText titleEditText, distanceEditText, timeEditText, paceEditText, descriptionEditText;
    private Button dateButton, submitButton;
    private CheckBox makePublic;
    private Spinner metricSpinner, weatherConditionSpinner, moodSpinner;


    private Run current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        current = new Run();
        final String[] metricArray;
        final String[] weatherArray;
        final String[] moodArray;


        titleEditText = findViewById(R.id.title_edit_text);
        distanceEditText = findViewById(R.id.distance_edit_text);
        timeEditText = findViewById(R.id.time_edit_text);
        paceEditText = findViewById(R.id.pace_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);

        dateButton = findViewById(R.id.date_button);
        submitButton = findViewById(R.id.submit_button);

        makePublic = findViewById(R.id.make_public);

        metricSpinner = findViewById(R.id.metric_spinner);
        weatherConditionSpinner = findViewById(R.id.weather_condition_spinner);
        moodSpinner = findViewById(R.id.mood_spinner);

        final String[] metric = new String[]{"Meters", "Miles"};

        final String[] weather = new String[]{"Sunny", "Windy", "Rainy", "Neutral"};

        final String[] mood = new String[]{"Happy", "Sad", "Mad", "Calm", "Stressed"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, metric);

        metricSpinner.setAdapter(adapter);


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weather);
        weatherConditionSpinner.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mood);
        moodSpinner.setAdapter(adapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                current.setTitle(titleEditText.getText().toString());
                current.setDistance(distanceEditText.getText().toString());
                current.setTime(timeEditText.getText().toString());
                current.setPace(paceEditText.getText().toString());
                current.setNotes(descriptionEditText.getText().toString());

//                current.getMetric(metricSpinner.getSelectedItem().toString());
//                current.getWeatherCondition(weatherConditionSpinner.getSelectedItem().toString());
//                current.getMood(moodSpinner.getSelectedItem().toString());


                Backendless.Persistence.save(current, new AsyncCallback<Run>() {
                    @Override
                    public void handleResponse(Run response) {
                        Toast.makeText(RunActivity.this, "Data Has Been Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RunActivity.this, UserMenuActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("RunActivity", fault.toString());

                    }
                });
            }
        });


    }


}
