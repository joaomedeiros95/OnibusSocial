package com.joaoemedeiros.onibussocial.mysql;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joaoemedeiros.onibussocial.bd.helper.SQLiteHandler;
import com.joaoemedeiros.onibussocial.bd.model.Onibus;
import com.joaoemedeiros.onibussocial.internet.HttpConnectionOnibusBD;
import com.joaoemedeiros.onibussocial.internet.JSONConstantes;

public class OnibusDAO {
	
	private static OnibusDAO instance = null;
	
	private static final String LOG = "SQLite Database �nibus";
	private static final String KEY_ID = "id";
	private static final String KEY_LINHA = "linha";
    private static final String KEY_ITINERARIO = "itinerario";
    private static final String KEY_NOME_EMPRESA = "nomeEmpresa";
    private static final String KEY_CIDADE_EMPRESA = "cidadeEmpresa";
    private static final String KEY_ESTADO_EMPRESA = "estadoEmpresa";
    private static final String TABLE_ONIBUS = "onibus";
    
    private static final String CREATE_TABLE_ONIBUS = "CREATE TABLE " + TABLE_ONIBUS + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_LINHA + " TEXT," + KEY_ITINERARIO + " TEXT,"
    		+ KEY_NOME_EMPRESA + " TEXT," + KEY_CIDADE_EMPRESA + " TEXT," + KEY_ESTADO_EMPRESA + " TEXT" + ")";
    
	/**
     * Adds the onibus.
     *
     * @param onibus the onibus
     */
    public void addOnibus(Onibus onibus, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
       
        ContentValues values = new ContentValues();
        values.put(KEY_ID, onibus.getId());
        values.put(KEY_LINHA, onibus.getLinha());
        values.put(KEY_ITINERARIO, onibus.getItinerario());
        values.put(KEY_NOME_EMPRESA, onibus.getNomeEmpresa());
        values.put(KEY_CIDADE_EMPRESA, onibus.getCidadeEmpresa());
        values.put(KEY_ESTADO_EMPRESA, onibus.getEstadoEmpresa());
       
        db.insert(TABLE_ONIBUS, null, values);
        
    }
    
    /**
     * Gets the onibus.
     *
     * @param id the id
     * @return the onibus
     */
    public Onibus getOnibus(int id, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ONIBUS + " WHERE " + KEY_ID + " = " + id;
       
        Log.d(LOG, selectQuery);
       
        Cursor cursor = db.rawQuery(selectQuery, null);
       
        if (cursor != null)
        	cursor.moveToFirst();
 
        Onibus onibus = new Onibus(cursor.getInt(cursor.getColumnIndex(KEY_ID)), 
        		cursor.getString(cursor.getColumnIndex(KEY_LINHA)), 
        		cursor.getString(cursor.getColumnIndex(KEY_ITINERARIO)), 
        		cursor.getString(cursor.getColumnIndex(KEY_NOME_EMPRESA)), 
        		cursor.getString(cursor.getColumnIndex(KEY_CIDADE_EMPRESA)), 
        		cursor.getString(cursor.getColumnIndex(KEY_ESTADO_EMPRESA)));
        
        
        // return localiza��o
        return onibus;
    }
    
    /**
     * Gets the all onibus.
     *
     * @return the all onibus
     */
    public List<Onibus> getAllOnibus(Context context) {
        List<Onibus> onibusList = new ArrayList<Onibus>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ONIBUS;

        Log.d(LOG, selectQuery);

        SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        	do {
        		Onibus onibus = new Onibus(cursor.getInt(cursor.getColumnIndex(KEY_ID)), 
                		cursor.getString(cursor.getColumnIndex(KEY_LINHA)), 
                		cursor.getString(cursor.getColumnIndex(KEY_ITINERARIO)), 
                		cursor.getString(cursor.getColumnIndex(KEY_NOME_EMPRESA)), 
                		cursor.getString(cursor.getColumnIndex(KEY_CIDADE_EMPRESA)), 
                		cursor.getString(cursor.getColumnIndex(KEY_ESTADO_EMPRESA)));
        		// Adding localiza��o to list
        		onibusList.add(onibus);
        	} while (cursor.moveToNext());
        }
        
        

