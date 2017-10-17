package br.com.trees;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.adapter.ProjetoCensoAdapter;
import br.com.dao.ProjetoCensoDAO;
import br.com.model.ProjetoCenso;
import br.com.model.Send;
import br.com.enums.Status;

/**
 * Created by Fernando on 28/07/2016.
 */
public class TodosProjetosCenso extends ListActivity {

    //PEGANDO OS DADOS
    ProjetoCensoAdapter adapter;
    List<ProjetoCenso> projetoCensos;
    ProjetoCensoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_projetos_censo);

        //INSTANCIANDO O DAO
        dao = new ProjetoCensoDAO(this);
        projetoCensos = dao.listar();

        //PEGANDO DADOS DO ADAPTER
        adapter = new ProjetoCensoAdapter(this, R.layout.activity_lista_projetos_censo, projetoCensos);

        //PASSANDO O ADAPTER
        setListAdapter(adapter);
        //CRIANDO O MENU
        registerForContextMenu(this.getListView());
    }

    //ESTE RECURSO SERVE PARA VERIFICAR O STATUS DO PROJETO
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        //VERIFICANDO O STATUS DO PROJETO
        ProjetoCenso projetoCenso = this.projetoCensos.get(info.position);
        menu.setHeaderTitle(projetoCenso.getNome());
        //DEVOLVENDO MENSAGEM AO USUARIO
        if (projetoCenso.getStatus().equals("CONCLUIDO")) {
            menu.add(Menu.NONE, 0, Menu.NONE, "Marcar como em progresso");
            menu.add(Menu.NONE, 1, Menu.NONE, "Enviar projeto");
        } else if (projetoCenso.getStatus().equals("EM_PROGRESSO")) {
            menu.add(Menu.NONE, 0, Menu.NONE, "Marcar como concluído");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
        this.doActionMenuItem(item.getItemId(), this.projetoCensos.get(info.position));
        return true;
    }

    public void doActionMenuItem(int id, ProjetoCenso projetoCenso) {
        switch (id) {
            case 0: {
                if (projetoCenso.getStatus().equals("CONCLUIDO")) {
                    projetoCenso.setStatus(Status.EM_PROGRESSO.toString());
                } else if (projetoCenso.getStatus().equals("EM_PROGRESSO")) {
                    projetoCenso.setStatus(Status.CONCLUIDO.toString());
                }

                this.dao.alterar(projetoCenso);
                finish();
                startActivity(this.getIntent());
                Toast.makeText(this, "Projeto " + projetoCenso.getNome() + " alterado com sucesso!",
                        Toast.LENGTH_LONG).show();
                break;
            }
            case 1: {
                Intent abre_login = new Intent(this, IdentificacaoActivity.class);
                Send.putExtra(abre_login, projetoCenso);
                startActivity(abre_login);
                break;
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ProjetoCenso projetoCenso = this.adapter.getItem(position);

        //VERIFICANDO O STATUS DO PROJETO
        //SE ELE ESTIVER CONCLUIDO O SISTEMA CHAMA A ACTIVITY VER COLETA
        if (projetoCenso.getStatus().equals("CONCLUIDO") || projetoCenso.getStatus().equals("ENVIADO")) {
            Intent it = new Intent(this, VerColetaCenso.class);
            it.putExtra("idProjetoCenso", projetoCenso.getId());
            startActivity(it);
        } else {
            //SE ELE ESTIVER EM PROGRESSO O SISTEMA CHAMA A ACTIVITY OPCOES CENSO
            Intent it = new Intent(this, OpcoesCenso.class);
            it.putExtra("idProjetoCenso", projetoCenso.getId().toString());
            startActivity(it);
        }
    }

}
