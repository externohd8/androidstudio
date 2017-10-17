package br.com.model;

/**
 * Created by Fernando on 15/10/2016.
 */
public class Usuario {

    private Long id;
    private String login;
    private String senha;

    public Usuario(Long id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario [ id = " + this.id + ", login = " + this.login + ", senha = "
                + this.senha + "]";
    }
}
