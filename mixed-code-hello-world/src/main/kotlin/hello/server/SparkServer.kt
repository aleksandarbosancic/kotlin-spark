package hello.server

import hello.controllers.UserController
import hello.utils.PID
import spark.Spark.*
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import spark.kotlin.before
import java.awt.SystemColor.info
import spark.Spark.exception



class SparkServer

fun main(args: Array<String>) {

    val logger = LoggerFactory.getLogger(SparkServer::class.java!!)

    println("Start spark server!")
    PID().printPID()

    before("/*"){ req, res -> logger.info(req.requestMethod() + " " + req.uri()) }
    before("/*") { req, res ->
        res.header("Access-Control-Allow-Headers", "Authorization, Content-Type")
        res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        res.header("Access-Control-Allow-Origin", "*")
    }

    get("/hello"){ reg, res ->
         "Hello Spark Kotlin!"
    }
    get("/multiline-interpolation-example") { req, res ->
        val name = "Alice"
        val email = "alice@alice.kt"
        """
            <h1>Hello $name</h1>
            <p>Your email is $email</p>
        """
    }
    fun serverError(code: Int = 500, message: String = "Internal server error"): String {
        return """
            <h1 style="font-family:monospace">$message (Error $code)</h1>
            <p style="font-family:monospace">We're sorry, but our server messed up. Please back and try again.</p>
        """
    }

    get("/internal-server-error") { req, res ->
        serverError();
    }

    get("/not-implemented") { req, res ->
        serverError(code = 501, message = "Not implemented");
    }
    get("/when-example") { req, res ->
        when (req.queryParams("lang")) {
            "EN" -> "Hello!"
            "FR" -> "Salut!"
            "IT" -> "Ciao!"
            else -> "Sorry, I can't greet you in ${req.queryParams("lang")} yet"
        }
    }

    options("/*") { req, res -> "" }

    path("/users", UserController().userRoute)

    exception(Exception::class.java) { e, req, res ->
        val message = e.javaClass.name + ": " + e.message
        logger.error(message)
        res.type("application/json")
        res.status(500)
        res.body(message)
    }

}