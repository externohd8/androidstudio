package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.com.dao.ProjetoCensoDAO;
import br.com.model.ProjetoCenso;
import br.com.enums.Status;
import br.com.validator.Validator;

/**
 * Created by Fernando on 22/04/2016.
 * Está tela e responsavel por cadastrar um novo projeto
 */
public class NovoProjetoCenso extends Activity {
    //PEGANDO AS VARIAVEIS
    EditText txtNome, txtAreaInventariada;

    //ACESSANDO O BANCO DE DADOS
    ProjetoCensoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_projeto_censo);
        //PEGANDO OS DADOS DA TELA
        dao = new ProjetoCensoDAO(this);
        txtNome = (EditText) findViewById(R.id.tv_nome_amostra);
        txtAreaInventariada = (EditText) findViewById(R.id.txt_area_inventariada);
    }

    public void inserir(View v){
        //VERIFICANDO SE OS CAMPOS ESTÃO VAZIOS
        if (Validator.validateEmptyField(this, txtNome, txtAreaInventariada)) {
            //GERANDO UM PROJETO CENSO
            ProjetoCenso projetoCenso = new ProjetoCenso();
            projetoCenso.setNome(txtNome.getText().toString());
            projetoCenso.setAreaInventariada(Double.parseDouble(txtAreaInventariada.getText().toString()));
            projetoCenso.setDataCadastro(new Date());
            projetoCenso.setStatus(Status.EM_PROGRESSO.toString());

            //SALVANDO O PROJETO CENSO
            dao.salvar(projetoCenso);
            //LIMPANDO OS DADOS DOS CAMPOS
            txtNome.setText("");
            txtAreaInventariada.setText("");

            //MENSAGEM DE SUCESSO
            Toast.makeText(this, "Projeto criado com sucesso", Toast.LENGTH_LONG).show();

            //ENCERANDO A ACTIVITY
            finish();

            //REDIRECIONANDO O USUARIO PARA A TELA TODOS OS PROJETOS CENSO
            Intent it = new Intent(this, TodosProjetosCenso.class);
            startActivity(it);

        }
    }

}
