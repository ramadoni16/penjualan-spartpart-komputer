package com.ramadoni.spartpartkomputer.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.pembeli.HomePembeliActivity;
import com.ramadoni.spartpartkomputer.session.PrefSetting;
import com.ramadoni.spartpartkomputer.session.SessionManager;
import com.ramadoni.spartpartkomputer.users.LoginActivity;

public class HomeAdminActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView CardExit, CarddataSpartpart, CardInputSpartpart, CardProfile, CardPesananPembeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomeAdminActivity.this);

        prefSetting.isLogin(session, prefs);

        CardExit = (CardView) findViewById(R.id.CardExit);
        CarddataSpartpart = (CardView) findViewById(R.id.CarddataSpartpart);
        CardInputSpartpart =(CardView) findViewById(R.id.CardInputSpartpart);
        CardProfile =(CardView) findViewById(R.id.CardProfie);
        CardPesananPembeli= (CardView) findViewById(R.id.CardPesananPembeli);

        CardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomeAdminActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        CarddataSpartpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, ActivityDataSpartpart.class);
                startActivity(i);
                finish();
            }
        });

        CardPesananPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, PesananPembeli.class);
                startActivity(i);
                finish();
            }
        });

        CardInputSpartpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, InputDataSpartpart.class);
                startActivity(i);
                finish();
            }
        });
        CardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeAdminActivity.this, Profile.class);
                startActivity(i);
                finish();
            }
        });
    }
}