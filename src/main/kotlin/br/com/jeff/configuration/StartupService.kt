package br.com.jeff.configuration

import br.com.jeff.model.User
import br.com.jeff.repository.UserRepository
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class StartupService(private val userRepository: UserRepository) {

    private val log = LoggerFactory.getLogger(StartupService::class.java)

    @EventListener
    fun onStartupEvent(event: StartupEvent) {

        log.info("Event: {}", event.toString())
        val user = userRepository.save(User(
            192168180200,
            "123456"
        ))

        log.info("Usuario criado com sucesso: {}", user)
    }
}