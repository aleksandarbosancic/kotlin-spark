package hello.domain

data class User(var name: String, var email: String, var id: Int){

    var age: Int = 0

    constructor(name: String, email: String, id: Int, age: Int): this(name, email, id){
        this.age = age
    }
}