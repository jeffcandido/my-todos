package br.com.jeff.repository

import br.com.jeff.configuration.security.BCryptPasswordEncoderService
import br.com.jeff.model.User
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
abstract class UserRepository(private val bCryptPasswordEncoderService: BCryptPasswordEncoderService): JpaRepository<User, Long> {
    fun saveEncoded(entity: User): User {
        return save(User(entity.cpf, bCryptPasswordEncoderService.encode(entity.password)))
    }
}