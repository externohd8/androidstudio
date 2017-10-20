package br.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.enums.Status;
import br.com.model.ProjetoCenso;
import br.com.trees.R;

/**
 * Created by Fernando on 01/09/2016.
 */
public class ProjetoCensoAdapter extends ArrayAdapter<ProjetoCenso> {

    Context context;
    int layout;
    List<ProjetoCenso> projetoCensos;

    public ProjetoCensoAdapter(Context context, int layout, List<ProjetoCenso> projetoCenso){
        super(context, layout, projetoCenso);
        this.context = context;
        this.layout = layout;
        this.projetoCensos = projetoCenso;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        TextView tvNome = (TextView) view.findViewById(R.id.tv_nome);
        TextView tvAreaInventariada = (TextView) view.findViewById(R.id.tv_area_inventariada);
        TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);

        ProjetoCenso projetoCenso = projetoCensos.get(position);
        tvNome.setText(projetoCenso.getNome());
        tvAreaInventariada.setText(Double.toString(projetoCenso.getAreaInventariada()));
        tvStatus.setText(Status.get(projetoCenso.getStatus()).getDescricao());

        return view;
    }


}
