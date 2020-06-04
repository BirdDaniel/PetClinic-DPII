package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia7Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	object Home {
		val home = exec(http("Home")
			.get("/"))
		.pause(9)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(17)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(13)
	}

	object ShowPayments {
		val showPayments = exec(http("ShowPayments")
			.get("/employees/1/payments"))
		.pause(17)
	}

	object YouCantDoThis {
		val youCantDoThis = exec(http("YouCantDoThis")
			.get("/employees/2/payments"))
		.pause(13)
	}



	val positiveScn = scenario("Positive").exec(Home.home,
	                                            Login.login,
												ShowPayments.showPayments)

    val negativeScn = scenario("Negative").exec(Home.home,
	                                            Login.login,
												YouCantDoThis.youCantDoThis)


	setUp(
	positiveScn.inject(rampUsers(3500) during (100 seconds)),
	negativeScn.inject(rampUsers(3500) during (100 seconds))
	).protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}