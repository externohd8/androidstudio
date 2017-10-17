package br.com.trees;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.adapter.ProjetoAmostrasAdapter;
import br.com.dao.AmostraDAO;
import br.com.dao.ProjetoAmostrasDAO;
import br.com.model.ProjetoAmostras;
import br.com.enums.Status;
import br.com.model.Send;

/**
 * Created by Fernando on 28/07/2016.
 */
public class TodosProjetosAmostra extends ListActivity {

    //PEGANDO OS DADOS
    ProjetoAmostrasAdapter adapter;
    List<ProjetoAmostras> projetoAmostras;
    ProjetoAmostrasDAO dao;
    AmostraDAO amostraDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_projetos_amostras);

        //INSTANCIANDO O DAO
        dao = new ProjetoAmostrasDAO(this);
        amostraDao = new AmostraDAO(this);

        projetoAmostras = dao.listar();

        //PEGANDO DADOS DO ADAPTER
        adapter = new ProjetoAmostrasAdapter(this, R.layout.activicty_lista_projetos_amostras, projetoAmostras);

        //PASSANDO O ADAPTER
        setListAdapter(adapter);

        //CRIANDO O MENU
        registerForContextMenu(this.getListView());

    }

    //ESTE RECURSO SERVE PARA VERIFICAR O STATUS DO PROJETO
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //VERIFICANDO O STATUS DO PROJETO
        ProjetoAmostras projetoAmostras= this.projetoAmostras.get(info.position);
        menu.setHeaderTitle(projetoAmostras.getNome());
        //DEVOLVENDO MENSAGEM AO USUARIO
        if (projetoAmostras.getStatus().equals("CONCLUIDO")){
            menu.add(Menu.NONE, 0, Menu.NONE, "Marcar como em progresso");
            menu.add(Menu.NONE, 1, Menu.NONE, "Enviar projeto");
        } else if (projetoAmostras.getStatus().equals("EM_PROGRESSO")){
            menu.add(Menu.NONE, 0, Menu.NONE, "Marcar como concluído");
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        this.doActionMenuItem(item.getItemId(), this.projetoAmostras.get(info.position));
        return true;
    }

    public void doActionMenuItem(int id, ProjetoAmostras projetoAmostras){
        switch (id){
            case 0: {
                if (this.amostraDao.countAmostrasNaoConcluidas(projetoAmostras.getId().toString()) > 0) {
                    Toast.makeText(this, "O projeto possui amostras não concluídas.", Toast.LENGTH_LONG).show();
                    break;
                }

                if(projetoAmostras.getStatus().equals("CONCLUIDO")){
                    projetoAmostras.setStatus(Status.EM_PROGRESSO.toString());
                }else if(projetoAmostras.getStatus().equals("EM_PROGRESSO")){
                    projetoAmostras.setStatus(Status.CONCLUIDO.toString());
                }

                this.dao.alterar(projetoAmostras);
                finish();
                startActivity(this.getIntent());
                Toast.makeText(this, "Projeto " + projetoAmostras.getNome() + " alterado com sucesso", Toast.LENGTH_LONG).show();
                break;
            }

            case 1: {
                Intent abre_login = new Intent(this, IdentificacaoActivity.class);
                Send.putExtra(abre_login, projetoAmostras);
                startActivity(abre_login);
                break;
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent it = new Intent(this, TodasAmostras.class);
        it.putExtra("idProjetoAmostra", this.adapter.getItem(position).getId().toString());
        startActivity(it);
    }
}
