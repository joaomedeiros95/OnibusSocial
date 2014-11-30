package com.joaoemedeiros.onibussocial;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joaoemedeiros.onibussocial.bd.model.Pedido;
import com.joaoemedeiros.onibussocial.exceptions.UnconnectedException;
import com.joaoemedeiros.onibussocial.internet.HttpConnectionOnibusBD;

public class PedidosActivity extends ActionBarActivity {
	
	private EditText editNome;
	private EditText editEmail;
	private EditText editCidade;
	private EditText editEstado;
	private Button enviar;
	private List<String> mensagens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedidos);
		enviar = (Button) findViewById(R.id.button1);
		editNome = (EditText) findViewById(R.id.editText1);
		editEmail = (EditText) findViewById(R.id.editText2);
		editCidade = (EditText) findViewById(R.id.editText3);
		editEstado = (EditText) findViewById(R.id.editText4);
		mensagens = new ArrayList<String>();
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		enviar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Pedido pedido = new Pedido(editNome.getText().toString(), editEmail.getText().toString(), 
										   editCidade.getText().toString(), editEstado.getText().toString());
				if(validate(pedido)) {
					HttpConnectionOnibusBD http = new HttpConnectionOnibusBD();
					try {
						http.postPedido(pedido);
						Toast.makeText(getApplicationContext(), "Pedido enviado!", Toast.LENGTH_LONG).show();
						finish();
					} catch (UnconnectedException e) {
						e.printStackTrace();
						Toast.makeText(getApplicationContext(), "OnibusSocial: Verifique sua conexão com Internet", Toast.LENGTH_LONG).show();
						MainActivity.isConnected();
					}
				} else {
					for(String mensagem : mensagens) 
						Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
				}
			}

		});
	}
	
	private boolean validate(Pedido pedido) {
		boolean retorno = true;
		mensagens.clear();
		if(pedido.getCidade() == null || pedido.getCidade().isEmpty()) {
			retorno = false;
			mensagens.add("O campo cidade é obrigatório.");
		}
		if(pedido.getEstado() == null || pedido.getEstado().isEmpty()) {
			retorno = false;
			mensagens.add("O campo estado é obrigatório.");
		}
		if(pedido.getEmail() != null && !pedido.getEmail().isEmpty()) {
			if(!emailValidate(pedido.getEmail())) {
				retorno = false;
				mensagens.add("O seu email não está em um formato válido.");
			}
		}
		return retorno;
	}

	private boolean emailValidate(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		if(id ==  R.id.action_about) {
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			return true;
		}
		if(id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
