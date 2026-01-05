package br.feedback.entity;

import br.feedback.domain.Feedback;
import br.feedback.service.Urgencia;

import java.time.Instant;
import java.util.UUID;

public class FeedbackEntity {

    private UUID id;
    private String descricao;
    private double nota;
    private Instant dataEnvio;
    private Urgencia urgencia;

    public static FeedbackEntity fromDomain(Feedback feedback) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.id = feedback.getId();
        entity.descricao = feedback.getDescricao();
        entity.nota = feedback.getNota();
        entity.dataEnvio = feedback.getDataEnvio();
        entity.urgencia = feedback.getUrgencia();
        return entity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Instant getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Instant dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Urgencia urgencia) {
        this.urgencia = urgencia;
    }
}
