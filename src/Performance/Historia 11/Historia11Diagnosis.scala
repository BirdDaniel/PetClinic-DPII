package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia11Diagnosis extends Simulation {

	
	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Upgrade-Insecure-Requests" -> "1")


	val headers_5 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")


	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
			.pause(28)
	} 

	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))
		).pause(43)
		 .exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object Login2{
		val login2 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(43)
		.exec(http("Logged2")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object Profile{
		val profile = exec(http("Profile")
			.get("/owners/1")
			.headers(headers_0))
		.pause(10)
	}

	object MyPets{
		val myPets = exec(http("MyPets")
			.get("/owners/1/myPetList")
			.headers(headers_0))
		.pause(10)
	}

	object PetList{
		val petList = exec(http("PetList")
			.get("/owners/1/myPetList/residence")
			.headers(headers_0))
		.pause(6)
	}

	object PetList2{
		val petList2 = exec(http("PetList2")
			.get("/owners/1/myPetList/residence")
			.headers(headers_0)
			.check(status.is(403)))
		.pause(6)
	}

	val scp = scenario("Owner").exec(Home.home,
									Login.login,
									Profile.profile,
									MyPets.myPets,
									PetList.petList)
	
	val scn = scenario("Employee").exec(Home.home,
									Login2.login2,
									Profile.profile,
									MyPets.myPets,
									PetList2.petList2)

	setUp(scn.inject(rampUsers(3500) during (100 seconds)),
		  scp.inject(rampUsers(3500) during (100 seconds)))
		  .protocols(httpProtocol)
          .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}