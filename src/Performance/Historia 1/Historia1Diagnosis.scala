package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia1Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.woff""", """.*.woff2"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")


	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(9)
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

	object Profile {
		val profile = exec(http("Profile")
			.get("/owners/1")
			.headers(headers_0))
		.pause(7)
	}

	object Pets {
		val pets = exec(http("Pets")
			.get("/owners/1/myPetList")
			.headers(headers_0))
		.pause(13)
	}

	object Pets2 {
		val pets2 = exec(http("Pets")
			.get("/owners/2/myPetList")
			.headers(headers_0))
		.pause(13)
	}

	val his_1_positiva = scenario("Historia 1 Positivo").exec(
											Home.home,
											Login.login,
											Profile.profile,
											Pets.pets)


	val his_1_negativa = scenario("Historia 1 Negativo").exec(
											Home.home,
											Login.login,
											Profile.profile,
											Pets2.pets2)


	setUp(his_1_positiva.inject(rampUsers(70000) during (10 seconds)),
			his_1_negativa.inject(rampUsers(70000) during (10 seconds)))
			.protocols(httpProtocol)
}