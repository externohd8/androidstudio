package br.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.banco.BancoDados;
import br.com.model.Arvore;
import br.com.model.DadosProjetoCenso;
import br.com.model.ProjetoCenso;

/**
 * Created by Fernando on 20/09/2016.
 */
public class DadosProjetoCensoDAO {

    //ACESSO AO BANCO DE DADOS
    SQLiteDatabase db;

    ArvoreDAO arvoreDAO;

    public DadosProjetoCensoDAO(Context context){
        //CHAMANDO A CLASSE DO BANCO
        db = BancoDados.getDB(context);
        this.arvoreDAO = new ArvoreDAO(context);
    }

    public void salvar(DadosProjetoCenso dadosProjetoCenso){
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put("id_projeto", dadosProjetoCenso.getProjetoCenso().getId());
        values.put("id_arvore", dadosProjetoCenso.getArvore().getId());
        values.put("cap", dadosProjetoCenso.getCap().toString());
        values.put("altura", dadosProjetoCenso.getAltura());
        values.put("data_cadastro", dateFormat.format(dadosProjetoCenso.getDataCadastro()));

        db.insert("dados_projeto_censo", null, values);
    }

    public List<DadosProjetoCenso> listarPorProjeto(String idProjeto){
        String[] colunas = new String[]{"id", "id_arvore", "altura", "cap", "id_projeto"};
        String[] args = new String[]{idProjeto};

        Cursor c = db.query("dados_projeto_censo", colunas, "id_projeto = ?", args, null, null, null);

        List<DadosProjetoCenso>  dpcs = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                DadosProjetoCenso dpc = new DadosProjetoCenso();

                dpc.setId(c.getLong(c.getColumnIndex("id")));
                dpc.setAltura(c.getDouble(c.getColumnIndex("altura")));
                dpc.setCap(c.getDouble(c.getColumnIndex("cap")));
                ProjetoCenso projetoCenso = new ProjetoCenso();
                projetoCenso.setId(c.getLong(c.getColumnIndex("id_projeto")));
                dpc.setProjetoCenso(projetoCenso);

                Arvore arvore = arvoreDAO.buscar(c.getLong(c.getColumnIndex("id_arvore")));
                dpc.setArvore(arvore);

                dpcs.add(dpc);
            } while (c.moveToNext());
        }

        c.close();

        return dpcs;
    }

    public int countArvoresPorProjeto(String idProjeto) {
        String query = "select count(*) as contador from dados_projeto_censo where id_projeto = ?";
        Cursor cursor = db.rawQuery(query, new String[] { idProjeto });

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex("contador"));
        }

        cursor.close();

        return count;
    }

    public void excluir(String id) {

        db.delete("dados_projeto_censo", "id = ?", new String[] {id});

    }

}
