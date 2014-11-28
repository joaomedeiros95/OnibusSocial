/*
 * @author Jo�o Eduardo Medeiros
 * 
 */
package com.joaoemedeiros.onibussocial.bd.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The Class SQLiteHandler.
 *
 * @author Jo�oEduardo
 */
public class SQLiteHandler extends SQLiteOpenHelper {
	
	/** Vers�o da database. */
	private static final int DATABASE_VERSION = 2;
	
	/** Nome da database. */
	private static final String DATABASE_NAME = "onibusManager";
	
	/** Nome das tabelas. */
	private static final String TABLE_LOCALIZACAO = "localizacao";
	private static final String TABLE_ONIBUS = "onibus";
	private static final String TABLE_TRACKER = "tracker";
	
	/** Campos da tabela comuns. */
	private static final String KEY_ID = "id";
	private static final String KEY_ID_ONIBUS = "id_onibus";
	private static final String KEY_LOCALIZACAO = "localizacao";
    
    /** Campos da tabela Localizacao. */
    private static final String KEY_UA = "ultima_atualizacao";
    
    /** Campos da tabela �nibus. */
    private static final String KEY_LINHA = "linha";
    private static final String KEY_ITINERARIO = "itinerario";
    private static final String KEY_NOME_EMPRESA = "nomeEmpresa";
    private static final String KEY_CIDADE_EMPRESA = "cidadeEmpresa";
    private static final String KEY_ESTADO_EMPRESA = "estadoEmpresa";
    
    /** Comandos SQL para criar as tabelas. */
    /** Criar tabela localiza��o */
    private static final String CREATE_TABLE_LOCALIZACAO = "CREATE TABLE " + TABLE_LOCALIZACAO + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_ONIBUS + " INTEGER," + KEY_LOCALIZACAO + " TEXT,"
            + KEY_UA + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
    /** Criar tabela �nibus. */
    private static final String CREATE_TABLE_ONIBUS = "CREATE TABLE " + TABLE_ONIBUS + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINHA + " TEXT," + KEY_ITINERARIO + " TEXT,"
    		+ KEY_NOME_EMPRESA + " TEXT," + KEY_CIDADE_EMPRESA + " TEXT," + KEY_ESTADO_EMPRESA + " TEXT" + ")";
    /** Criar tabela tracker. */
    private static final String CREATE_TABLE_TRACKER = "CREATE TABLE " + TABLE_TRACKER + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_ID_ONIBUS + " INTEGER," + KEY_LOCALIZACAO + " TEXT" + ")";
    
    private static SQLiteHandler instance = null;
    
    /**
     * Instantiates a new SQ lite handler.
     *
     * @param context the context
     */
    public SQLiteHandler(Context context) {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCALIZACAO);
        db.execSQL(CREATE_TABLE_ONIBUS);
        db.execSQL(CREATE_TABLE_TRACKER);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALIZACAO);
	     db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONIBUS);
	     db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKER);
	 
	     // criar novamente as tabelas
	     onCreate(db);
	}
	
	/**
	 * Padrão singleton.
	 * @param context
	 * @return
	 */
	public static SQLiteHandler getSQLite(Context context) {
		if(instance == null) {
			instance = new SQLiteHandler(context);
		}
		return instance;
	}
    
	/**
	 * Close db.
	 */
	public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
