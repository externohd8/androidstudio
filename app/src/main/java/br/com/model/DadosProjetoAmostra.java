package br.com.model;

import java.util.Date;

/**
 * Created by Fernando on 29/07/2016.
 */
public class DadosProjetoAmostra {

    private Long id;
    private Amostra amostra;
    private ProjetoAmostras projetoAmostras;
    private Arvore arvore;
    private Double cap;
    private Double altura;
    private Date dataCadastro;

    public DadosProjetoAmostra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Amostra getAmostra() {
        return amostra;
    }

    public void setAmostra(Amostra amostra) {
        this.amostra = amostra;
    }

    public ProjetoAmostras getProjetoAmostras() {
        return projetoAmostras;
    }

    public void setProjetoAmostras(ProjetoAmostras projetoAmostras) {
        this.projetoAmostras = projetoAmostras;
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
