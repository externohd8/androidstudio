package br.com.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.banco.BancoDados;
import br.com.enums.Status;
import br.com.model.ProjetoAmostras;

/**
 * Created by Fernando on 02/08/2016.
 * Esta classe é responsavel por gerenciar os processos de cadastros, updates, selects no db
 */
public class ProjetoAmostrasDAO {

    SQLiteDatabase db;

    public ProjetoAmostrasDAO(Context context){
        db = BancoDados.getDB(context);
    }

    public void salvar(ProjetoAmostras projetoAmostras){
        ContentValues values = new ContentValues();
        //POPULANDO O OBJETO
        values.put("nome", projetoAmostras.getNome());
        values.put("area_inventariada", projetoAmostras.getAreaInventariada());
        values.put("indice_confianca", projetoAmostras.getIndiceConfianca());
        values.put("status", projetoAmostras.getStatus());
        //GRAVANDO NO BANCO
        db.insert("projeto_amostra", null, values);
    }

    public List<ProjetoAmostras> listar(){

        String[] colunas = new String[]{"id", "nome", "area_inventariada", "indice_confianca", "status"};
        List<ProjetoAmostras> projetoAmostras;
        Cursor c = db.query("projeto_amostra", colunas, null, null, null,null, null);

        projetoAmostras = new ArrayList<ProjetoAmostras>();
        if(c.moveToFirst()){
            do {
                ProjetoAmostras pa = new ProjetoAmostras();
                pa.setId(c.getLong(c.getColumnIndex("id")));
                pa.setNome(c.getString(c.getColumnIndex("nome")));
                pa.setAreaInventariada(c.getDouble(c.getColumnIndex("area_inventariada".toString())));
                pa.setIndiceConfianca(c.getDouble(c.getColumnIndex("indice_confianca".toString())));
                pa.setStatus(c.getString(c.getColumnIndex("status")));

                projetoAmostras.add(pa);
            }while (c.moveToNext());
        }
        c.close();
        return projetoAmostras;
    }

    public ProjetoAmostras buscar(String id){
        String[] colunas = new String[]{"id", "nome", "area_inventariada", "indice_confianca","status"};
        String[] args = new String[]{ id };

        Cursor c = db.query("projeto_amostra", colunas, "id = ?", args, null, null, null, null);

        c.moveToFirst();
        ProjetoAmostras projetoAmostras = new ProjetoAmostras();
        projetoAmostras.setId(c.getLong(c.getColumnIndex("id")));
        projetoAmostras.setNome(c.getString(c.getColumnIndex("nome")));
        projetoAmostras.setAreaInventariada(c.getDouble(c.getColumnIndex("area_inventariada".toString())));
        projetoAmostras.setIndiceConfianca(c.getDouble(c.getColumnIndex("indice_confianca".toString())));
        projetoAmostras.setStatus(c.getString(c.getColumnIndex("status")));

        c.close();

        return projetoAmostras;
    }

    public ProjetoAmostras buscar(Long id) {
        return this.buscar(id.toString());
    }

    public void alterar(ProjetoAmostras projetoAmostras){
        ContentValues values = new ContentValues();
        values.put("id", projetoAmostras.getId());
        values.put("nome", projetoAmostras.getNome());
        values.put("area_inventariada", projetoAmostras.getAreaInventariada());
        values.put("indice_confianca", projetoAmostras.getIndiceConfianca());
        values.put("status", projetoAmostras.getStatus());

        db.update("projeto_amostra", values, "id = ?", new String[]{
                projetoAmostras.getId().toString()
        });
    }

    public void alterarStatus(Long idProjeto, Status novoStatus) {
        ContentValues values = new ContentValues();
        values.put("status", novoStatus.toString());

        db.update("projeto_amostra", values, "id = ?", new String[] {
                idProjeto.toString()
        });
    }

}
