package br.com.model;

import java.util.Date;

/**
 * Created by Fernando on 29/07/2016.
 */
public class DadosProjetoCenso {

    private Long id;
    private ProjetoCenso projetoCenso;
    private Arvore arvore;
    private Double cap;
    private Double altura;
    private Date dataCadastro;

    public DadosProjetoCenso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjetoCenso getProjetoCenso() {
        return projetoCenso;
    }

    public void setProjetoCenso(ProjetoCenso projetoCenso) {
        this.projetoCenso = projetoCenso;
    }

    public Arvore getArvore() {
        return arvore;
    }

    public void setArvore(Arvore arvore) {
        this.arvore = arvore;
    }

    public Double getCap() {
        return cap;
    }

    public void setCap(Double cap) {
        this.cap = cap;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
