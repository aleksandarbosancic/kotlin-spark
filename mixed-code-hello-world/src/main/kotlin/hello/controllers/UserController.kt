package hello.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import hello.services.UserDao
import spark.Request
import spark.Spark.*

class UserController {

    val userDao = UserDao()

    init {
        println("User Controller Initialization")
    }

    val userRoute = {
        get("") { req, res ->
            jacksonObjectMapper().writeValueAsString(userDao.users)
        }

        get("/:id") { req, res ->
            userDao.findById(req.params("id").toInt())
        }

        get("/email/:email") { req, res ->
            userDao.findByEmail(req.params("email"))
        }

        post("/create") { req, res ->
            userDao.save(name = req.qp("name"), email = req.qp("email"))
            res.status(201)
            "ok"
        }

        patch("/update/:id") { req, res ->
            userDao.update(
                    id = req.params("id").toInt(),
                    name = req.qp("name"),
                    email = req.qp("email")
            )
            "ok"
        }

        delete("/delete/:id") { req, res ->
            userDao.delete(req.params("id").toInt())
            "ok"
        }
    }

    // add "qp()" alias for "queryParams()" on Request object
    fun Request.qp(key: String): String = this.queryParams(key)

}
