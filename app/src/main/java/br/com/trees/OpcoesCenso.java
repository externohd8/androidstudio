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

import br.com.dao.ArvoreDAO;
import br.com.dao.DadosProjetoCensoDAO;
import br.com.dao.ProjetoCensoDAO;
import br.com.model.Arvore;
import br.com.model.DadosProjetoCenso;
import br.com.model.ProjetoCenso;
import br.com.validator.Validator;

public class OpcoesCenso extends Activity {

    //CRIANDO VARIAVEIS
    EditText txtCap, txtAltura;

    AutoCompleteTextView acBuscar;
    List<Arvore> arvores;
    ArrayAdapter<Arvore> adapter;
    ArvoreDAO arvoreDAO;
    private ProjetoCensoDAO dao;
    private DadosProjetoCensoDAO dadosProjetoCensoDAO;
    private ProjetoCenso projetoCenso;
    private Arvore arvore;

    //O RESOURCES SERVE PARA PASSAR OS CAMPOS PARA A VERIFICAÇÃO SE ESTÃO VAZIOS
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_censo);

        //INICIALIZANDO OS TEXTFIELDS DA TELA
        txtCap = (EditText) findViewById(R.id.txt_cap);
        txtAltura = (EditText)findViewById(R.id.txt_altura);


        //CONECTANDO AO BANCO
        dao = new ProjetoCensoDAO(this);
        dadosProjetoCensoDAO = new DadosProjetoCensoDAO(this);

        //PEGANDO O ID PASSADO PELA TELA ANTERIOR
        Bundle extras = getIntent().getExtras();
        //PEGANDO O TEXTVIEW DA TELA
        TextView tvNomeProjeto = (TextView)findViewById(R.id.tv_nome_projeto);
        //CONSULTANDO O ID PASSADO NO BANCO E RETORNANDO O PROJETO ENCONTRADO
        projetoCenso = dao.buscar(extras.getString("idProjetoCenso"));
        //SETANDO O NOME DO PROJETO ENCONTRADO
        tvNomeProjeto.setText(projetoCenso.getNome().toString());

        //ESTE METODO CONTA QUANTAS ARVORES FORMA CADASTRADOS DE ACORDO COM O PROJETO PASSADO POR PARAMETRO
        int count = dadosProjetoCensoDAO.countArvoresPorProjeto(projetoCenso.getId().toString());
        //Toast.makeText(this, Integer.toString(count), Toast.LENGTH_LONG).show();
        TextView tvTotal = (TextView)findViewById(R.id.txt_cadastradas);
        //MOSTRANDO NA TELA DO USUARIO
        tvTotal.setText(Integer.toString(count));


        //INFORMANDO O USUARIO DO PROJETO SELECIONADO
        //Toast.makeText(this, projetoCenso.getId().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, projetoCenso.getNome().toString(), Toast.LENGTH_SHORT).show();

        //AUTO COMPLETE
        arvoreDAO = new ArvoreDAO(this);
        arvores = arvoreDAO.listar();
        adapter = new ArrayAdapter<>(this, R.layout.activity_search_arvore_item, arvores);

        acBuscar = (AutoCompleteTextView) findViewById(R.id.ac_buscar);
        acBuscar.setAdapter(adapter);
        acBuscar.setThreshold(1);

        acBuscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //PEGANDO AS INFORMAÇÕES DA ARVORE ESCOLHIDA PELO CLIENTE
                arvore = (Arvore)((ListView) parent).getAdapter().getItem(position);
                //Toast.makeText(OpcoesCenso.this, arvore.getId().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(OpcoesCenso.this, arvore.getNomeComum(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    public void cadastra_dado_censo(View v){
        //VERIFICANDO CAMPOS VAZIOS
        if(Validator.validateEmptyField(this, acBuscar, txtCap, txtAltura)) {
            //GERANDO UM PROJETO
            DadosProjetoCenso dadosProjetoCenso = new DadosProjetoCenso();
            dadosProjetoCenso.setArvore(this.arvore);
            dadosProjetoCenso.setProjetoCenso(this.projetoCenso);
            dadosProjetoCenso.setCap(Double.parseDouble(txtCap.getText().toString()));
            dadosProjetoCenso.setAltura(Double.parseDouble(txtAltura.getText().toString()));
            dadosProjetoCenso.setDataCadastro(new Date());

            //SALVANDO O OBJETO
            dadosProjetoCensoDAO.salvar(dadosProjetoCenso);

            //MENSAGEM DE SUCESSO
            Toast.makeText(this, "Árvore cadastrada com sucesso", Toast.LENGTH_LONG).show();

            //PARA QUE O COUNT CONTADOR DE ARVORES FUNCIONE PRECISA MATAR A ACTIVITY
            finish();
            //E INICIA-LA NOVAMENTE
            Intent intent = this.getIntent();
            intent.putExtra("idProjetoCenso", this.projetoCenso.getId().toString());
            startActivity(intent);
        }
    }

    public void ver_coleta(View v){
        Intent abre_coleta = new Intent(this, VerColetaCenso.class);
        abre_coleta.putExtra("idProjetoCenso", projetoCenso.getId());
        startActivity(abre_coleta);
    }


}
