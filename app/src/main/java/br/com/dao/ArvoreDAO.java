package br.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.banco.BancoDados;
import br.com.model.Arvore;

/**
 * Created by Fernando on 11/09/2016.
 */
public class ArvoreDAO {

    SQLiteDatabase db;

    private final String[] colunas = {"id", "nome_comum", "nome_cientifico", "familia", "fator_forma"};

    public ArvoreDAO(Context context) {
        db = BancoDados.getDB(context);
    }

    public void salvar(Arvore arvore) {
        ContentValues values = new ContentValues();
        values.put(this.colunas[1], arvore.getNomeComum());
        values.put(this.colunas[2], arvore.getNomeCientifico());
        values.put(this.colunas[3], arvore.getFamilia());
        values.put(this.colunas[4], arvore.getFatorForma());

        db.insert("arvore", null, values);
    }

    public Arvore buscar(String id) {
        String[] colunas = new String[] {"id", "nome_comum", "nome_cientifico", "familia", "fator_forma"};
        String[] args = new String[]{id};
        Cursor c = db.query("arvore", colunas, "id = ?", args, null, null, null);

        Arvore arvore = null;

        if (c.moveToFirst()) {
            arvore = new Arvore();
            arvore.setId(c.getLong(c.getColumnIndex("id")));
            arvore.setNomeComum(c.getString(c.getColumnIndex("nome_comum")));
            arvore.setNomeCientifico(c.getString(c.getColumnIndex("nome_cientifico")));
            arvore.setFamilia(c.getString(c.getColumnIndex("familia")));
            arvore.setFatorForma(c.getDouble(c.getColumnIndex("fator_forma")));
        }

        return arvore;
    }

    public Arvore buscar(Long id) {
        return this.buscar(id.toString());
    }

    public boolean hasArvores() {
        long cnt  = DatabaseUtils.queryNumEntries(db, "arvore");
        return cnt > 0;
    }

    public List<Arvore> listar(){

        String[] colunas = new String[]{"id", "nome_comum", "nome_cientifico", "familia", "fator_forma"};
        List<Arvore> arvores;
        Cursor c = db.query("arvore", colunas, null, null, null, null, null);

        arvores = new ArrayList<Arvore>();
        if(c.moveToFirst()){
            do {
                Arvore arvore = new Arvore();
                arvore.setId(c.getLong(c.getColumnIndex("id")));
                arvore.setNomeComum(c.getString(c.getColumnIndex("nome_comum")));
                arvore.setNomeCientifico(c.getString(c.getColumnIndex("nome_cientifico")));
                arvore.setFamilia(c.getString(c.getColumnIndex("familia")));
                arvore.setFatorForma(c.getDouble(c.getColumnIndex("fator_forma")));

                arvores.add(arvore);
            }while (c.moveToNext());
        }
        c.close();
        return arvores;
    }

}
