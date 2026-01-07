package br.feedback.service;

import br.feedback.domain.Feedback;
import br.feedback.dto.FeedbackPayload;
import br.feedback.entity.FeedbackEntity;
import br.feedback.repository.FeedbackRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class FeedbackService {

    @Inject
    FeedbackRepositoryImpl repository;
    
    @Inject
    SqsService sqsService;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final org.jboss.logging.Logger LOGGER = org.jboss.logging.Logger.getLogger("AppLifeCycleBean");

    public Feedback avaliar(String descricao, Double nota) throws Exception {

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (nota == null || nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        }

        Feedback feedback = new Feedback(UUID.randomUUID(), descricao, nota, Instant.now());

        salvar(feedback);
        if (feedback.getUrgencia() == Urgencia.ALTA) {
            FeedbackPayload payload = new FeedbackPayload(feedback.getDescricao(), feedback.getNota(), feedback.getUrgencia());
            String json = mapper.writeValueAsString(payload);
            LOGGER.log(Logger.Level.WARN, "Avaliacao baixa, alta urgencia\n Enviando payload: " + json);
            sqsService.sendMessage(json);
        }

        return feedback;
    }

    public void salvar(Feedback feedback) {
        FeedbackEntity entity = FeedbackEntity.fromDomain(feedback);
        repository.persist(entity);
        LOGGER.log(Logger.Level.INFO, "Salvando no banco");
    }

}
