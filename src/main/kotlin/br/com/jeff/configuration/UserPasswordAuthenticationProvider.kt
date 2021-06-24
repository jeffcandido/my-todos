package br.com.jeff.configuration

import br.com.jeff.repository.UserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.Long.parseLong
import javax.inject.Singleton

@Singleton
class UserPasswordAuthenticationProvider(private val userRepository: UserRepository): AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse> {
        val user = userRepository.findById(parseLong(authenticationRequest?.identity as String))

        if(user.isPresent) {
            if(user.get().password == authenticationRequest.secret)
                return Flowable.just(UserDetails(user.get().cpf.toString(), listOf()))
            return Flowable.just(AuthenticationFailed())
        } else {
            return Flowable.just(AuthenticationFailed())
        }
    }

}