package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import br.com.dao.ProjetoAmostrasDAO;
import br.com.model.ProjetoAmostras;
import br.com.enums.Status;
import br.com.validator.Validator;

/**
 * Created by Fernando on 28/07/2016.
 * Está tela é responsavel por cadastrar um novo projeto
 */
public class NovoProjetoAmostra extends Activity {

    //CRIANDO AS VARIAVEIS
    EditText txtNome, txtAreaInventariada, txtIndiceConfianca;

    //ACESSO AO BANCO DE DADOS
    ProjetoAmostrasDAO projetoAmostrasDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_projeto_amostra);

        //PEGANDO OS DADOS DA TELA
        projetoAmostrasDAO = new ProjetoAmostrasDAO(this);

        //PEGANDO OS VALORES DO TXT
        txtNome = (EditText)findViewById(R.id.tv_nome_amostra);
        txtAreaInventariada = (EditText)findViewById(R.id.txt_area_inventariada);
        txtIndiceConfianca = (EditText)findViewById(R.id.txt_indice_confiabilidade);
    }

    //METODO SALVAR
    public void salvarProjeto(View v){

        //VERIFICANDO SE OS CAMPOS ESTÃO VAZIOS
        if(Validator.validateEmptyField(this, txtNome, txtAreaInventariada, txtIndiceConfianca)) {

            //POPULANDO O OBJETO
            ProjetoAmostras projetoAmostras = new ProjetoAmostras();
            projetoAmostras.setNome(txtNome.getText().toString());
            projetoAmostras.setAreaInventariada(Double.parseDouble(txtAreaInventariada.getText().toString()));
            projetoAmostras.setIndiceConfianca(Double.parseDouble(txtIndiceConfianca.getText().toString()));
            projetoAmostras.setDataCadastro(new Date());
            projetoAmostras.setStatus(Status.EM_PROGRESSO.toString());

            //SALVANDO O OBJETO
            projetoAmostrasDAO.salvar(projetoAmostras);

            //LIMPANDO OS CAMPOS
            txtNome.setText("");
            txtIndiceConfianca.setText("");
            txtAreaInventariada.setText("");

            //AVISO
            Toast.makeText(this, "Projeto criando com sucesso !!!", Toast.LENGTH_LONG).show();

            //ENCERANDO A ACTIVITY
            finish();

            //REDIRECIONANDO PARA A TELA TODOS OS PROJETOS
            Intent it = new Intent(this, TodosProjetosAmostra.class);
            startActivity(it);
        }
    }

}
