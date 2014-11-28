package com.joaoemedeiros.onibussocial.mysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joaoemedeiros.onibussocial.bd.helper.SQLiteHandler;
import com.joaoemedeiros.onibussocial.bd.model.Acompanhador;

public class AcompanhadorDAO {
	
	private static AcompanhadorDAO instance = null;
	
	private static final String KEY_ID = "id";
	private static final String KEY_ID_ONIBUS = "id_onibus";
	private static final String KEY_LOCALIZACAO = "localizacao";
	private static final String TABLE_TRACKER = "tracker";
	private static final String LOG = "SQLite Database �nibus";
	
	 /**
     * Adds the acompanhador.
     *
     * @param acompanhador the acompanhador
     */
    public void addAcompanhador(Acompanhador acompanhador, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_ID, acompanhador.getId());
    	values.put(KEY_LOCALIZACAO, acompanhador.getLocalizacao());
    	values.put(KEY_ID_ONIBUS, acompanhador.getId_onibus());
    	
    	db.insert(TABLE_TRACKER, null, values);
    	
    }
    
    /**
     * Gets the acompanhador.
     *
     * @param id the id
     * @return the acompanhador
     */
    public Acompanhador getAcompanhador(int id, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
    	String selectQuery = "SELECT * FROM " + TABLE_TRACKER + " WHERE " + KEY_ID + " = " + id;
    	
    	Log.d(LOG, selectQuery);
    	
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	
    	if(cursor != null)
    		cursor.moveToFirst();
    	
    	Acompanhador acompanhador = new Acompanhador(cursor.getInt(cursor.getColumnIndex(KEY_ID)), 
    			cursor.getInt(cursor.getColumnIndex(KEY_ID_ONIBUS)), 
    			cursor.getString(cursor.getColumnIndex(KEY_LOCALIZACAO)));
    	
    	
    	
    	return acompanhador;
    }
    
    /**
     * Update acompanhador.
     *
     * @param acompanhador the acompanhador
     * @return the int
     */
    public int updateAcompanhador(Acompanhador acompanhador, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_LOCALIZACAO, acompanhador.getLocalizacao());
    	values.put(KEY_ID_ONIBUS, acompanhador.getId_onibus());
    	
    	int retorno = db.update(TABLE_TRACKER, values, KEY_ID + " = ?", 
    			new String[] { String.valueOf(acompanhador.getId()) });
    	
    	
    	return retorno;
    }
    
    /**
     * Delete acompanhador.
     *
     * @param acompanhador the acompanhador
     */
    public void deleteAcompanhador(Acompanhador acompanhador, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
    	db.delete(TABLE_TRACKER, KEY_ID + " = ?", 
    			new String[] { String.valueOf(acompanhador.getId()) });
    	
    }
	
	public AcompanhadorDAO getDAO() {
		if(instance == null) {
			instance = new AcompanhadorDAO();
		}
		return instance;
	}

}
