package com.joaoemedeiros.onibussocial;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Activity para a p�gina About.
 * @author Jo�oEduardo
 */

public class AboutActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        switch (id) {
		/*case R.id.action_settings:
			return true;*/
		case R.id.action_about:
			return true;
		case R.id.action_pedidos:
			Intent intent1 = new Intent(this, PedidosActivity.class);
			startActivity(intent1);
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }

}
