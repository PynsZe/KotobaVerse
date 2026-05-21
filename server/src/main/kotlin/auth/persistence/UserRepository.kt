package io.github.pynsze.auth.persistance

import io.github.pynsze.auth.model.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction
import org.jetbrains.exposed.v1.jdbc.update
import kotlin.time.toKotlinInstant

class UserRepository {

    data class NewLocalUser(
        val email: String,
        val username: String,
        val passwordHash: String,
        val displayName: String? = null,
    )

    suspend fun findById(id: Long): User? = dbQuery {
        UsersTable.selectAll()
            .where { UsersTable.id eq id }
            .singleOrNull()
            ?.toUser()
    }

    suspend fun findByEmail(email: String): User? = dbQuery {
        UsersTable.selectAll()
            .where { UsersTable.email eq email.lowercase() }
            .singleOrNull()
            ?.toUser()
    }

    suspend fun findByUsername(username: String): User? = dbQuery {
        UsersTable.selectAll()
            .where { UsersTable.username eq username }
            .singleOrNull()
            ?.toUser()
    }

    suspend fun existsByEmail(email: String): Boolean = dbQuery {
        !UsersTable.selectAll()
            .where { UsersTable.email eq email.lowercase() }
            .empty()
    }

    suspend fun existsByUsername(username: String): Boolean = dbQuery {
        !UsersTable.selectAll()
            .where { UsersTable.username eq username }
            .empty()
    }

    suspend fun insertLocal(input: NewLocalUser): User = dbQuery {
        val insertedId = UsersTable.insert {
            it[email]        = input.email.lowercase()
            it[username]     = input.username
            it[passwordHash] = input.passwordHash
            it[displayName]  = input.displayName
            // oauthProvider / oauthSubject restent NULL → compte local
        } get UsersTable.id

        UsersTable.selectAll()
            .where { UsersTable.id eq insertedId }
            .single()
            .toUser()
    }

    suspend fun setActive(id: Long, active: Boolean): Boolean = dbQuery {
        UsersTable.update({ UsersTable.id eq id }) {
            it[isActive] = active
        } > 0
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        suspendTransaction { block() }

    private fun ResultRow.toUser(): User = User(
        id            = this[UsersTable.id],
        email         = this[UsersTable.email],
        username      = this[UsersTable.username],
        displayName   = this[UsersTable.displayName],
        passwordHash  = this[UsersTable.passwordHash],
        oauthProvider = this[UsersTable.oauthProvider],
        oauthSubject  = this[UsersTable.oauthSubject],
        avatarUrl     = this[UsersTable.avatarUrl],
        isAdmin       = this[UsersTable.isAdmin],
        isActive      = this[UsersTable.isActive],
        createdAt     = this[UsersTable.createdAt].toInstant().toKotlinInstant(),
        updatedAt     = this[UsersTable.updatedAt].toInstant().toKotlinInstant(),
    )
}