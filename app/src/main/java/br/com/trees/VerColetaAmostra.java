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

import br.com.adapter.ColetaAmostraAdapter;
import br.com.dao.AmostraDAO;
import br.com.dao.DadosProjetoAmostraDAO;
import br.com.model.Amostra;
import br.com.model.DadosProjetoAmostra;

/**
 * Created by Fernando on 25/09/2016.
 */
public class VerColetaAmostra extends ListActivity {

    AmostraDAO amostrasDAO;

    List<DadosProjetoAmostra> dadosProjetoAmostraList;
    DadosProjetoAmostraDAO dao;

    ColetaAmostraAdapter coletaAmostraAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coletadas_amostra);

        amostrasDAO = new AmostraDAO(this);
        dao = new DadosProjetoAmostraDAO(this);

        Bundle extras = getIntent().getExtras();

        String idAmostra = extras.getString("idAmostra");

        TextView tvNomeAmostra = (TextView)findViewById(R.id.txt_nome_amostra);

        Amostra amostra = amostrasDAO.buscar(idAmostra);

        tvNomeAmostra.setText(amostra.getNome());
        Toast.makeText(this, amostra.getNome(), Toast.LENGTH_LONG).show();

        dadosProjetoAmostraList = dao.listarPorAmostra(idAmostra);

        //ADAPTER
        coletaAmostraAdapter = new ColetaAmostraAdapter(this, R.layout.activity_lista_dados_amostra, dadosProjetoAmostraList);

        setListAdapter(coletaAmostraAdapter);

        registerForContextMenu(this.getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 0, Menu.NONE, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        this.doActionMenuItem(item.getItemId(), this.dadosProjetoAmostraList.get(info.position));
        return true;
    }

    private void doActionMenuItem(int id, DadosProjetoAmostra dadosProjetoAmostra) {
        switch (id) {
            case 0: {
                this.dao.excluir(dadosProjetoAmostra.getId().toString());
                Intent lista = new Intent(this, VerColetaAmostra.class);
                lista.putExtra("idAmostra", dadosProjetoAmostra.getAmostra().getId().toString());
                startActivity(lista);
                break;
            }
        }
    }
}