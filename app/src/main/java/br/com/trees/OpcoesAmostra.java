package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.com.dao.AmostraDAO;
import br.com.dao.ArvoreDAO;
import br.com.dao.DadosProjetoAmostraDAO;
import br.com.model.Amostra;
import br.com.model.Arvore;
import br.com.model.DadosProjetoAmostra;
import br.com.validator.Validator;

/**
 * Created by Fernando on 18/09/2016.
 */
public class OpcoesAmostra extends Activity {

    //CRIANDO VARIAVEIS
    EditText txtCap, txtAltura;

    AutoCompleteTextView acBuscar;
    List<Arvore> arvores;
    ArrayAdapter<Arvore> adapter;
    ArvoreDAO arvoreDAO;
    Amostra amostra;

    //O RESOURCES SERVE PARA PASSAR OS CAMPOS PARA A VERIFICAÇÃO SE ESTÃO VAZIOS
    Resources resources;

    private AmostraDAO dao;
    private Arvore arvore;
    private DadosProjetoAmostraDAO dadosProjetoAmostraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_amostra);

        //INICIANDO OS TEXT FIELDS DA TELA
        txtAltura = (EditText) findViewById(R.id.txt_altura);
        txtCap = (EditText)findViewById(R.id.txt_cap);

        dadosProjetoAmostraDAO = new DadosProjetoAmostraDAO(this);

        //CONECTANDO AO BANCO
        dao = new AmostraDAO(this);
        //PEGANDO O ID PASSADO PELA TELA ANTERIOR
        Bundle extras = getIntent().getExtras();
        //PEGANDO O TEXTVIEW DA TELA
        TextView tvNomeProjeto = (TextView)findViewById(R.id.tv_nome_projeto);
        //CONSULTANDO O ID PASSADO NO BANCO E RETORNANDO O PROJETO ENCONTRADO
        amostra = dao.buscar(extras.getString("idAmostra"));
        //SETANDO O NOME DO PROJETO ENCONTRADO
        tvNomeProjeto.setText(amostra.getNome());

        //CONTADOR DE ARVORES
        int count = dadosProjetoAmostraDAO.countArvoresPorAmostra(amostra.getId().toString());
        TextView tvTotal = (TextView)findViewById(R.id.txt_cadastradas_amostra);
        tvTotal.setText(Integer.toString(count));

        //INFORMANDO O USUARIO DO PROJETO SELECIONADO
        //Toast.makeText(this, amostra.getId().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, amostra.getNome(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, amostra.getProjetoAmostra().getId().toString(), Toast.LENGTH_SHORT).show();


        //AUTO COMPLETE
        arvoreDAO = new ArvoreDAO(this);
        arvores = arvoreDAO.listar();
        adapter = new ArrayAdapter<>(this, R.layout.activity_search_arvore_item, arvores);

        acBuscar = (AutoCompleteTextView) findViewById(R.id.ac_buscar);
        acBuscar.setAdapter(adapter);
        acBuscar.setThreshold(1);

        acBuscar.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PEGANDO AS INFORMAÇÕES DA ARVORE ESCOLHIDA PELO CLIENTE
                arvore = (Arvore)((ListView) parent).getAdapter().getItem(position);
                //Toast.makeText(OpcoesCenso.this, arvore.getId().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(OpcoesAmostra.this, arvore.getNomeComum(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void cadastra_dado_amostra(View v){

        //VERIFICANDO SE OS CAMPOS ESTÃO VAZIOS
        if(Validator.validateEmptyField(this, acBuscar, txtAltura, txtCap)) {
            //GERANDO UM PROJETO
            DadosProjetoAmostra dadosProjetoAmostra = new DadosProjetoAmostra();
            dadosProjetoAmostra.setArvore(this.arvore);
            dadosProjetoAmostra.setAmostra(amostra);
            dadosProjetoAmostra.setProjetoAmostras(amostra.getProjetoAmostra());
            dadosProjetoAmostra.setAltura(Double.parseDouble(txtAltura.getText().toString()));
            dadosProjetoAmostra.setCap(Double.parseDouble(txtCap.getText().toString()));
            dadosProjetoAmostra.setDataCadastro(new Date());

            //SALVANDO O OBJETO
            dadosProjetoAmostraDAO.salvar(dadosProjetoAmostra);

            //LIMPANDO CAMPOS
            txtAltura.setText("");
            txtCap.setText("");

            //MENSAGEM DE SUCESSO
            Toast.makeText(this, "Árvore cadastrada com sucesso", Toast.LENGTH_LONG).show();

            //PARA QUE O COUNT CONTADOR DE ARVORES FUNCIONE PRECISA MATAR A ACTIVITY
            finish();
            //E INICIA-LA NOVAMENTE
            Intent intent = this.getIntent();
            intent.putExtra("idAmostra", this.amostra.getId().toString());
            startActivity(intent);

        }
    }


    public void ver_coleta_amostra(View v){
        Intent abre_coleta_amostra = new Intent(this, VerColetaAmostra.class);
        abre_coleta_amostra.putExtra("idAmostra", amostra.getId().toString());
        startActivity(abre_coleta_amostra);
    }





}
