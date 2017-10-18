package br.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.banco.BancoDados;
import br.com.model.Amostra;
import br.com.model.Arvore;
import br.com.model.DadosProjetoAmostra;

/**
 * Created by Fernando on 01/09/2016.
 * Esta classe é responsavel por gerenciar os processos de cadastros, updates, selects no db
 */
public class DadosProjetoAmostraDAO {

    //ACESSO AO BANCO DE DADOS
    SQLiteDatabase db;

    ArvoreDAO arvoreDAO;

    public DadosProjetoAmostraDAO(Context context){
        //CHAMANDO A CLASSE DO BANCO
        db = BancoDados.getDB(context);
        this.arvoreDAO = new ArvoreDAO(context);
    }

    public void salvar(DadosProjetoAmostra dadosProjetoAmostra){
        ContentValues values = new ContentValues();
        values.put("id_arvore", dadosProjetoAmostra.getArvore().getId());
        values.put("id_projeto", dadosProjetoAmostra.getProjetoAmostras().getId());
        values.put("id_amostra", dadosProjetoAmostra.getAmostra().getId());
        values.put("cap", dadosProjetoAmostra.getCap());
        values.put("altura", dadosProjetoAmostra.getAltura());

        db.insert("dados_projeto_amostra", null, values);

    }

    public List<DadosProjetoAmostra> listarPorAmostra(String idAmostra){
        String[] colunas = new String[]{"id", "id_arvore", "altura", "cap", "id_amostra", "id_projeto"};
        String[] args = new String[]{idAmostra};

        Cursor c = db.query("dados_projeto_amostra", colunas, "id_amostra = ?", args, null, null, null);

        List<DadosProjetoAmostra>  dpcs = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                DadosProjetoAmostra dpc = new DadosProjetoAmostra();
                Arvore arvore = arvoreDAO.buscar(c.getLong(c.getColumnIndex("id_arvore")));
                dpc.setArvore(arvore);
                dpc.setId(c.getLong(c.getColumnIndex("id")));

                Amostra amostra = new Amostra();
                amostra.setId(c.getLong(c.getColumnIndex("id_amostra")));
                dpc.setAmostra(amostra);

                dpc.setAltura(c.getDouble(c.getColumnIndex("altura")));
                dpc.setCap(c.getDouble(c.getColumnIndex("cap")));
                dpcs.add(dpc);
            } while (c.moveToNext());
        }

        c.close();

        return dpcs;
    }

    public List<DadosProjetoAmostra> listarPorAmostra(Long idAmostra) {
        return this.listarPorAmostra(idAmostra.toString());
    }

    public int countArvoresPorAmostra(String idAmostra){
        String query = "select count(*) as contador from dados_projeto_amostra where id_amostra = ?";
        Cursor cursor = db.rawQuery(query, new String[]{idAmostra});

        int count = 0;

        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("contador"));
        }

        cursor.close();

        return count;

    }

    public void excluir(String id) {

        db.delete("dados_projeto_amostra", "id = ?", new String[] {id});

    }

}
