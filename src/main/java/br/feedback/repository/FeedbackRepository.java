package br.feedback.repository;

import br.feedback.entity.FeedbackEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface FeedbackRepository {
    
    void persist(FeedbackEntity feedback);

}
