package br.feedback.service;

import br.feedback.domain.Feedback;
import br.feedback.dto.FeedbackPayload;
import br.feedback.entity.FeedbackEntity;
import br.feedback.repository.FeedbackRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class FeedbackService {

    @Inject
    FeedbackRepository repository;
    
    @Inject
    SqsService sqsService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Feedback avaliar(String descricao, Double nota) throws Exception {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (nota == null || nota < 0 || nota > 10) {
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        }

        Feedback feedback = new Feedback(UUID.randomUUID(), descricao, nota, Instant.now());
        
        if (feedback.getUrgencia() == Urgencia.ALTA) {
            FeedbackPayload payload = new FeedbackPayload(feedback.getDescricao(), feedback.getNota(), feedback.getUrgencia());
            String json = mapper.writeValueAsString(payload);
            sqsService.sendMessage(json);
        }
        
        salvar(feedback);
        return feedback;
    }

    public void salvar(Feedback feedback) {
        FeedbackEntity entity = FeedbackEntity.fromDomain(feedback);
        repository.persist(entity);
    }
    
    public Multi<FeedbackEntity> listarTodos() {
        return repository.listAll();
    }
    
    public void limparDados() {
        repository.deleteAll();
    }
}
