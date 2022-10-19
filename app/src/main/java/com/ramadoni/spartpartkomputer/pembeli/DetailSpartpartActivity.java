package com.ramadoni.spartpartkomputer.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.pembeli.PilihSpartpart;
import com.ramadoni.spartpartkomputer.server.BaseURL;
import com.ramadoni.spartpartkomputer.session.PrefSetting;
import com.ramadoni.spartpartkomputer.users.LoginActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailSpartpartActivity extends AppCompatActivity {

    EditText edtKodeSpartpart, edtNamaSpartpart, edtTahunPembuatan, edtSpartpartLaptop, edtHargaSpartpart, edtJumlahPesanan, edtMadeIn;
    ImageView GambarSpartpart;
    Button TambahPesanan;
    String  username;
    String strKodeSpartpart, strNamaSpartpart, strTahunPembuatan, strSpartPartLaptop, strHargaSpartpart, strGambarSpartpart, strMadeIn, strjumlah, _id;

    private RequestQueue mRequestQueue;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spartpart);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        username = PrefSetting.userName;

        edtKodeSpartpart = (EditText) findViewById(R.id.edtKodeSpartpart);
        edtNamaSpartpart = (EditText) findViewById(R.id.edtNamaSpartpart);
        edtTahunPembuatan = (EditText) findViewById(R.id.edtTahunPembuatan);
        edtSpartpartLaptop = (EditText) findViewById(R.id.edtSpartpartLaptop);
        edtHargaSpartpart = (EditText) findViewById(R.id.edtHargaSpartpart);
        edtMadeIn = (EditText) findViewById(R.id.edtMadeIn);
        edtJumlahPesanan = (EditText) findViewById(R.id.edtJumlahPesanan);

        GambarSpartpart = (ImageView) findViewById(R.id.GambarSpartpart);
        TambahPesanan = (Button) findViewById(R.id.TambahPesanan);

        TambahPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strKodeSpartpart = edtKodeSpartpart.getText().toString();
                strNamaSpartpart = edtNamaSpartpart.getText().toString();
                strTahunPembuatan = edtTahunPembuatan.getText().toString();
                strSpartPartLaptop = edtSpartpartLaptop.getText().toString();
                strMadeIn = edtMadeIn.getText().toString();
                strjumlah = edtJumlahPesanan.getText().toString();
                strHargaSpartpart = edtHargaSpartpart.getText().toString();
                int Total = Integer.parseInt(strHargaSpartpart) * Integer.parseInt(strjumlah);

                order(strNamaSpartpart, strSpartPartLaptop, strjumlah, strHargaSpartpart, Total);

            }
        });

        Intent i = getIntent();

        strKodeSpartpart = i.getStringExtra("kodeSpartpart");
        strNamaSpartpart = i.getStringExtra("namaSpartpart");
        strTahunPembuatan = i.getStringExtra("tahunPembuatan");
        strMadeIn = i.getStringExtra("madeIn");
        strSpartPartLaptop = i.getStringExtra("spartpartLaptop");
        strHargaSpartpart = i.getStringExtra("hargaSpartpart");
        strGambarSpartpart = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodeSpartpart.setText(strKodeSpartpart);
        edtNamaSpartpart.setText(strNamaSpartpart);
        edtTahunPembuatan.setText(strTahunPembuatan);
        edtMadeIn.setText(strMadeIn);
        edtSpartpartLaptop.setText(strSpartPartLaptop);
        edtHargaSpartpart.setText(strHargaSpartpart);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambarSpartpart)
                .into(GambarSpartpart);
    }
    private void order(String strnamaSpartpart, String strspartpartLaptop, String strhargaSpartpart, String strJumlahPesanan, int total) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("namaSpartpart", strNamaSpartpart);
        params.put("spartpartLaptop", strSpartPartLaptop);
        params.put("Harga", strHargaSpartpart);
        params.put("Jumlah", strjumlah);
        params.put("Total", String.valueOf(total));

        pDialog.setMessage("Mohon Tunggu");
        showDialog();
        Log.e("param", String.valueOf(params));

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.order, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(DetailSpartpartActivity.this, PilihSpartpart.class);
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
    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}