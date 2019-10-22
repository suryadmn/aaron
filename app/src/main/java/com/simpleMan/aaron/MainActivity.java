package com.simpleMan.aaron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Initialize
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Spinner spinnerJuz, spinnerSurah;
    private EditText editText;
    private Button btnGo;
    private String getData;
    private int intData;
    private quraan fragmentQuraan;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nView);
        spinnerJuz = findViewById(R.id.juzSpinner);
        spinnerSurah = findViewById(R.id.surahSpinner);
        editText = findViewById(R.id.editText);
        btnGo = findViewById(R.id.btnGo);

        sharedPreferences = getSharedPreferences("Aaron",0);
        editor = sharedPreferences.edit();
        editor.apply();

        //Display qura'an default view when first time apps open
        callFragmentQuraan();

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get data from EditText
                getData = editText.getText().toString();

                if (getData.isEmpty()){
                    getData="0";
                }

                intData = Integer.parseInt(getData);
                if (intData == 0){
                    Toast.makeText(getApplicationContext(), "Number page can't be zero or empty", Toast.LENGTH_LONG).show();
                }

                intData -= 1;

                //get data from Activity and send to Fragment quraan
                bundle = new Bundle();
                bundle.putInt("data", intData);

                //Call fragment quraan from function
                callFragmentQuraan();

                //Hide virtual keyboard when button go pressed
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //Empty EditText
                editText.setText("");
            }
        });

        //Limit spinner popup juz
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinnerJuz);

            // Set popupWindow height to 800px
            popupWindow.setHeight(800);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        //Limit spinner popup surah
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinnerSurah);

            // Set popupWindow height to 500px
            popupWindow.setHeight(800);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        //Implements spinner juz to select Listener
        spinnerJuz.setOnItemSelectedListener(this);

        //Implements spinner surah to select Listener
        spinnerSurah.setOnItemSelectedListener(this);

        //For Navigation
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);
    }

    //Handle 3 Line navigation icons when clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            //Hide virtual keyboard
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Handle switch case function
    public void selectItemDrawer(MenuItem menuItem){
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.quraan:
                fragmentClass = quraan.class;
                break;

            case R.id.bookmark:
                fragmentClass = bookmark.class;
                break;

            case R.id.tajwid:
                fragmentClass = tajwid.class;
                break;

            case R.id.help:
                fragmentClass = help.class;
                break;

            case R.id.about:
                fragmentClass = about.class;
                break;

            default:
                fragmentClass = quraan.class;
        }
        try{
            myFragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    //Handle when menu tapped
    private  void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }

    //Handle spinner when choosen
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /** For Juz */
        if (adapterView.getId() == R.id.juzSpinner){
            if (spinnerJuz.getSelectedItemPosition() == 0){
                //Do Nothing

            }else if(spinnerJuz.getSelectedItemPosition() == 1){
                //get data from Activity and send to Fragment quraan
                int juzData = 0;
                bundle = new Bundle();
                bundle.putInt("data",juzData);

                //Call fragment quraan from function
                callFragmentQuraan();

                Toast.makeText(this, spinnerJuz.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

            }else if (spinnerJuz.getSelectedItemPosition() == 2){
                //get data from Activity and send to Fragment quraan
                int juzData = 21;
                bundle = new Bundle();
                bundle.putInt("data",juzData);

                //Call fragment quraan
                callFragmentQuraan();

                Toast.makeText(this, spinnerJuz.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

            }else if (spinnerJuz.getSelectedItemPosition() == 3){
                //get data from Activity and send to Fragment quraan
                int juzData = 41;
                bundle = new Bundle();
                bundle.putInt("data",juzData);

                //Call fragment quraan from function
                callFragmentQuraan();

                Toast.makeText(this, spinnerJuz.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }

        /** For spinner surah */
        }else if (adapterView.getId() == R.id.surahSpinner){
            //get data from Activity and send to Fragment quraan
            if (spinnerSurah.getSelectedItemPosition() == 0){
                //Do nothing

            }else if(spinnerSurah.getSelectedItemPosition() == 1){
                int surahData = 0;
                bundle = new Bundle();
                bundle.putInt("data",surahData);

                //Call fragment quraan from function
                callFragmentQuraan();

                Toast.makeText(this, spinnerSurah.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

            }else if(spinnerSurah.getSelectedItemPosition() == 2){
                int surahData = 1;
                bundle = new Bundle();
                bundle.putInt("data",surahData);

                //Call fragment quraan from function
                callFragmentQuraan();

                Toast.makeText(this, spinnerSurah.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();

            }else if (spinnerSurah.getSelectedItemPosition() == 3){
                int surahData = 49;
                bundle = new Bundle();
                bundle.putInt("data",surahData);

                //Call fragment quraan from function
                callFragmentQuraan();

                Toast.makeText(this, spinnerSurah.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }

    public void callFragmentQuraan() {
        fragmentQuraan = new quraan();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragmentQuraan).commit();
        drawerLayout.closeDrawers();
        //save data to bundle
        fragmentQuraan.setArguments(bundle);
    }
}
