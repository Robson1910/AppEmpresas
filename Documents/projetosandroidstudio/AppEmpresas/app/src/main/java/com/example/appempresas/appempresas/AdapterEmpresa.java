package com.example.appempresas.appempresas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.master.glideimageview.GlideImageView;

import java.util.ArrayList;

/**
 * Created by ROBSON on 13/08/2018.
 */

public class AdapterEmpresa extends ArrayAdapter<empresa> {
    private ArrayList<empresa> empresas;
    private Context context;

    public AdapterEmpresa(Context c, ArrayList<empresa> objects) {
        super(c, 0, objects);

        this.context = c;
        this.empresas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (empresas != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.activity_empresa, parent, false);

            TextView textViewNome = (TextView) view.findViewById(R.id.textNome);
            TextView textViewNegocio = (TextView) view.findViewById(R.id.textNegocio);
            TextView textViewPais = (TextView) view.findViewById(R.id.textPais);
            TextView textViewInforme = (TextView) view.findViewById(R.id.textInforme);
            TextView textRecuperar = (TextView) view.findViewById(R.id.recuperarUrl);
            GlideImageView imageEmpresa = (GlideImageView) view.findViewById(R.id.image_empresa);

            empresa empresa2 = empresas.get(position);
            textViewNome.setText(empresa2.getNome());
            textViewNegocio.setText(empresa2.getNegocio());
            textViewPais.setText(empresa2.getPais());
            textViewInforme.setText(empresa2.getInforme());
            imageEmpresa.loadImageUrl(empresa2.getImage());
            textRecuperar.setText(empresa2.getImage());

        }

        return view;
    }
}
