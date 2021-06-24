package br.com.jeff.configuration.security

import br.com.jeff.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.Long.parseLong
import javax.inject.Singleton

@Singleton
class UserPasswordAuthenticationProvider(private val userRepository: UserRepository, private val bCryptPasswordEncoderService: BCryptPasswordEncoderService): AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        val user = userRepository.findById(parseLong(authenticationRequest?.identity as String))

        if(user.isPresent) {
            if(bCryptPasswordEncoderService.matches(authenticationRequest.secret as String, user.get().password))
                return Flowable.just(UserDetails(user.get().cpf.toString(), listOf()))
            return Flowable.just(AuthenticationFailed())
        } else {
            return Flowable.just(AuthenticationFailed())
        }
    }

}