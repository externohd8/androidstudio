package br.com.trees;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.builder.ArvoreBuilder;

/**
 * Created by Fernando on 22/04/2016.
 */
public class PainelActivity extends Activity{

    ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ESTE METODO É RESPONSAVEL POR VERIFICAR SE EXISTEM ARVORES CADASTRADAS NO SISTEMA
        //ELE RETORNA UMA MENSAGEM AO USUARIO
        load = ProgressDialog.show(this, "Por favor, aguarde ...", "Carregando informações.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArvoreBuilder.build(PainelActivity.this);
                load.dismiss();
            }
        }).start();

        setContentView(R.layout.activity_painel);
    }

    public void novo_projeto_censo(View v){
        Intent abre_novo_censo = new Intent(this, NovoProjetoCenso.class);
        startActivity(abre_novo_censo);
    }

    public void novo_projeto_amostras(View v){
        Intent abre_novo_amostras = new Intent(this, NovoProjetoAmostra.class);
        startActivity(abre_novo_amostras);
    }

    public void todos_projetos_censo(View v){
        Intent abre_todos_projetos_censo = new Intent(this, TodosProjetosCenso.class);
        startActivity(abre_todos_projetos_censo);
    }

    public void todos_projetos_amostras(View v){
        Intent abre_todos_projetos_amostras  = new Intent(this, TodosProjetosAmostra.class);
        startActivity(abre_todos_projetos_amostras);
    }



}
