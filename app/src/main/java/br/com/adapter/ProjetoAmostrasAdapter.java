package br.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.model.ProjetoAmostras;
import br.com.trees.R;

/**
 * Created by Fernando on 07/09/2016.
 */
public class ProjetoAmostrasAdapter extends ArrayAdapter<ProjetoAmostras> {

    Context context;
    int layout;
    List<ProjetoAmostras> projetoAmostras;

    public ProjetoAmostrasAdapter(Context context, int layout, List<ProjetoAmostras> projetoAmostras){
        super(context, layout, projetoAmostras);
        this.context = context;
        this.layout = layout;
        this.projetoAmostras = projetoAmostras;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        TextView tvNome = (TextView) view.findViewById(R.id.tv_nome);
        TextView tvAreaInventariada = (TextView) view.findViewById(R.id.tv_area_inventariada);
        TextView tvIndiceConfianca = (TextView) view.findViewById(R.id.tv_confianca);
        TextView tvStatus = (TextView) view.findViewById(R.id.tv_status_amostra);

        ProjetoAmostras projetoAmostra = projetoAmostras.get(position);
        tvNome.setText(projetoAmostra.getNome());
        tvAreaInventariada.setText(Double.toString(projetoAmostra.getAreaInventariada()));
        tvIndiceConfianca.setText(Double.toString(projetoAmostra.getIndiceConfianca()));
        tvStatus.setText(projetoAmostra.getStatus());

        return view;
    }
}
