package br.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.enums.Status;
import br.com.model.Amostra;
import br.com.trees.R;

/**
 * Created by Fernando on 15/09/2016.
 */
public class AmostrasAdapter extends ArrayAdapter<Amostra> {

    Context context;
    int layout;
    List<Amostra> amostras;

    public AmostrasAdapter(Context context, int layout, List<Amostra> amostras){
        super(context, layout, amostras);
        this.context = context;
        this.layout = layout;
        this.amostras = amostras;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        TextView tvNome = (TextView)view.findViewById(R.id.tv_nome_amostra);
        TextView tvTamanhoAmostra = (TextView)view.findViewById(R.id.tv_tamanho_amostra);
        TextView tvStatus = (TextView)view.findViewById(R.id.tv_status_amostra);

        Amostra amostra = amostras.get(position);
        tvNome.setText(amostra.getNome());
        tvTamanhoAmostra.setText(Double.toString(amostra.getTamanho()));
        tvStatus.setText(Status.get(amostra.getStatus()).getDescricao());

        return view;
    }
}
