package io.github.pynsze.auth.service

import at.favre.lib.crypto.bcrypt.BCrypt

class BCryptPasswordHasher(
    private val cost: Int = DEFAULT_COST
) : IPasswordHasher {

    override fun hash(plaintext: String): String {
        return BCrypt.withDefaults().hashToString(cost, plaintext.toCharArray())
    }

    override fun verify(plaintext: String, hash: String): Boolean {
        return BCrypt.verifyer().verify(plaintext.toCharArray(), hash).verified
    }

    companion object {
        const val DEFAULT_COST = 12
    }
}
