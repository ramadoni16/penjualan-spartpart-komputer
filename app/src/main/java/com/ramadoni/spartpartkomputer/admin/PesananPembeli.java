package com.ramadoni.spartpartkomputer.admin;

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
import com.ramadoni.spartpartkomputer.adapter.AdapterTransaksi;
import com.ramadoni.spartpartkomputer.model.ModelTransaksi;
import com.ramadoni.spartpartkomputer.pembeli.HomePembeliActivity;
import com.ramadoni.spartpartkomputer.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PesananPembeli  extends AppCompatActivity {

    ProgressDialog pDialog;
    AdapterTransaksi adapter;
    ListView list;

    ArrayList<ModelTransaksi> newsList = new ArrayList<ModelTransaksi>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanan_user);
        getSupportActionBar().setTitle("Daftar Pesanan");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterTransaksi(PesananPembeli.this, newsList);
        list.setAdapter(adapter);
        getAllKeranjang();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PesananPembeli.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllKeranjang() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataOrder, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data Keranjang = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelTransaksi order = new ModelTransaksi();
                                    final String _id = jsonObject.getString("_id");
                                    final String username = jsonObject.getString("username");
                                    final String namaSpartpart = jsonObject.getString("namaSpartpart");
                                    final String spartpartLaptop = jsonObject.getString("spartpartLaptop");
                                    final String Hargaspartpart = jsonObject.getString("Harga");
                                    final String jumlah = jsonObject.getString("Jumlah");
                                    final String total = jsonObject.getString("Total");
                                    order.setUsername(username);
                                    order.setNamaSpartpart(namaSpartpart);
                                    order.setSpartpartLaptop(spartpartLaptop);
                                    order.setHarga(Hargaspartpart);
                                    order.setJumlah(jumlah);
                                    order.setTotal(total);
                                    order.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(PesananPembeli.this, HomePembeliActivity.class);
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("username", newsList.get(position).getUsername());
                                            a.putExtra("namaSpartpart", newsList.get(position).getNamaSpartpart());
                                            a.putExtra("spartpartLaptop", newsList.get(position).getSpartpartLaptop());
                                            a.putExtra("Harga", newsList.get(position).getHarga());
                                            a.putExtra("Jumlah", newsList.get(position).getJumlah());
                                            a.putExtra("Total", newsList.get(position).getTotal());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(order);
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
