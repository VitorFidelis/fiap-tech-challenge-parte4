package br.feedback.repository;

import br.feedback.entity.FeedbackEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public interface FeedbackRepository {
    
    Uni<FeedbackEntity> findById(UUID id);
    
    Multi<FeedbackEntity> listAll();
    
    Uni<Void> persist(FeedbackEntity feedback);
    
    Uni<Boolean> deleteById(UUID id);

    Uni<Void> deleteAll();
}
