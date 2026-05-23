package io.github.pynsze.auth.service

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class BCryptPasswordHasherTest {

    // cost = 4 → tests rapides (~quelques ms par hash au lieu de ~250ms en prod)
    private val hasher = BCryptPasswordHasher(cost = 4)

    @Test
    fun `un meme plaintext produit deux hashs differents (salt unique)`() {
        val h1 = hasher.hash("hunter2")
        val h2 = hasher.hash("hunter2")
        assertNotEquals(h1, h2)
    }

    @Test
    fun `verify accepte le bon mot de passe`() {
        val hash = hasher.hash("hunter2")
        assertTrue(hasher.verify("hunter2", hash))
    }

    @Test
    fun `verify rejette un mauvais mot de passe`() {
        val hash = hasher.hash("hunter2")
        assertFalse(hasher.verify("hunter3", hash))
    }

    @Test
    fun `verify rejette une chaine vide`() {
        val hash = hasher.hash("hunter2")
        assertFalse(hasher.verify("", hash))
    }
}