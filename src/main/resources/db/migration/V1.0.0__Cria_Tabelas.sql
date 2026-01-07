CREATE TABLE IF NOT EXISTS feedback_entity (
                id UUID PRIMARY KEY,
                descricao TEXT NOT NULL,
                nota DECIMAL NOT NULL,
                data_envio TIMESTAMP NOT NULL,
                urgencia VARCHAR(20) NOT NULL
            );