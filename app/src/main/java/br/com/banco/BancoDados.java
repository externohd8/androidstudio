package br.com.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BancoDados {

	// Nome do banco
	private static final String NOME_BANCO = "collect_trees";
	// Controle de versao
	private static final int VERSAO_BANCO = 1;
	// Script para fazer drop na tabela
	private static final String[] SCRIPT_DATABASE_DELETE = new String[] { "DROP TABLE IF EXISTS projeto_censo;","DROP TABLE IF EXISTS projeto_amostra;", "DROP TABLE IF EXISTS amostras;", "DROP TABLE EXISTS dados_projeto_censo;", "DROP TABLE EXISTS dados_projeto_amostra;", "DROP TABLE EXISTS arvore;" };

	// Cria a tabela com o "_id" sequencial
	private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
            "create table if not exists projeto_censo(id integer primary key, nome text, area_inventariada double, data_cadastro date, status text);",
			"create table if not exists projeto_amostra(id integer primary key, nome text, area_inventariada double, data_cadastro date, indice_confianca double, status text);",
			"create table if not exists amostras(id integer primary key, id_projeto integer, nome text, tamanho double, data_cadastro date, status text, foreign key(id_projeto) references projeto_amostra(id));",
			"create table if not exists arvore(id integer primary key, nome_comum text, nome_cientifico text, familia text, fator_forma double);",
			"create table if not exists dados_projeto_amostra(id primary key, id_arvore integer, id_projeto integer, id_amostra integer, cap double, altura double, data_cadastro date, foreign key(id_projeto) references projeto_amostra(id), foreign key(id_arvore) references arvore(id), foreign key(id_amostra) references amostra(id));",
			"create table if not exists dados_projeto_censo(id integer primary key, id_projeto integer, id_arvore integer, cap double, altura double, data_cadastro date, foreign key(id_projeto) references projeto_censo(id), foreign key(id_arvore) references arvore(id));"
	};

	private static SQLiteDatabase db;

	public static SQLiteDatabase getDB(Context ctx) {
		if (db == null) {			
			SQLiteHelper dbHelper = new SQLiteHelper(ctx, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
			db = dbHelper.getWritableDatabase();
		}
		return db;
		
	}

}