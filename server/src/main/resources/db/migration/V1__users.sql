CREATE TABLE users (
    id              BIGSERIAL   PRIMARY KEY,
    email           TEXT        NOT NULL UNIQUE CHECK (email = lower(email)),
    username        VARCH(255)  NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VRACHAR(255),
    password_hash TEXT,
    oauth_provider  TEXT        NOT NULL,
    oauth_subject   TEXT        NOT NULL,
    avatar_url      TEXT,
    is_admin        BOOLEAN     NOT NULL DEFAULT false,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    UNIQUE (oauth_provider, oauth_subject)
);

-- Fonction générique réutilisable par les futures tables avec `updated_at`.
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_set_updated_at
BEFORE UPDATE ON users
FOR EACH ROW EXECUTE FUNCTION set_updated_at();
