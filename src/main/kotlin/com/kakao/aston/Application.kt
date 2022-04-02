package com.kakao.aston

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class Application {
    companion object {
        const val BASE_PROFILE = "local"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}