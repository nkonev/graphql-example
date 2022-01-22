package name.nkonev.graphqlexample.graphqlexample

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@SpringBootApplication
class GraphqlExampleApplication

fun main(args: Array<String>) {
	runApplication<GraphqlExampleApplication>(*args)
}

@Controller
class GreetingGraphqlController {

    val logger = LoggerFactory.getLogger(this::class.java)

    private val db = listOf(Customer(id = 1, name = "Josh"), Customer(2, "B"))

    @QueryMapping
    fun customers() = db

    @QueryMapping
    fun customersByName(@Argument name: String) = db.filter { it.name == name }

    @SchemaMapping(typeName = "Customer")
    fun orders(customer: Customer): List<Order> {
        logger.info("Invoked orders")
        return listOf(Order(1, customer.id), Order(2, customer.id))
    }
}

data class Customer (val id : Int, val name : String)

data class Order (val id: Int, val customerId: Int)