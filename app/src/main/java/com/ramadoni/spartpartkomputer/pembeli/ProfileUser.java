package com.ramadoni.spartpartkomputer.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.session.PrefSetting;

public class ProfileUser  extends AppCompatActivity {

    TextView txtUserName, txtNama, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        getSupportActionBar().setTitle("Profile User");


        txtUserName = (TextView) findViewById(R.id.userName1);
        txtNama = (TextView) findViewById(R.id.nama1);
        txtEmail = (TextView) findViewById(R.id.Email);

        txtUserName.setText(PrefSetting.userName);
        txtNama.setText(PrefSetting.noTelp);
        txtEmail.setText(PrefSetting.email);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfileUser.this, HomePembeliActivity.class);
        startActivity(i);
        finish();
    }
}
