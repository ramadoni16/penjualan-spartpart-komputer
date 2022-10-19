package com.ramadoni.spartpartkomputer.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ornach.nobobutton.NoboButton;
import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.admin.HomeAdminActivity;
import com.ramadoni.spartpartkomputer.pembeli.HomePembeliActivity;
import com.ramadoni.spartpartkomputer.server.BaseURL;
import com.ramadoni.spartpartkomputer.session.PrefSetting;
import com.ramadoni.spartpartkomputer.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    Button btnpindah;
    NoboButton btnlogin;
    EditText edtUserName, edtPassword;


    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnpindah = (Button) findViewById(R.id.btnpindah);
        btnlogin =(NoboButton) findViewById(R.id.loginbtn);
        edtUserName =(EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferences();

        session = new SessionManager(this);

        prefSetting.checkLogin(session, prefs);

        btnpindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrasiActivity2.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strusername = edtUserName.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strusername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                } else {
                    login(strusername, strPassword);
                }
            }
        });
    }
    public void login (String userName, String password){


        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) { 
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");

                            if (status == false) {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String userName = jsonObject.getString("userName");
                                String namaLengkap = jsonObject.getString("namaLengkap");
                                String email = jsonObject.getString("email");
                                String noTelp = jsonObject.getString("noTelp");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(LoginActivity.this, _id, userName, namaLengkap, email, noTelp, role,prefs);
                                if (role.equals("1")) {
                                    Intent i = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent i = new Intent(LoginActivity.this, HomePembeliActivity.class);
                                    startActivity(i);
                                    finish();
                                }
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