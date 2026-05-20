CREATE TABLE tokens (
    id                  BIGSERIAL    PRIMARY KEY,
    line_id             BIGINT       NOT NULL REFERENCES lignes(id) ON DELETE CASCADE,
    token_index         INTEGER      NOT NULL CHECK (position >= 0),
    char_start          INTEGER      NOT NULL CHECK (char_debut >= 0),
    char_end            INTEGER      NOT NULL CHECK (char_fin > char_debut),
    surface             TEXT         NOT NULL,
    reading             TEXT,        -- nullable: ponctuation, symboles
    lemma               TEXT,        -- nullable pareil
    pos                 TEXT         NOT NULL,
    tokenizer_version   TEXT         NOT NULL,  -- ex: 'kuromoji-ipadic-0.9.0' : se renseigner sur les versions à prendre
    input_id            BIGINT,
    -- FK vers entrees(id) à ajouter dans la migration JMdict
    -- (ALTER TABLE tokens ADD CONSTRAINT tokens_entree_id_fkey ...)
    UNIQUE (ligne_id, position)
);
