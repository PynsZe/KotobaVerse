package io.github.pynsze.auth.service

interface IPasswordHasher {
    fun hash(plaintext: String): String
    fun verify(plaintext: String, hash: String): Boolean
}

class PasswordHasher {
}