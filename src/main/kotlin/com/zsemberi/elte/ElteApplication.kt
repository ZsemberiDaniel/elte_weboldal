package com.zsemberi.elte

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ElteApplication

fun main(args: Array<String>) {
    runApplication<ElteApplication>(*args)
}
