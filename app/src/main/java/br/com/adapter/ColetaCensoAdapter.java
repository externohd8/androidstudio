package br.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.model.DadosProjetoCenso;
import br.com.trees.R;

/**
 * Created by Fernando on 21/09/2016.
 */
public class ColetaCensoAdapter extends ArrayAdapter<DadosProjetoCenso> {

    Context context;
    int layout;
    List<DadosProjetoCenso> dadosProjetoCensos;

    public ColetaCensoAdapter(Context context, int layout, List<DadosProjetoCenso> dadosProjetoCensos){
        super(context, layout, dadosProjetoCensos);
        this.context = context;
        this.layout = layout;
        this.dadosProjetoCensos = dadosProjetoCensos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        TextView tvNomeArvore = (TextView)view.findViewById(R.id.tv_nome_comum_adapter);
        TextView tvCap = (TextView)view.findViewById(R.id.tv_cap_adapter);
        TextView tvAltura = (TextView)view.findViewById(R.id.tv_altura_adapter);

        DadosProjetoCenso dadosProjetoCenso = dadosProjetoCensos.get(position);
        tvNomeArvore.setText(dadosProjetoCenso.getArvore().getNomeComum().toString());
        tvCap.setText(Double.toString(dadosProjetoCenso.getCap()));
        tvAltura.setText(Double.toString(dadosProjetoCenso.getAltura()));

        return view;
    }


}
