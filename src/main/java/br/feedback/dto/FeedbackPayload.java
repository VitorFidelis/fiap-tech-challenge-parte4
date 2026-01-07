package br.feedback.dto;

import br.feedback.service.Urgencia;

import java.time.Instant;

public record FeedbackPayload(
        String descricao,
        Double nota,
        Urgencia urgencia,
        Instant dataEnvio
) {
}
