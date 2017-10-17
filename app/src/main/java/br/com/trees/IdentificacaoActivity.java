package br.com.trees;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import br.com.model.JSON;
import br.com.model.Result;
import br.com.model.Send;
import br.com.model.Usuario;
import br.com.services.HttpConnection;
import br.com.validator.Validator;

/**
 * Created by Fernando on 15/10/2016.
 * Está tela serve para identificar o usuario quando vai se conectar ao sistema web
 */
public class IdentificacaoActivity extends Activity {

    //PEGANDO AS VARIAVEIS
    EditText txtLogin, txtSenha;
    private Send send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        this.send = Send.fromExtra(extras);

        txtLogin = (EditText) findViewById(R.id.txt_login);
        txtSenha = (EditText) findViewById(R.id.txt_senha);
    }

    public void sendJson(View v){
        //VERIFICANDO SE OS CAMPOS ESTÃO VAZIOS
        if(Validator.validateEmptyField(this, txtLogin, txtSenha)) {
            try {
                Usuario usuario = new Usuario();
                usuario.setLogin(txtLogin.getText().toString());
                usuario.setSenha(txtSenha.getText().toString());
                String json = JSON.toString(usuario);
                this.send.setParams("send-json", json);
                new LoginTask().execute(this.send);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //A CLASSE LOGIN TASK É UMA CLASSE INTERNA ELA E RESPONSAVEL POR ENVIAR OS DADDOS DO USUARIO VIA WEB SERVICE
    private class LoginTask extends AsyncTask<Send, Void, Result> {

        private Send send;
        //URL QUE RECEBE AS INFORMAÇÕES
        private static final String url = "http://www.institutofernandobeleboni.com.br/florestsimulator/json/progressUsuario.php";

        @Override
        protected Result doInBackground(Send... send) {
            this.send = send[0];
            return HttpConnection.getSetDataWeb(url, this.send.getParams());
        }

        @Override
        protected void onPostExecute(Result result) {
            if (result.hasError()) {
                //GET ANSWER
                String msg = JSON.from(result.getAnswer(), String.class);
                Toast.makeText(IdentificacaoActivity.this, msg, Toast.LENGTH_LONG).show();
            } else {
                Usuario u = JSON.from(result.getAnswer(), Usuario.class);
                Toast.makeText(IdentificacaoActivity.this, "Usuário " + u.getLogin() + " conectado com sucesso.",
                        Toast.LENGTH_LONG).show();
                this.send.setIdUsuario(u.getId());
                Intent enviar = new Intent(IdentificacaoActivity.this, EnviaProjetoActivity.class);
                Send.putExtra(enviar, this.send);
                IdentificacaoActivity.this.finish();
                startActivity(enviar);
            }
        }
    }
}
