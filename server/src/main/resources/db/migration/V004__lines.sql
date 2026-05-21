CREATE TABLE lines (
    id              BIGSERIAL    PRIMARY KEY,
    song_id         BIGINT       NOT NULL REFERENCES songs(id) ON DELETE CASCADE,
    line_index      INTEGER      NOT NULL CHECK (line_index >= 0),
    raw_text        TEXT         NOT NULL,
    translation_en  TEXT,
    translation_fr  TEXT,
    UNIQUE (song_id, line_index)
);
