package com.ramadoni.spartpartkomputer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramadoni.spartpartkomputer.R;
import com.ramadoni.spartpartkomputer.model.ModelSpartpart;
import com.ramadoni.spartpartkomputer.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSpartpart extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelSpartpart> item;

    public AdapterSpartpart(Activity activity, List<ModelSpartpart> item) {
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
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_spartpart, null);



        TextView spartpartLaptop   = (TextView) convertView.findViewById(R.id.txtNamaLaptop);
        TextView namaSpartpart     = (TextView) convertView.findViewById(R.id.txtnamaSpartpart);
        TextView madeIn            = (TextView) convertView.findViewById(R.id.txtmadeIn);
        TextView hargaSpartpart    = (TextView) convertView.findViewById(R.id.txtHargaSpartPart);
        TextView tahunPembuatan    = (TextView) convertView.findViewById(R.id.txtTahunPembutan);
        ImageView gambar           = (ImageView) convertView.findViewById(R.id.gambarSpartpart);




        spartpartLaptop.setText(item.get(position).getSpartpartLaptop());
        namaSpartpart.setText(item.get(position).getNamaSpartpart());
        madeIn.setText(item.get(position).getMadeIn());
        hargaSpartpart.setText("Rp." + item.get(position).getHargaSpartpart());
        tahunPembuatan.setText(item.get(position).getTahunPembuatan());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}
