package br.com.services;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.model.JSON;
import br.com.model.Result;
import br.com.model.Send;

/**
 * Created by Fernando on 15/10/2016.
 * Esta classe é responsavel por gerenciar os json enviados e recebidos
 * Está classe manipula todos os dados em json
 */
public class HttpConnection {

    public static Result getSetDataWeb(String url, String method, String data) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try{
            List<NameValuePair> valores = new ArrayList<>();
            valores.add(new BasicNameValuePair("method", method));
            valores.add(new BasicNameValuePair("json", data));
            httpPost.setEntity(new UrlEncodedFormEntity(valores));
            HttpResponse resposta = httpClient.execute(httpPost);
            String answer = EntityUtils.toString(resposta.getEntity());

            return JSON.from(answer, Result.class);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return new Result("Erro ao se conectar.", Result.ERROR);
    }
    public static Result getSetDataWeb(String url, String... params) {
        return getSetDataWeb(url, params[0], params[1]);
    }

    public static Result getSetDataWeb(Send send) {
        return getSetDataWeb(send.getUrl(), send.getParams());
    }

}
