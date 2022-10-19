package com.ramadoni.spartpartkomputer.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.adapter.AdapterSpartpart;
import com.ramadoni.spartpartkomputer.adapter.AdapterTransaksi;
import com.ramadoni.spartpartkomputer.model.ModelSpartpart;
import com.ramadoni.spartpartkomputer.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilihSpartpart extends AppCompatActivity {
    ProgressDialog pDialog;
    AdapterSpartpart adapter;
    ListView list;

    ArrayList<ModelSpartpart> newsList = new ArrayList<ModelSpartpart>();
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_spartpart);
        getSupportActionBar().setTitle("Pilih Spartpart");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterSpartpart(PilihSpartpart.this, newsList);
        list.setAdapter(adapter);
        getAllSpartpart();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PilihSpartpart.this, HomePembeliActivity.class);
        startActivity(i);
        finish(); }

    private void getAllSpartpart() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataSpartpart, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data Spartpart = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelSpartpart komputer = new ModelSpartpart();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodeSpartpart = jsonObject.getString("kodeSpartpart");
                                    final String namaSpartpart = jsonObject.getString("namaSpartpart");
                                    final String tahunPembuatan = jsonObject.getString("tahunPembuatan");
                                    final String madeIn = jsonObject.getString("madeIn");
                                    final String spartpartLaptop = jsonObject.getString("spartpartLaptop");
                                    final String hargaSpartpart = jsonObject.getString("hargaSpartpart");
                                    final String gambar = jsonObject.getString("gambar");
                                    komputer.setKodeSpartpart(kodeSpartpart);
                                    komputer.setNamaSpartpart(namaSpartpart);
                                    komputer.setTahunPembuatan(tahunPembuatan);
                                    komputer.setMadeIn(madeIn);
                                    komputer.setSpartpartLaptop(spartpartLaptop);
                                    komputer.setHargaSpartpart(hargaSpartpart);
                                    komputer.setGambar(gambar);
                                    komputer.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(PilihSpartpart.this, DetailSpartpartActivity.class);
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("kodeSpartpart", newsList.get(position).getKodeSpartpart());
                                            a.putExtra("namaSpartpart", newsList.get(position).getNamaSpartpart());
                                            a.putExtra("tahunPembuatan", newsList.get(position).getTahunPembuatan());
                                            a.putExtra("madeIn", newsList.get(position).getMadeIn());
                                            a.putExtra("spartpartLaptop", newsList.get(position).getSpartpartLaptop());
                                            a.putExtra("hargaSpartpart", newsList.get(position).getHargaSpartpart());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(komputer);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

