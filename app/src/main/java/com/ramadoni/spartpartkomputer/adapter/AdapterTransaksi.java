package com.ramadoni.spartpartkomputer.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.model.ModelTransaksi;

import java.util.List;

public class AdapterTransaksi extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelTransaksi> item;

    public AdapterTransaksi(Activity activity, List<ModelTransaksi> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_pesanan, null);


        TextView username           = (TextView) convertView.findViewById(R.id.txtUsername);
        TextView namaSpartpart      = (TextView) convertView.findViewById(R.id.txtnamaSpartpart);
        TextView spartpartLaptop    = (TextView) convertView.findViewById(R.id.txtNamaLaptop);
        TextView harga              = (TextView) convertView.findViewById(R.id.txtHargaSpartPart);
        TextView jumlah             = (TextView) convertView.findViewById(R.id.txtJumlah);
        TextView total              = (TextView) convertView.findViewById(R.id.txtTotal);

        username.setText(item.get(position).getUsername());
        namaSpartpart.setText(item.get(position).getSpartpartLaptop());
        spartpartLaptop.setText(item.get(position).getSpartpartLaptop());
        harga.setText("Rp." + item.get(position).getHarga());
        jumlah.setText(item.get(position).getJumlah());
        total.setText(item.get(position).getTotal());
        return convertView;
    }
}
