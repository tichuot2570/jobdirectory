package com.jobdirectory.jobdirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }


    public void saveSettings(View view) {

        //--- GUI color

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupGUI);
        int radioId = radioGroup.getCheckedRadioButtonId();
        String guiColor = ((RadioButton) findViewById(radioId)).getText().toString();
        String selectedColor;

        if (guiColor.equals("Blue")) {
            selectedColor = "#2cc2f3";
        } else {
            selectedColor = MainActivity.DEFAULTCOLORVALUE;
        }

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("localPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("guiColor", selectedColor);
        editor.commit();


        //--- Language

        RadioGroup radioGroupLang = (RadioGroup) findViewById(R.id.radioGroupLang);
        int radioLangId = radioGroupLang.getCheckedRadioButtonId();
        String lang = ((RadioButton) findViewById(radioLangId)).getText().toString();
        String selectedLang;

        if (lang.equals("FR")) {
            selectedLang = "FR";
        } else {
            selectedLang = MainActivity.DEFAULTLANGUAGE;
        }

        editor.putString("selectedLanguage", selectedLang);
        editor.commit();


        Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_LONG).show();
        displayList(view);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar,menu);
        return  super.onCreateOptionsMenu(menu);
    }


    /**
     * -------- Action bar menu listener
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("localPref", 0);

        switch(item.getItemId()) {
            case R.id.action_login:
                //Verify if a user is already logged
                boolean checkloggedUser = sharedPref.getBoolean("loggedUser", false);
                Intent intent_login;
                if (checkloggedUser == true) {
                    intent_login = new Intent(this, ListJobCompanyActivity.class);
                } else {
                    intent_login = new Intent(this, LoginActivity.class);
                }
                this.startActivity(intent_login);
                break;
            case R.id.action_logout:
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("userName");
                editor.remove("loggedUser");
                editor.commit();
                Intent intent_logout = new Intent(this, MainActivity.class);
                this.startActivity(intent_logout);
                break;
            case R.id.action_home:
                Intent intent_home = new Intent(this, MainActivity.class);
                this.startActivity(intent_home);
                break;
            case R.id.action_about:
                Intent intent_about = new Intent(this, AboutActivity.class);
                this.startActivity(intent_about);
                break;
            case R.id.action_settings:
                Intent intent_settings = new Intent(this, SettingsActivity.class);
                this.startActivity(intent_settings);
                break;
            case R.id.action_favorite:
                Intent intent_favorite = new Intent(this, ListJobFavoriteActivity.class);
                intent_favorite.putExtra("activityInfo", "favorite");
                this.startActivity(intent_favorite);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    //----------------------------

    public void displayList(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}