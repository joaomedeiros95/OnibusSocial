package com.joaoemedeiros.onibussocial.mysql;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joaoemedeiros.onibussocial.bd.helper.SQLiteHandler;
import com.joaoemedeiros.onibussocial.bd.model.Localizacao;

public class LocalizacaoDAO {

	private static LocalizacaoDAO instance = null;
	
	private static final String KEY_ID = "id";
	private static final String KEY_ID_ONIBUS = "id_onibus";
	private static final String KEY_LOCALIZACAO = "localizacao";
	private static final String KEY_UA = "ultima_atualizacao";
	private static final String TABLE_LOCALIZACAO = "localizacao";
	private static final String LOG = "SQLite Database �nibus";

	// adicionando localiza��o
	public void addLocalizacao(Localizacao localizacao, Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, localizacao.getId());
		values.put(KEY_ID_ONIBUS, localizacao.getId_onibus());
		values.put(KEY_LOCALIZACAO, localizacao.getLocalizacao());
		values.put(KEY_UA, localizacao.getUltima_atualizacao());

		db.insert(TABLE_LOCALIZACAO, null, values);

	}

	/**
	 * Gets the localizacao.
	 * 
	 * @param id
	 *            the id
	 * @return the localizacao
	 */
	public Localizacao getLocalizacao(int id, Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_LOCALIZACAO + " WHERE "
				+ KEY_ID + " = " + id;

		Log.d(LOG, selectQuery);

		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor != null)
			cursor.moveToFirst();

		Localizacao localizacao = new Localizacao(cursor.getInt(cursor
				.getColumnIndex(KEY_ID)), cursor.getInt(cursor
				.getColumnIndex(KEY_ID_ONIBUS)), cursor.getString(cursor
				.getColumnIndex(KEY_LOCALIZACAO)), cursor.getString(cursor
				.getColumnIndex(KEY_UA)));

		// return localiza��o
		return localizacao;
	}

	/**
	 * Gets the all localizacao.
	 * 
	 * @return the all localizacao
	 */
	public List<Localizacao> getAllLocalizacao(Context context) {
		List<Localizacao> localizacaoList = new ArrayList<Localizacao>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_LOCALIZACAO;

		Log.d(LOG, selectQuery);

		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Localizacao localizacao = new Localizacao();
				localizacao.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				localizacao.setId_onibus(cursor.getInt(cursor
						.getColumnIndex(KEY_ID_ONIBUS)));
				localizacao.setLocalizacao(cursor.getString(cursor
						.getColumnIndex(KEY_LOCALIZACAO)));
				localizacao.setUltima_atualizacao(cursor.getString(cursor
						.getColumnIndex(KEY_UA)));
				// Adding localiza��o to list
				localizacaoList.add(localizacao);
			} while (cursor.moveToNext());
		}

		// return localiza��o list
		return localizacaoList;
	}

	/**
	 * Gets the all localizacao by onibus.
	 * 
	 * @param id_onibus
	 *            the id_onibus
	 * @return the all localizacao by onibus
	 */
	public List<Localizacao> getAllLocalizacaoByOnibus(int id_onibus, Context context) {
		List<Localizacao> localizacaoList = new ArrayList<Localizacao>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_LOCALIZACAO + " WHERE "
				+ KEY_ID_ONIBUS + " = " + id_onibus;

		Log.d(LOG, selectQuery);

		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Localizacao localizacao = new Localizacao();
				localizacao.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				localizacao.setId_onibus(cursor.getInt(cursor
						.getColumnIndex(KEY_ID_ONIBUS)));
				localizacao.setLocalizacao(cursor.getString(cursor
						.getColumnIndex(KEY_LOCALIZACAO)));
				localizacao.setUltima_atualizacao(cursor.getString(cursor
						.getColumnIndex(KEY_UA)));
				// Adding localiza��o to list
				localizacaoList.add(localizacao);
			} while (cursor.moveToNext());
		}

		// return localiza��o list
		return localizacaoList;
	}

	/**
	 * Update localizacao.
	 * 
	 * @param localizacao
	 *            the localizacao
	 * @return the int
	 */
	public int updateLocalizacao(Localizacao localizacao, Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID_ONIBUS, localizacao.getId_onibus());
		values.put(KEY_LOCALIZACAO, localizacao.getLocalizacao());
		values.put(KEY_UA, localizacao.getUltima_atualizacao());

		int retorno = db.update(TABLE_LOCALIZACAO, values, KEY_ID + " = ?",
				new String[] { String.valueOf(localizacao.getId()) });

		// updating row
		return retorno;
	}

	/**
	 * Delete localizacao.
	 * 
	 * @param localizacao
	 *            the localizacao
	 */
	public void deleteLocalizacao(Localizacao localizacao, Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
		db.delete(TABLE_LOCALIZACAO, KEY_ID + " = ?",
				new String[] { String.valueOf(localizacao.getId()) });

	}
	
	/**
     * Gets the size.
     *
     * @return the size
     */
    public int getSizeLocalizacao(Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
        String countQuery = "SELECT  * FROM " + TABLE_LOCALIZACAO;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        
 
        // return count
        return cursor.getCount();
    }
    
    /**
     * Delete all.
     */
    public void deleteAllLocalizacao(Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
    	db.delete(TABLE_LOCALIZACAO, null, null);
    	
    }
    
    /**
     * Checks if is row exist.
     *
     * @param id the id
     * @return true, if is row exist
     */
    public boolean isRowExistLocalizacao(int id, Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
    	String countQuery = "SELECT  * FROM " + TABLE_LOCALIZACAO + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        
        
        if(cursor.getCount() >= 1)
        	return true;
        else
        	return false;
    }

	public LocalizacaoDAO getDAO() {
		if (instance == null) {
			instance = new LocalizacaoDAO();
		}
		return instance;
	}

}
