package br.com.model;

import java.util.Date;
import java.util.List;

import br.com.enums.TipoProjeto;

/**
 * Created by Fernando on 29/07/2016.
 */
public class ProjetoAmostras implements ToSendInterface {

    private Long id;
    private String nome;
    private Double areaInventariada;
    private Double indiceConfianca;
    private String status;
    private Date dataCadastro;

    private List<Amostra> amostras;

    //não salva no banco
    private Long idUsuario;

    public ProjetoAmostras() {}

    public ProjetoAmostras(String id) {
        this.setId(Long.parseLong(id));
    }

    public ProjetoAmostras(Long id) {
        this.setId(id);
    }

    public Long getId() {
        return id;
    }

    @Override
    public TipoProjeto getTipo() {
        return TipoProjeto.PROJETO_AMOSTRAS;
    }

    @Override
    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getAreaInventariada() {
        return areaInventariada;
    }

    public void setAreaInventariada(Double areaInventariada) {
        this.areaInventariada = areaInventariada;
    }

    public Double getIndiceConfianca() {
        return indiceConfianca;
    }

    public void setIndiceConfianca(Double indiceConfianca) {
        this.indiceConfianca = indiceConfianca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Amostra> getAmostras() {
        return amostras;
    }

    public void setAmostras(List<Amostra> amostras) {
        this.amostras = amostras;
    }
}
