CREATE TABLE songs (
    id              BIGSERIAL    PRIMARY KEY,
    title           TEXT         NOT NULL,
    mbid_recording  UUID,        -- MusicBrainz Recording ID, nullable (œuvre inconnue de MB)
    mbid_work       UUID,        -- MusicBrainz Work ID, nullable
    language        TEXT         NOT NULL DEFAULT 'ja',  -- ISO 639-1
    duration_ms     INTEGER      CHECK (duration_ms IS NULL OR duration_ms > 0),
    release_date    DATE,        -- date de composition/sortie de la chanson (pas du row)
    origin_id       BIGINT       NOT NULL REFERENCES origins(id),
    created_by      BIGINT       NOT NULL REFERENCES users(id),
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX songs_created_by_idx     ON songs (created_by);
CREATE INDEX songs_provenance_id_idx  ON songs (origin_id);
CREATE INDEX songs_mbid_recording_idx ON songs (mbid_recording)
    WHERE mbid_recording IS NOT NULL;

CREATE TRIGGER songs_set_updated_at
    BEFORE UPDATE ON songs
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
