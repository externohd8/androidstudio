package br.com.model;

import br.com.enums.TipoProjeto;

/**
 * Created by Fernando on 22/10/2016.
 */
public interface ToSendInterface {
    //SÃO ATRIBUTOS USADOS POR PADRAO EM TODO O PROJETO
    Long getId();

    TipoProjeto getTipo();

    Long getIdUsuario();

}
