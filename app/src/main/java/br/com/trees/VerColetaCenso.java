package br.com.trees;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.adapter.ColetaCensoAdapter;
import br.com.dao.DadosProjetoCensoDAO;
import br.com.dao.ProjetoCensoDAO;
import br.com.model.DadosProjetoCenso;
import br.com.model.ProjetoCenso;

/**
 * Created by Fernando on 21/09/2016.
 */
public class VerColetaCenso extends ListActivity {

    ProjetoCensoDAO projetoCensoDAO;

    List<DadosProjetoCenso> dadosProjetoCensoList;
    DadosProjetoCensoDAO dao;

    ColetaCensoAdapter coletaCensoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletadas_censo);

        projetoCensoDAO = new ProjetoCensoDAO(this);
        dao = new DadosProjetoCensoDAO(this);
        Bundle extras = getIntent().getExtras();

        Long s = extras.getLong("idProjetoCenso");

        TextView tvNomeProjeto = (TextView)findViewById(R.id.txt_nome_projeto_censo);
        ProjetoCenso projetoCenso = projetoCensoDAO.buscar(s);
        tvNomeProjeto.setText(projetoCenso.getNome());
        Toast.makeText(this, projetoCenso.getNome(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, extras.getString("idProjetoCenso"), Toast.LENGTH_LONG).show();

        dadosProjetoCensoList = dao.listarPorProjeto(s.toString());
        //ADAPTER
        coletaCensoAdapter = new ColetaCensoAdapter(this, R.layout.activity_lista_dados_coleta_censo, dadosProjetoCensoList);

        setListAdapter(coletaCensoAdapter);
    }
}
