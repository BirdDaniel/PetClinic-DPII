package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia3Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_6 = Map("Accept" -> "image/webp,*/*")

	val headers_17 = Map(
		"Accept" -> "application/font-woff2;q=1.0,application/font-woff;q=0.9,*/*;q=0.8",
		"Accept-Encoding" -> "identity")

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(29)
	}	

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]","value").saveAs("stoken")))
		.pause(15)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object ShowResidences {
		val showResidences = exec(http("ShowResidences")
			.get("/residence/findAll")
			.headers(headers_0))
		.pause(15)
	}

	object DetailsOfOneResidence {
		val detailsOfOneResidence = exec(http("DetailsOfOneResidence")
			.get("/residence/1")
			.headers(headers_0))
		.pause(29)
	}

	object YouCantDoThis {
		val youCantDoThis = exec(http("YouCantDoThis")
			.get("/residence/20")
			.headers(headers_0))
		.pause(10)
	}



	val positiveScn = scenario("Positive").exec(Home.home,
	                                            Login.login,
												ShowResidences.showResidences,
												DetailsOfOneResidence.detailsOfOneResidence)

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