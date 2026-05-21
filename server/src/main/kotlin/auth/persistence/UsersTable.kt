package io.github.pynsze.auth.persistance

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.timestampWithTimeZone

object UsersTable : Table("users") {
    val id = long("id").autoIncrement()
    val email         = text("email")
    val username      = text("username")
    val displayName   = text("display_name").nullable()
    val passwordHash  = text("password_hash").nullable()
    val oauthProvider = text("oauth_provider").nullable()
    val oauthSubject  = text("oauth_subject").nullable()
    val avatarUrl     = text("avatar_url").nullable()
    val isAdmin       = bool("is_admin").default(false)
    val isActive      = bool("is_active").default(true)
    val createdAt     = timestampWithTimeZone("created_at")
    val updatedAt     = timestampWithTimeZone("updated_at")

    override val primaryKey = PrimaryKey(id)
}