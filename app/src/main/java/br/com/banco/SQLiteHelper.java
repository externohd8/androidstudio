package br.com.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	private String[] scriptSQLCreate;
	private String[] scriptSQLDelete;


	public SQLiteHelper(Context context, String name, int version, String[] scriptSQLCreate, String[] scriptSQLDelete) {
		super(context, name, null, version);
		
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
	}

	@Override
	/**
	 * Cria o banco de dados
	 */
	
	public void onCreate(SQLiteDatabase db) {
		int qtdeScripts = scriptSQLCreate.length;
		// Executa cada sql passado como par???metro
		for (int i = 0; i < qtdeScripts; i++) {
			db.execSQL(scriptSQLCreate[i]);
		}
	}

	@Override
	
	/**
	 * Atualiza o banco da dados
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		int qtdeScripts = scriptSQLDelete.length;
		// Executa cada sql passado como par???metro
		for (int i = 0; i < qtdeScripts; i++) {
			db.execSQL(scriptSQLDelete[i]);
		}
		onCreate(db);

	}

}
