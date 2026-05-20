CREATE TABLE lines (
    id              BIGSERIAL    PRIMARY KEY,
    song_id         BIGINT       NOT NULL REFERENCES chansons(id) ON DELETE CASCADE,
    line_index      INTEGER      NOT NULL CHECK (num_ligne >= 0),
    raw_text        TEXT         NOT NULL,
    translation_en  TEXT,
    translation_fr  TEXT,
    UNIQUE (chanson_id, num_ligne)
);
