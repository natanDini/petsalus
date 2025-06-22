package org.example.project.service


import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.model.CadastroRequest
import org.example.project.network.LoginRequest
import org.example.project.network.LoginResponse


class ApiService(private val client: HttpClient) {

    private val base = "http://192.168.1.10:8080/petsalus/api"

    suspend fun cadastrarUsuario(request: CadastroRequest): Boolean {
        return try {
            val response: HttpResponse = client.post("$base/user/registrar") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse? {
        return try {
            val response: HttpResponse = client.post("$base/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            if (response.status == HttpStatusCode.OK) {
                response.body()  // agora desserializa corretamente
            } else {
                println("Erro no login: ${response.status}")
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}