package com.example.rentlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class privacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_policy);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.privacy_optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.Home:
                Intent intent = new Intent(this, userHomepage.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                Intent intent1 = new Intent(this, Login.class);
                startActivity(intent1);
                return true;
            case R.id.tc:
                Intent intent2 = new Intent(this, termsandcnd.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}