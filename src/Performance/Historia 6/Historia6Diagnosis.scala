package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia6Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")


	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val csvFeeder = csv("items.csv")


	object Home {

		val home = exec(http("HOME")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login{

		val login = exec(http("LOGIN")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LOGGED")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}


	object Inventory{
		
		val inventory = exec(http("INVENTORY")
			.get("/employees/1/itemsList")
			.headers(headers_0))
		.pause(5)
		
	}

	object Add{
		
		val add = exec(http("ADD_FORM")
			.get("/employees/1/itemsList/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
			.feed(csvFeeder)
			.exec(http("ADD")
			.post("/employees/1/itemsList/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "${name}")
			.formParam("price", "${price}")
			.formParam("sale", "${sale}")
			.formParam("description", "${description}")
			.formParam("stock", "${stock}")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	object BadURL{
		
		val badURL = exec(http("BAD_URL")
			.get("/employees/0/itemsList")
			.headers(headers_0))
		.pause(5)
		
	}

	val addSnc = scenario("Add").exec(Home.home,
					Login.login,
					Inventory.inventory,
					Add.add)

	val badURLSnc = scenario("BadURL").exec(Home.home,
					Login.login,
					BadURL.badURL)


	setUp(addSnc.inject(rampUsers(3500) during (100 seconds)),
		badURLSnc.inject(rampUsers(3500) during (100 seconds))
		).protocols(httpProtocol)
         .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}
