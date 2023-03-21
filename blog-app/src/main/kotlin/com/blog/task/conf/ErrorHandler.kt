package com.blog.task.conf

import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DefaultErrorHandler{
    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e:Exception):ResponseEntity<*>{
        e.printStackTrace()
        return ResponseEntity(ErrorResponse("Exception", e.message.toString()), HttpStatus.BAD_REQUEST)
    }
}

@Serializable
data class ErrorResponse(val code: String, val message: String)