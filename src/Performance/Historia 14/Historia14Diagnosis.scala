package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia14Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.png""", """.*.ico""", """.*.woff""", """.*.woff2"""), WhiteList())
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
        	.check(css("input[name=_csrf]", "value").saveAs("stoken"))
			)
		.pause(14)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(24)
	}

	object Profile {
		val profile = exec(http("Profile")
			.get("/owners/1"))
		.pause(7)
	}

	object Pets {
		val pets = exec(http("Pets")
			.get("/owners/1/myPetList"))
		.pause(13)
	}

	object EditPet {
		val editpet = exec(http("request_5")
			.get("/owners/1/pets/11/edit")
        	.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(12)
		.exec(http("request_6")
			.post("/owners/1/pets/11/edit")
			.headers(headers_2)
			.formParam("id", "11")
			.formParam("name", "Freddy Jr.")
			.formParam("birthDate", "2010/03/09")
			.formParam("type", "bird")
			.formParam("_csrf", "${stoken}"))
		.pause(3)
	}

	object EditPet2 {
		val editpet2 = exec(http("request_5")
			.get("/owners/1/pets/11/edit")
        	.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(12)
		.exec(http("request_6")
			.post("/owners/1/pets/11/edit")
			.headers(headers_2)
			.formParam("id", "11")
			.formParam("name", "")
			.formParam("birthDate", "2010/03/09")
			.formParam("type", "bird")
			.formParam("_csrf", "${stoken}"))
		.pause(3)
	}

	val his_14_positiva = scenario("Historia 14 positiva").exec(
															Home.home,
															Login.login,
															Profile.profile,
															Pets.pets,
															EditPet.editpet)

															
	val his_14_negativa = scenario("Historia 14 negativa").exec(
															Home.home,
															Login.login,
															Profile.profile,
															Pets.pets,
															EditPet2.editpet2)

	setUp(his_14_positiva.inject(rampUsers(3500) during (100 seconds)),
			his_14_negativa.inject(rampUsers(3500) during (100 seconds))
			).protocols(httpProtocol)
			.assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}