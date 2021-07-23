package com.gmail.appverstas.routes

import com.gmail.appverstas.data.checkPasswordForEmail
import com.gmail.appverstas.data.requests.AccountRequest
import com.gmail.appverstas.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch(e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.password, request.email)
            if(isPasswordCorrect){
                call.respond(OK, SimpleResponse(true, "You have logged in!"))
            }else{
                call.respond(OK, SimpleResponse(false, "The Email or password is incorrect!"))
            }
        }
    }
}