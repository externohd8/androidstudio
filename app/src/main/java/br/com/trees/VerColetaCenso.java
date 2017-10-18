package br.com.trees;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.adapter.ColetaCensoAdapter;
import br.com.dao.DadosProjetoCensoDAO;
import br.com.dao.ProjetoCensoDAO;
import br.com.enums.Status;
import br.com.model.DadosProjetoCenso;
import br.com.model.ProjetoCenso;
import br.com.model.Send;

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

        registerForContextMenu(this.getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 0, Menu.NONE, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        this.doActionMenuItem(item.getItemId(), this.dadosProjetoCensoList.get(info.position));
        return true;
    }

    private void doActionMenuItem(int id, DadosProjetoCenso dadosProjetoCenso) {
        switch (id) {
            case 0: {
                this.dao.excluir(dadosProjetoCenso.getId().toString());
                Intent lista = new Intent(this, VerColetaCenso.class);
                lista.putExtra("idProjetoCenso", dadosProjetoCenso.getProjetoCenso().getId());
                startActivity(lista);
                break;
            }
        }
    }
}
