package br.com.enums;
/**
 * Created by Fernando on 02/08/2016.
 */
public enum Status {
    EM_PROGRESSO("Em progresso"),
    CONCLUIDO("Concluido"),
    ENVIADO("Enviado");

    private String descricao;

    Status(String descricao) {
        this.descricao =  descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public static Status get(String value) {
        for (Status s : Status.values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        return null;
    }

}
