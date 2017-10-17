package br.com.trees;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;

import br.com.dao.AmostraDAO;
import br.com.dao.DadosProjetoCensoDAO;
import br.com.dao.ProjetoAmostrasDAO;
import br.com.dao.ProjetoCensoDAO;
import br.com.model.JSON;
import br.com.model.ProjetoAmostras;
import br.com.model.ProjetoCenso;
import br.com.model.Result;
import br.com.model.Send;
import br.com.services.HttpConnection;

/**
 * Created by Fernando on 23/10/2016.
 * Está tela serve para enviar o projeto via json
 */
public class EnviaProjetoActivity extends Activity {

    private ProgressDialog load;
    private ProjetoCensoDAO projetoCensoDAO;
    private DadosProjetoCensoDAO dadosProjetoCensoDAO;
    private ProjetoAmostrasDAO projetoAmostrasDAO;
    private AmostraDAO amostraDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        load = ProgressDialog.show(EnviaProjetoActivity.this, "Por favor Aguarde ...",
//                "Enviando projeto...");

        load = new ProgressDialog(this);
        load.setMessage("Enviando projeto...");
        load.setTitle("Por favor Aguarde ...");

        Bundle extras = getIntent().getExtras();
        Send send = Send.fromExtra(extras);

        projetoCensoDAO = new ProjetoCensoDAO(this);
        dadosProjetoCensoDAO = new DadosProjetoCensoDAO(this);

        projetoAmostrasDAO = new ProjetoAmostrasDAO(this);
        amostraDAO = new AmostraDAO(this);

        try {
            this.enviaProjeto(send);
        } catch (JSONException e) {
            Toast.makeText(this, "Ocorreu um erro ao enviar o projeto.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            load.dismiss();
        }
    }

    private void enviaProjeto(Send send) throws JSONException {
        switch (send.getTipoProjeto()) {

            case PROJETO_AMOSTRAS:
                ProjetoAmostras projetoAmostras = projetoAmostrasDAO.buscar(send.getIdProjeto());
                projetoAmostras.setAmostras(amostraDAO.listarPorProjeto(projetoAmostras.getId()));
                projetoAmostras.setIdUsuario(send.getIdUsuario());
                send.setUrl("http://www.institutofernandobeleboni.com.br/florestsimulator/json/progressProjetoAmostras.php");
                send.setParams("send-project-amostras", JSON.toString(projetoAmostras));
                break;

            case PROJETO_CENSO: {
                ProjetoCenso projetoCenso = projetoCensoDAO.buscar(send.getIdProjeto());
                projetoCenso.setDadosProjetoCensos(dadosProjetoCensoDAO.listarPorProjeto(
                        projetoCenso.getId().toString()));
                projetoCenso.setIdUsuario(send.getIdUsuario());
                send.setUrl("http://www.institutofernandobeleboni.com.br/florestsimulator/json/progressProjetoCenso.php");
                send.setParams("send-project-censo", JSON.toString(projetoCenso));
                break;
            }

            default:
                throw new JSONException("Erro");

        }

        new EnviaTask().execute(send);
    }

    private class EnviaTask extends AsyncTask<Send, Void, Result> {

        private Send send;

        @Override
        protected Result doInBackground(Send... send) {
            this.send = send[0];
            return HttpConnection.getSetDataWeb(this.send);
        }
        @Override
        protected void onPostExecute(Result result) {
            String msg = JSON.from(result.getAnswer(), String.class);
            Toast.makeText(EnviaProjetoActivity.this, msg, Toast.LENGTH_LONG).show();
            if (!result.hasError()) {
                send.getTipoProjeto().alterarStatus(EnviaProjetoActivity.this, send.getIdProjeto(),
                        br.com.enums.Status.ENVIADO);
            }
            Intent it = new Intent(EnviaProjetoActivity.this, this.send.getTipoProjeto().getClasseRetorno());
            EnviaProjetoActivity.this.finish();
            startActivity(it);
            load.dismiss();
        }

    }

}
