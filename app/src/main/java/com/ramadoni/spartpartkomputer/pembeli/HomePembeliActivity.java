package com.ramadoni.spartpartkomputer.pembeli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.adapter.AdapterSpartpart;
import com.ramadoni.spartpartkomputer.admin.ActivityDataSpartpart;
import com.ramadoni.spartpartkomputer.admin.EditSpartpartDanHapusActivity;
import com.ramadoni.spartpartkomputer.admin.HomeAdminActivity;
import com.ramadoni.spartpartkomputer.admin.Profile;
import com.ramadoni.spartpartkomputer.model.ModelSpartpart;
import com.ramadoni.spartpartkomputer.server.BaseURL;
import com.ramadoni.spartpartkomputer.session.PrefSetting;
import com.ramadoni.spartpartkomputer.session.SessionManager;
import com.ramadoni.spartpartkomputer.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeliActivity extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, CarddataSpartpart, CardProfie, keranjangsaya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_pembeli);
        getSupportActionBar().hide();

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(HomePembeliActivity.this);

        prefSetting.isLogin(session, prefs);

        cardExit = (CardView) findViewById(R.id.cardExitUser);
        CarddataSpartpart = (CardView) findViewById(R.id.CarddataSpartpart);
        CardProfie = (CardView) findViewById(R.id.CardProfie);
        keranjangsaya = (CardView) findViewById(R.id.keranjangsaya);

        cardExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeliActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        CarddataSpartpart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeliActivity.this, PilihSpartpart.class);
                startActivity(i);
                finish();
            }
        });

        CardProfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeliActivity.this, ProfileUser.class);
                startActivity(i);
                finish();
            }
        });

        keranjangsaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePembeliActivity.this, KeranjangBelanja.class);
                startActivity(i);
                finish();
            }
        });
    }
}