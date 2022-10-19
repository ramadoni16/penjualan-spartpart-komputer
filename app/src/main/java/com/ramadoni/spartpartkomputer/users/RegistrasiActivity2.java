package com.ramadoni.spartpartkomputer.users;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ornach.nobobutton.NoboButton;
import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegistrasiActivity2 extends AppCompatActivity {

    Button kembali_login;
    NoboButton btnRegistrasi;
    EditText username, namalengkap, email, nomortelepon, password;
    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi2);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        namalengkap = (EditText) findViewById(R.id.namalengkap);
        email = (EditText) findViewById(R.id.email);
        nomortelepon = (EditText) findViewById(R.id.nomortelepon);
        password = (EditText) findViewById(R.id.password);

        kembali_login = (Button) findViewById(R.id.kembali_login);
        btnRegistrasi = (NoboButton) findViewById(R.id.btnRegistrasi);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        kembali_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrasiActivity2.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = username.getText().toString();
                String strNamaLengkap = namalengkap.getText().toString();
                String strEmail = email.getText().toString();
                String strNoTelp = nomortelepon.getText().toString();
                String strPassword = password.getText().toString();

                if (strUserName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNamaLengkap.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strEmail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strNoTelp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "NoTelp Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    registrasi(strUserName, strNamaLengkap, strEmail, strNoTelp, strPassword);
                }
            }
        });
    }

    public void registrasi(String userName, String namaLengkap, String email, String noTelp, String password){


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("namaLengkap", namaLengkap);
        params.put("email", email);
        params.put("noTelp", noTelp);
        params.put("role", "2");
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.register, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");

                            if (status == false) {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent( RegistrasiActivity2.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else {
                                 Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        mRequestQueue.add(req);
    }

    private void showDialog() {
        if(!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private  void  hideDialog(){
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}