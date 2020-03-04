package com.simpleMan.aaron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
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
        permission_check();
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.juzArray, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerJuz.setAdapter(adapter);

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

            case R.id.teori:
                fragmentClass = Theory.class;
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

    /**
     * Call Al-Qur'an Fragment
     */
    public void callFragmentQuraan() {
        fragmentQuraan = new quraan();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragmentQuraan).commit();
        drawerLayout.closeDrawers();
        //save data to bundle
        fragmentQuraan.setArguments(bundle);
    }

    /**
     * Check EXTERNAL_STORAGE Permission
     */
    public void permission_check(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         switch (requestCode){
             case 1:
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                     File create_dir =  new File(Environment.getExternalStorageDirectory()+"/Aaron/Data/Audio");
                     Toast.makeText(this, "Membuat folder baru", Toast.LENGTH_LONG).show();
                     create_dir.mkdirs();

                 }else {
                     finish();
                 }
         }
    }
}
