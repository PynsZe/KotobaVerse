package io.github.pynsze

import io.ktor.server.application.*

// TODO: re-enable when OAuth lands (Google OAuth 2.0, cf. spec F-AUTH-1).
// Cette fonction n'est volontairement plus listée dans application.yaml
// — elle sert de marqueur pour la prochaine itération auth.
fun Application.configureSecurity() {
}
