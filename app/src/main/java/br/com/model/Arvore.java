package br.com.model;

/**
 * Created by Fernando on 29/07/2016.
 */
public class Arvore {

    private Long id;
    private String nomeComum;
    private String nomeCientifico;
    private String familia;
    private Double fatorForma;

    public Arvore() {}

    public Arvore(String nomeComum, String nomeCientifico, String familia, Double fatorForma) {
        this.nomeComum = nomeComum;
        this.nomeCientifico = nomeCientifico;
        this.familia = familia;
        this.fatorForma = fatorForma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComum() {
        return nomeComum;
    }

    public void setNomeComum(String nomeComum) {
        this.nomeComum = nomeComum;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public Double getFatorForma() {
        return fatorForma;
    }

    public void setFatorForma(Double fatorForma) {
        this.fatorForma = fatorForma;
    }

    @Override
    public String toString() {
        return this.getNomeComum()+ " - " + this.getNomeCientifico();
    }
}
