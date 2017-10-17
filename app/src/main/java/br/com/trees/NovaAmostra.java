package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.com.dao.AmostraDAO;
import br.com.dao.ProjetoAmostrasDAO;
import br.com.model.Amostra;
import br.com.model.ProjetoAmostras;
import br.com.enums.Status;

/**
 * Created by Fernando on 28/07/2016.
 */
public class NovaAmostra extends Activity {

    //PEGANDO AS VARIAVEIS
    EditText txtNomeAmostra, txtTamanhoAmostra;
    String idProjeto;

    ProjetoAmostrasDAO dao;
    ProjetoAmostras projetoAmostras;
    AmostraDAO amostraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amostra);
        dao = new ProjetoAmostrasDAO(this);
        amostraDAO = new AmostraDAO(this);

        txtNomeAmostra = (EditText)findViewById(R.id.tv_nome_amostra);
        txtTamanhoAmostra = (EditText)findViewById(R.id.tv_tamanho_amostra);

        //PEGANDO O ID VINDO DA OUTRA TELA
        Bundle extras = getIntent().getExtras();
        idProjeto = extras.getString("idProjetoAmostra");

        projetoAmostras = dao.buscar(idProjeto);
        // Toast.makeText(this, idProjeto, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, projetoAmostras.getNome(), Toast.LENGTH_SHORT).show();

    }

    public void inserirNovaAmostra(View v){
        //CADASTRANDO UMA NOVA AMOSTRA
        Amostra amostra = new Amostra();
        amostra.setProjetoAmostra(new ProjetoAmostras(idProjeto));
        amostra.setNome(txtNomeAmostra.getText().toString());
        amostra.setTamanho(Double.parseDouble(txtTamanhoAmostra.getText().toString()));
        amostra.setDataCadastro(new Date());
        amostra.setStatus(Status.EM_PROGRESSO.toString());

        //SALVANDO O PROJETO CENSO
        amostraDAO.salvar(amostra);

        //MENSAGEM DE SUCESSO
        Toast.makeText(this, "Amostra criada com sucesso", Toast.LENGTH_LONG).show();

        //ENCERANDO A ACTIVITY
        finish();
        
        Intent it = new Intent(this, TodasAmostras.class);
        it.putExtra("idProjetoAmostra", idProjeto);
        startActivity(it);
    }
}
