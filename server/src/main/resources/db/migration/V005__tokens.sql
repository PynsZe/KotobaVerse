CREATE TABLE tokens (
    id                  BIGSERIAL    PRIMARY KEY,
    line_id             BIGINT       NOT NULL REFERENCES lines(id) ON DELETE CASCADE,
    token_index         INTEGER      NOT NULL CHECK (token_index >= 0),
    char_start          INTEGER      NOT NULL CHECK (char_start >= 0),
    char_end            INTEGER      NOT NULL CHECK (char_end > char_start),
    surface             TEXT         NOT NULL,
    reading             TEXT,        -- nullable: ponctuation, symboles
    lemma               TEXT,        -- nullable pareil
    pos                 TEXT         NOT NULL,
    tokenizer_version   TEXT         NOT NULL,  -- ex: 'kuromoji-ipadic-0.9.0' : se renseigner sur les versions à prendre
    input_id            BIGINT,
    -- FK vers entrees(id) à ajouter dans la migration JMdict
    -- (ALTER TABLE tokens ADD CONSTRAINT tokens_entree_id_fkey ...)
    UNIQUE (line_id, token_index)
);
