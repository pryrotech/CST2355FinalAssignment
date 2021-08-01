package com.example.cst2355finalassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME, "In onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchText = (EditText)findViewById(R.id.searchQuery);

        Button launchButton = findViewById(R.id.launchButton);
        launchButton.setOnClickListener(click -> {
            String searchTerm = searchText.getText().toString();
            Intent goToSearch = new Intent(MainActivity.this, SearchActivity.class);

            goToSearch.putExtra("SearchTerm", searchTerm.toString().replaceAll(" ", "_"));
            startActivity(goToSearch);

        });
    }

}