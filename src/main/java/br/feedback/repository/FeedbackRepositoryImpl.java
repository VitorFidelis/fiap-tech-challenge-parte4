package br.feedback.repository;

import br.feedback.entity.FeedbackEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@ApplicationScoped
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Inject
    DataSource dataSource;


    @Override
    public void persist(FeedbackEntity feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback não pode ser nulo");
        }

        String sql = """
                INSERT INTO feedback_entity (id, descricao, nota, data_envio, urgencia)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, feedback.getId());
            ps.setString(2, feedback.getDescricao());
            ps.setDouble(3, feedback.getNota());
            ps.setTimestamp(4, Timestamp.from(feedback.getDataEnvio()));
            ps.setString(5, feedback.getUrgencia().name());
            ps.execute();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Erro ao salvar feedback", e);
        }
    }
}