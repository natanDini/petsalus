package org.example.project.service

import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

class UserService(private val client: HttpClient) {

    suspend fun uploadPhoto(file: File): String? {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "http://192.168.1.10:8080/petsalus/api/user/upload-foto",
            formData = formData {
                append("file", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                })
            }
        )

        return if (response.status.isSuccess()) {
            response.bodyAsText()  // Supondo que o backend já devolve o base64 no body
        } else {
            null
        }
    }
}
