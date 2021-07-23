package com.gmail.appverstas

import com.gmail.appverstas.data.checkPasswordForEmail
import com.gmail.appverstas.data.collections.User
import com.gmail.appverstas.data.registerUser
import com.gmail.appverstas.routes.loginRoute
import com.gmail.appverstas.routes.noteRoutes
import com.gmail.appverstas.routes.registerRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication){
        configureAuth()
    }
    install(Routing) {
        registerRoute()
        loginRoute()
        noteRoutes()
    }

}

private fun Authentication.Configuration.configureAuth(){
    basic {
        realm = "Note Server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if(checkPasswordForEmail(password, email)){
                UserIdPrincipal(email)
            }else null
        }
    }
}

