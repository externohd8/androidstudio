package br.com.model;

import com.google.gson.JsonElement;

/**
 * Created by Fernando on 22/10/2016.
 */
public class Result {
    //RESPONSAVEL POR FAZER A COMUNIÇÃO ENTRE O WEB SERVICE E AS TASK
    //PADRONIZANDO COMO COMO O PHP DEVE DEVOLVER AS RESPOSTAS

    public static final Long ERROR = 0L;
    public static final Long SUCCESS = 1L;

    private Long status = SUCCESS;
    private JsonElement answer;

    public Result(String answer, Long status) {
        this.answer = JSON.toJsonElement(answer);
        this.status = status;
    }

    public static Long getERROR() {
        return ERROR;
    }

    public static Long getSUCCESS() {
        return SUCCESS;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    //OBJETO PADRAO DA GOOGLE, QUE CONTEM O JSON
    //RESPONSAVEL POR MANTER OS DADOS DE UMA STRING DO FORMATO JSON
    public JsonElement getAnswer() {
        return answer;
    }

    public void setAnswer(JsonElement answer) {
        this.answer = answer;
    }

    public boolean hasError() {
        return ERROR.equals(this.status);
    }

}
