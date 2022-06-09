package br.com.company.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@ConfigurationPropertiesScan
@SpringBootApplication
class ForumApplication

fun main(args: Array<String>) {
    runApplication<ForumApplication>(*args)
}
