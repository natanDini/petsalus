package org.example.project.service

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.forms.*
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.project.model.PerfilCompleto
import java.io.File

class UserService(private val client: HttpClient) {

    suspend fun uploadPhoto(file: File, token: String): String? {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "http://192.168.1.10:8080/petsalus/api/user/upload-foto",
            formData = formData {
                append("foto", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                })
            },
            block = {
                headers.append(HttpHeaders.Authorization, "Bearer $token")
            }
        )

        return if (response.status.isSuccess()) {
            response.bodyAsText()
        } else {
            null
        }
    }

    suspend fun getPerfilCompleto(token: String): PerfilCompleto? {
        return try {
            val response: HttpResponse = client.get("http://192.168.1.10:8080/petsalus/api/user/perfil-completo") {
                header(HttpHeaders.Authorization, "Bearer $token")
                header(HttpHeaders.Accept, "application/json")
            }
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
