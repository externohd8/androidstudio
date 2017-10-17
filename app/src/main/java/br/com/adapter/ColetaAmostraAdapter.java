package br.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.model.DadosProjetoAmostra;
import br.com.trees.R;

/**
 * Created by Fernando on 28/09/2016.
 */
public class ColetaAmostraAdapter extends ArrayAdapter<DadosProjetoAmostra> {

    Context context;
    int layout;
    List<DadosProjetoAmostra> dadosProjetoAmostras;

    public ColetaAmostraAdapter(Context context, int layout, List<DadosProjetoAmostra> dadosProjetoAmostras){
        super(context, layout, dadosProjetoAmostras);
        this.context = context;
        this.layout = layout;
        this.dadosProjetoAmostras = dadosProjetoAmostras;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        TextView tvNomeArvore = (TextView)view.findViewById(R.id.tv_nome_comum_adapter_amostra);
        TextView tvCap = (TextView)view.findViewById(R.id.tv_cap_adapter_amostra);
        TextView tvAltura = (TextView)view.findViewById(R.id.tv_altura_adapter_amostra);

        DadosProjetoAmostra dadosProjetoAmostra = dadosProjetoAmostras.get(position);
        tvNomeArvore.setText(dadosProjetoAmostra.getArvore().getNomeComum());
        tvCap.setText(dadosProjetoAmostra.getCap().toString());
        tvAltura.setText(Double.toString(dadosProjetoAmostra.getAltura()));

        return view;
    }
}
