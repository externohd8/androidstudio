package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void painel(View v){
        Intent abre_painel = new Intent(this, PainelActivity.class);
        startActivity(abre_painel);
        finish();
    }

}
