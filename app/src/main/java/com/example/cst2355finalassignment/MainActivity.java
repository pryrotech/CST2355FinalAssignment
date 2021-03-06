package com.example.cst2355finalassignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ACTIVITY_NAME = "MAIN_ACTIVITY";
    SharedPreferences preferences;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(ACTIVITY_NAME, "In onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchText = (EditText)findViewById(R.id.searchQuery);

        String searchToast = getString(R.string.toast_1);

        //This gets the toolbar from the layout:
        Toolbar tBar = findViewById(R.id.toolbar);

        //This loads the toolbar, which calls onCreateOptionsMenu below:
        setSupportActionBar(tBar);

        //start Navigation Bar
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Display application icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        search = findViewById(R.id.searchQuery);

        preferences = getSharedPreferences("Search",Context.MODE_PRIVATE);
        String searchQuery = preferences.getString("Search","");
        search.setText(searchQuery);

        Fragment fragment = new UpdateFragment();

        getSupportFragmentManager().beginTransaction().replace
                (R.id.fragment_frame, fragment, fragment.getClass()
                        .getSimpleName()).addToBackStack(null).commit();

        Button launchButton = findViewById(R.id.launchButton);
        launchButton.setOnClickListener(click -> {
            Toast toast= Toast.makeText(getApplicationContext(),searchToast,Toast.LENGTH_SHORT);
            toast.show();
            String searchTerm = searchText.getText().toString();
            Intent goToSearch = new Intent(MainActivity.this, SearchActivity.class);
            goToSearch.putExtra("SearchTerm", searchTerm.toString().replaceAll(" ", "_"));
            startActivity(goToSearch);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.help_item:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.help_message_title)
                    //What is the message:
                    .setMessage(R.string.help_message)

                        .setPositiveButton(R.string.cancel, (click, arg) -> { })

                        .setNegativeButton(R.string.yes, (click, arg) -> {
                            Intent goToHelp = new Intent(MainActivity.this, HelpActivity.class);
                            startActivity(goToHelp);
                        })

                    //Show the dialog
                    .create().show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;

        switch(item.getItemId())
        {
            case R.id.homePage:
                Intent goToHome = new Intent(MainActivity.this, MainActivity.class);
                startActivity(goToHome);
                break;
            case R.id.favePage:
                Intent goToFave = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(goToFave);
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences(search.getText().toString());
    }

    private void SharedPreferences(String search){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Search",search);
        editor.commit();
    }

}