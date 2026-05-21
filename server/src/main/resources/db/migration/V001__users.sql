CREATE TABLE users (
    id              BIGSERIAL   PRIMARY KEY,
    email           TEXT        NOT NULL UNIQUE CHECK (email = lower(email)),
    username        TEXT  NOT NULL UNIQUE,
    display_name    TEXT,
    password_hash   TEXT,
    oauth_provider  TEXT,
    oauth_subject   TEXT,
    avatar_url      TEXT,
    is_admin        BOOLEAN     NOT NULL DEFAULT false,
    is_active       BOOLEAN     NOT NULL DEFAULT true,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at      TIMESTAMPTZ NOT NULL DEFAULT now(),

    -- Au moins une méthode d'auth doit être renseignée
    CONSTRAINT users_has_auth_method CHECK (
        password_hash IS NOT NULL
            OR (oauth_provider IS NOT NULL AND oauth_subject IS NOT NULL)
        ),

    -- Cohérence : provider et subject vont ensemble ou pas du tout
    CONSTRAINT users_oauth_pair CHECK (
        (oauth_provider IS NULL) = (oauth_subject IS NULL)
        )
);

-- Index unique partiel : unicité de l'identité OAuth uniquement pour les comptes OAuth
CREATE UNIQUE INDEX users_oauth_identity_uidx
    ON users (oauth_provider, oauth_subject)
    WHERE oauth_provider IS NOT NULL;

-- Fonction générique réutilisable par les futures tables avec `updated_at`.
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER users_set_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION set_updated_at();
