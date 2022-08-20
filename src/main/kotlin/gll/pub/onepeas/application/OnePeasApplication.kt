package gll.pub.onepeas.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@SpringBootApplication
@ComponentScan(basePackages = ["gll.pub.onepeas"])
@EnableJpaRepositories(basePackages = ["gll.pub.onepeas.repository"])
@EntityScan(basePackages = ["gll.pub.onepeas.model"])
class OnePeasApplication {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(this.javaClass)
        fun printApplicatonHeader(title: String, version: String, author: String){
            logger.info("**************************************")
            logger.info("** $title Application Started.")
            logger.info("** Version: $version")
            logger.info("** Author: $author")
            logger.info("**************************************")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<OnePeasApplication>(*args)
    OnePeasApplication.printApplicatonHeader("Contact High", "1.0.1", "Noah Panicola")
}
