CREATE TABLE origins (
    id                  BIGSERIAL    PRIMARY KEY,
    type                TEXT         NOT NULL,
    -- ex: 'utanet', 'lyrical_nonsense', 'aozora', 'tatoeba', 'manual'
    -- À promouvoir en enum quand la liste sera stable.
    external_ref        TEXT,
    -- URL source ou identifiant externe. Nullable car type='manual' n'en a pas.
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX origins_type_idx ON origins (type);