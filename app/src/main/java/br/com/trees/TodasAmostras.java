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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.adapter.AmostrasAdapter;
import br.com.dao.AmostraDAO;
import br.com.dao.ProjetoAmostrasDAO;
import br.com.model.Amostra;
import br.com.model.ProjetoAmostras;
import br.com.enums.Status;

public class TodasAmostras extends ListActivity{

    //MENU DE NOVA AMOSTRA
    final int MENU_NOVA_AMOSTRA = 1;

    private ProjetoAmostrasDAO dao;
    private ProjetoAmostras projetoAmostras;

    //PEGANDO DADOS
    AmostrasAdapter adapter;
    List<Amostra> amostras;
    AmostraDAO amostraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_amostras);

        //CONECTANDO AO BANCO
        dao = new ProjetoAmostrasDAO(this);
        //PEGANDO O ID PASSADO PELA TELA ANTERIOR
        Bundle extras = getIntent().getExtras();
        //PEGANDO O TEXTVIEW DA TELA
        TextView tvNomeProjeto = (TextView)findViewById(R.id.txt_nome_projeto);
        //CONSULTANDO ID PASSADO NO BANCO E RETORNANDO O POJETO ENCONTRADO
        projetoAmostras = dao.buscar(extras.getString("idProjetoAmostra"));
        //SETANDO O NOME DO PROJETO ENCONTRADO
        tvNomeProjeto.setText(projetoAmostras.getNome().toString());

        amostraDAO = new AmostraDAO(this);
        amostras = amostraDAO.listarPorProjeto(extras.getString("idProjetoAmostra"));

        adapter = new AmostrasAdapter(this,R.layout.activity_lista_amostras, amostras);

        setListAdapter(adapter);
        //CRIANDO O MENU
        registerForContextMenu(this.getListView());


        //INFORMANDO O NOME DO PROJETO ENCONTRADO
        //Toast.makeText(this, projetoAmostras.getId().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, projetoAmostras.getNome().toString(), Toast.LENGTH_SHORT).show();

    }

    //ESTE RECURSO SERVE PARA VERIFICAR O STATUS DO PROJETO
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //VERIFICANDO O STATUS DO AMOSTRA
        Amostra amostra = this.amostras.get(info.position);
        menu.setHeaderTitle(amostra.getNome());
        //DEVOLVENDO A MENSAGE AO USUARIO
        if(amostra.getStatus().equals("CONCLUIDO")){
            menu.add("Marcar como em progresso");
        }else if(amostra.getStatus().equals("EM_PROGRESSO")){
            menu.add("Marcar como concluído");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        this.doActionMenuItem(item.getItemId(), this.amostras.get(info.position));
        return true;
    }

    public void doActionMenuItem(int id, Amostra amostra){
        switch (id){
            case 0: {
                if (projetoAmostras.getStatus().equals("CONCLUIDO") || projetoAmostras.getStatus().equals("ENVIADO")) {
                    Toast.makeText(this, "Projeto " + amostra.getNome() + " já foi concluído ou enviado!",
                            Toast.LENGTH_LONG).show();
                    break;
                }

                if(amostra.getStatus().equals("CONCLUIDO")){
                    amostra.setStatus(Status.EM_PROGRESSO.toString());
                }else if(amostra.getStatus().equals("EM_PROGRESSO")){
                    amostra.setStatus(Status.CONCLUIDO.toString());
                }

                this.amostraDAO.alterar(amostra);
                finish();
                startActivity(this.getIntent());
                Toast.makeText(this, "Projeto " + amostra.getNome() + " alterado com sucesso !",
                        Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

    //ACOES DO BOTÃO NOVA AMOSTRA
    public void nova_amostra(View v){
        if (projetoAmostras.getStatus().equals("EM_PROGRESSO")) {
            Intent novaAmostra = new Intent(this, NovaAmostra.class);
            novaAmostra.putExtra("idProjetoAmostra", projetoAmostras.getId().toString());
            startActivity(novaAmostra);
            finish();
        }
        Toast.makeText(this, "Atênção, verifique o status do projeto !", Toast.LENGTH_LONG).show();
    }

    //PASSANDO O ID DA AMOSTRA PARA A OUTRA PAGINA
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Amostra amostra = this.adapter.getItem(position);

        //VERIFICANDO O STATUS DO PROJETO
        //SE ELE ESTIVER CONCLUIDO O SISTEMA CHAMA A ACTIVITY VER COLETA
        if(amostra.getStatus().equals("CONCLUIDO")) {
            Intent it = new Intent(this, VerColetaAmostra.class);
            it.putExtra("idAmostra", amostra.getId().toString());
            startActivity(it);
        }else{
            //SE ELE ESTIVER EM PROGRESSO O SISTEMA CHAMA A ACTIVITY OPCOES AMOSTRA
            Intent it = new Intent(this, OpcoesAmostra.class);
            it.putExtra("idAmostra", amostra.getId().toString());
            startActivity(it);
        }
    }
}
