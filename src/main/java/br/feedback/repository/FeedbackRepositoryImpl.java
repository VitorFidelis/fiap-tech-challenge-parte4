package br.feedback.repository;

import br.feedback.entity.FeedbackEntity;
import br.feedback.service.Urgencia;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.ZoneId;
import java.util.UUID;

@ApplicationScoped
public class FeedbackRepositoryImpl implements FeedbackRepository {

    @Inject
    Pool client;

    @Override
    public Uni<FeedbackEntity> findById(UUID id) {
        return client.preparedQuery("SELECT * FROM feedback_entity WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator ->
                        iterator.hasNext() ? from(iterator.next()) : null
                );
    }

    @Override
    public Multi<FeedbackEntity> listAll() {
        return client.query("SELECT * FROM feedback_entity")
                .execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(this::from);
    }

    @Override
    public Uni<Void> persist(FeedbackEntity feedback) {
        if (feedback == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Feedback não pode ser nulo"));
        }

        String sql = """
            INSERT INTO feedback_entity (id, descricao, nota, data_envio, urgencia)
            VALUES ($1, $2, $3, $4, $5)
            """;

        return client.preparedQuery(sql)
                .execute(Tuple.of(
                        feedback.getId(),
                        feedback.getDescricao(),
                        feedback.getNota(),
                        feedback.getDataEnvio(),
                        feedback.getUrgencia().name()
                ))
                .onItem().ignore().andContinueWithNull();
    }

    @Override
    public Uni<Boolean> deleteById(UUID id) {
        return client.preparedQuery("DELETE FROM feedback_entity WHERE id = $1")
                .execute(Tuple.of(id))
                .onItem().transform(rowSet -> rowSet.rowCount() > 0);
    }

    @Override
    public Uni<Void> deleteAll() {
        return client.query("DELETE FROM feedback_entity")
                .execute()
                .onItem().ignore().andContinueWithNull();
    }

    private FeedbackEntity from(Row row) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(row.getUUID("id"));
        entity.setDescricao(row.getString("descricao"));
        entity.setNota(row.getDouble("nota"));
        entity.setDataEnvio(
                row.getLocalDateTime("data_envio")
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
        entity.setUrgencia(Urgencia.valueOf(row.getString("urgencia")));
        return entity;
    }
}