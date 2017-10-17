package br.com.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Fernando on 29/07/2016.
 */
public class Amostra {

    private Long id;
    private ProjetoAmostras projetoAmostra;
    private String nome;
    private Double tamanho;
    private String status;
    private Date dataCadastro;

    private List<DadosProjetoAmostra> dadosProjetoAmostras;

    public Amostra() { }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public ProjetoAmostras getProjetoAmostra() {
        return projetoAmostra;
    }

    public void setProjetoAmostra(ProjetoAmostras projetoAmostra) {
        this.projetoAmostra = projetoAmostra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTamanho() {
        return tamanho;
    }

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DadosProjetoAmostra> getDadosProjetoAmostras() {
        return dadosProjetoAmostras;
    }

    public void setDadosProjetoAmostras(List<DadosProjetoAmostra> dadosProjetoAmostras) {
        this.dadosProjetoAmostras = dadosProjetoAmostras;
    }
}
