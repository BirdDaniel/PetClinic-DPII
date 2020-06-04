package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia4Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.woff""", """.*.woff2"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")


	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {
		val home = exec(http("Home")
			.get("/"))
		.pause(9)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
        	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			).pause(14)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(24)
	}

	object Requests {
		val requests = exec(http("Requests")
			.get("/owners/1/myRequestList"))
		.pause(8)
		
	}

	object Requests2 {
		val requests2 = exec(http("Requests")
			.get("/owners/2/myRequestList"))
		.pause(8)
	}


	val his_4_positiva = scenario("Historia 4 positiva").exec (
													Home.home,
													Login.login,
													Requests.requests
														)

	val his_4_negativa = scenario("Historia 4 negativa").exec (
													Home.home,
													Login.login,
													Requests.requests
														)

	setUp(his_4_positiva.inject(rampUsers(3500) during (100 seconds)),
			his_4_negativa.inject(rampUsers(3500) during (100 seconds))
            ).protocols(httpProtocol)
            .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}