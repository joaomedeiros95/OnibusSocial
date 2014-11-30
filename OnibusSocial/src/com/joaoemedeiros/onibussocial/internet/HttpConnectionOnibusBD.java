package com.joaoemedeiros.onibussocial.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.joaoemedeiros.onibussocial.bd.model.Pedido;
import com.joaoemedeiros.onibussocial.exceptions.UnconnectedException;

public class HttpConnectionOnibusBD {
	private String retorno = "";
	private Boolean erro = null;
	private int idUnique = -1;

	public void postLocalizacao(final int id, final int id_onibus,
			final double latitude, final double longitude) throws UnconnectedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://joaoemedeiros.com/services/onibus_social/set_localizacao.php");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair("id", String
						.valueOf(id)));
				nameValuePair.add(new BasicNameValuePair("onibus", String
						.valueOf(id_onibus)));
				nameValuePair.add(new BasicNameValuePair("localizacao", String
						.valueOf(latitude) + ":" + String.valueOf(longitude)));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				try {
					HttpResponse response = httpClient.execute(httpPost);
					Log.d("Http Response", response.toString());
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					setErro(true);
				}
				setErro(false);
			}
		}).start();
		while(isErro() == null);
		if(isErro())
			throw new UnconnectedException();
	}

	public void postPedido(final Pedido pedido) throws UnconnectedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://joaoemedeiros.com/services/onibus_social/set_pedidos.php");

				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair("nome", pedido
						.getNome()));
				nameValuePair.add(new BasicNameValuePair("email", pedido
						.getEmail()));
				nameValuePair.add(new BasicNameValuePair("cidade", pedido
						.getCidade()));
				nameValuePair.add(new BasicNameValuePair("estado", pedido
						.getEstado()));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				HttpResponse response;
				try {
					response = httpClient.execute(httpPost);

					Log.d("Http Response", response.toString());
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					setErro(true);
					e.printStackTrace();
				}
				setErro(false);
			}

		}).start();
		while(isErro() == null);
		if(isErro())
			throw new UnconnectedException();
	}

	public String getOnibus() throws UnconnectedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				InputStream inputStream = null;
				String result = "";

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(
							"http://joaoemedeiros.com/services/onibus_social/get_onibus.php");
					HttpResponse response = httpClient.execute(httpGet);
					inputStream = response.getEntity().getContent();
					if (inputStream != null) {
						result = converterInputStreamToString(inputStream);
					} else {
						result = null;
					}

					Log.d("Http Response", result);
					setRetorno(result);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					setErro(true);
					e.printStackTrace();
				}
				setErro(false);
			}
		}).start();
		while(isErro() == null);
		if(isErro())
			throw new UnconnectedException();
		return getRetorno();
	}

	public String getLocalizacao(int id_onibus) throws UnconnectedException {
		InputStream inputStream = null;
		String result = "";
		String url;
		if (id_onibus != 0)
			url = "http://joaoemedeiros.com/services/onibus_social/get_localizacao.php?onibus="
					+ id_onibus;
		else
			url = "http://joaoemedeiros.com/services/onibus_social/get_localizacao.php";

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response;
			response = httpClient.execute(httpGet);
			inputStream = response.getEntity().getContent();
			if (inputStream != null) {
				result = converterInputStreamToString(inputStream);
			} else {
				result = null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			setErro(true);
			e.printStackTrace();
		}
		if(erro == null)
			setErro(false);
		if(isErro())
			throw new UnconnectedException();
		return result;
	}

	public int getUniqueId() throws UnconnectedException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				InputStream inputStream = null;
				String result = "";
				String url = "http://joaoemedeiros.com/services/onibus_social/get_unique_id.php";

				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse response;
					response = httpClient.execute(httpGet);
					inputStream = response.getEntity().getContent();
					if (inputStream != null) {
						result = converterInputStreamToString(inputStream);
					} else {
						result = null;
					}
					if (result != null) {
						int retorno = 0;
						try {
							JSONObject jsonObj = new JSONObject(result);
							retorno = jsonObj.getInt(JSONConstantes.TAG_ID);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						setIdUnique(retorno);
					} else {
						setIdUnique(0);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					setErro(true);
					e.printStackTrace();
				}
				setErro(false);
			}
		}).start();
		while(isErro() == null);
		if(isErro())
			throw new UnconnectedException();		
		return getIdUnique();
	}

	private String converterInputStreamToString(InputStream inputStream) {
		String result = "";
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		try {
			while ((line = bufferedReader.readLine()) != null)
				result += line;
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public int getIdUnique() {
		return idUnique;
	}

	public void setIdUnique(int idUnique) {
		this.idUnique = idUnique;
	}

	public Boolean isErro() {
		return erro;
	}

	public void setErro(Boolean erro) {
		this.erro = erro;
	}
	
}