        // return localiza��o list
        return onibusList;
    }
    
    /**
     * Update onibus.
     *
     * @param onibus the onibus
     * @return the int
     */
    public int updateOnibus(Onibus onibus, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_LINHA, onibus.getLinha());
        values.put(KEY_ITINERARIO, onibus.getItinerario());
        values.put(KEY_NOME_EMPRESA, onibus.getNomeEmpresa());
        values.put(KEY_CIDADE_EMPRESA, onibus.getCidadeEmpresa());
        values.put(KEY_ESTADO_EMPRESA, onibus.getEstadoEmpresa());
 
        // updating ro
        int retorno = db.update(TABLE_ONIBUS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(onibus.getId()) });
        
        return retorno;
    }
    
    /**
     * Delete onibus.
     *
     * @param onibus the onibus
     */
    public void deleteOnibus(Onibus onibus, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
        db.delete(TABLE_ONIBUS, KEY_ID + " = ?",
                new String[] { String.valueOf(onibus.getId()) });
        
    }
    
    /**
     * Gets the size onibus.
     *
     * @return the size onibus
     */
    public int getSizeOnibus(Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
    	String countQuery = "SELECT  * FROM " + TABLE_ONIBUS;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        
 
        // return count
        return cursor.getCount();
    }
    
    /**
     * Delete all onibus.
     */
    public void deleteAllOnibus(Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
    	db.delete(TABLE_ONIBUS, null, null);
    	
    }
    
    /**
     * Checks if is row exist onibus.
     *
     * @param id the id
     * @return true, if is row exist onibus
     */
    public boolean isRowExistOnibus(int id, Context context) {
    	SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getReadableDatabase();
    	String countQuery = "SELECT  * FROM " + TABLE_ONIBUS + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        
        
        if(cursor.getCount() >= 1)
        	return true;
        else
        	return false;
    }
    
    /**
	 * Apaga todos os valores da tabela �nibus e cria novamente.
	 */
	public void resetOnibusDB(Context context) {
		SQLiteHandler handler = SQLiteHandler.getSQLite(context);
		SQLiteDatabase db = handler.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ONIBUS);
		db.execSQL(CREATE_TABLE_ONIBUS);
	}
    
    
    public void populate(Context context) {
    	resetOnibusDB(context);
    	
    	HttpConnectionOnibusBD httpcobd = new HttpConnectionOnibusBD();
		JSONArray onibus = new JSONArray();
		String resultadoJSON = httpcobd.getOnibus();
		List<Onibus> onibusASeremEnviados = new ArrayList<Onibus>();
		
		if(resultadoJSON != null) {
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(resultadoJSON);
				onibus = jsonObj.getJSONArray(JSONConstantes.TAG_PRODUCT);
				
				for (int i = 0; i < onibus.length(); i++) {
					JSONObject c = onibus.getJSONObject(i);
					
					Onibus bus = new Onibus();
					bus.setId(Integer.parseInt(c.getString(JSONConstantes.TAG_ID)));
					bus.setLinha(c.getString(JSONConstantes.TAG_LINHA));
//					bus.setItinerario(c.getString(JSONConstantes.TAG_ITINERARIO));
					bus.setNomeEmpresa(c.getString(JSONConstantes.TAG_EMPRESA));
					bus.setCidadeEmpresa(c.getString(JSONConstantes.TAG_CIDADE));
					bus.setEstadoEmpresa(c.getString(JSONConstantes.TAG_ESTADO));
					
					onibusASeremEnviados.add(bus);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * CRUD Operations
		 * */
		for(Onibus bus : onibusASeremEnviados) {
			Log.d("Insert: ", "Inserting.. " + bus);
			addOnibus(bus, context);
		}
    }
	
	public static OnibusDAO getDAO() {
		if(instance == null) {
			instance = new OnibusDAO();
		}
		return instance;
	}

}
