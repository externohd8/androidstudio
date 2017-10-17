package br.com.model;

import java.util.Date;
import java.util.List;

import br.com.enums.TipoProjeto;

/**
 * Created by Fernando on 29/07/2016.
 */
public class ProjetoCenso implements ToSendInterface {

    private Long id;
    private String nome;
    private Double areaInventariada;
    private String status;
    private Date dataCadastro;

    private List<DadosProjetoCenso> dadosProjetoCensos;

    // não salva banco app
    private Long idUsuario;

    public ProjetoCenso() {
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAreaInventariada() {
        return areaInventariada;
    }

    public void setAreaInventariada(Double areaInventariada) {
        this.areaInventariada = areaInventariada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DadosProjetoCenso> getDadosProjetoCensos() {
        return dadosProjetoCensos;
    }

    public void setDadosProjetoCensos(List<DadosProjetoCenso> dadosProjetoCensos) {
        this.dadosProjetoCensos = dadosProjetoCensos;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public TipoProjeto getTipo() {
        return TipoProjeto.PROJETO_CENSO;
    }

    @Override
    public Long getIdUsuario() {
        return this.idUsuario;
    }
}
