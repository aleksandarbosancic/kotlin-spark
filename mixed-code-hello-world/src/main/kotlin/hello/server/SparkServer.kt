package hello.server

import hello.controllers.HelloController
import hello.controllers.UserController
import hello.filters.Filters
import hello.utils.PID
import spark.Spark.*
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import spark.kotlin.before
import java.awt.SystemColor.info
import spark.Spark.exception
import java.io.File

class SparkServer{
}

val logger = LoggerFactory.getLogger(SparkServer::class.java!!)

fun main(args: Array<String>) {

    println("Start spark server!")
    PID().printPID()
    println(File("springloaded/springloaded-1.2.5.RELEASE.jar").exists())

    initApplication()
}

fun initApplication(): Unit {
    initFilters()
    initControllers()
    initExceptions()
}

fun initExceptions() {
    exception(Exception::class.java) { e, req, res ->
        val message = e.javaClass.name + ": " + e.message
        logger.error(message)
        res.type("application/json")
        res.status(500)
        res.body(message)
    }
}

fun initControllers() {
//    path("/users", UserController().userRoute)
    UserController()
    HelloController()
}

fun initFilters() {
    Filters()

}
