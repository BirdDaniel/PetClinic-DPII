package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia11 extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.ico""", """.*\.png"""), WhiteList())
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Proxy-Connection" -> "keep-alive")

	val headers_4 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_5 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
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
			.formParam("_csrf", "${stoken}")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
		.pause(11)
	}

	object Login2{
		val login2 = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(43)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
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
	val scp = scenario("Owner").exec(Home.home,
									Login.login,
									Profile.profile,
									MyPets.myPets,
									PetList.petList)
	
	val scn = scenario("Employee").exec(Home.home,
									Login2.login2,
									Profile.profile,
									MyPets.myPets,
									PetList.petList)

	setUp(scn.inject(atOnceUsers(1)),
		  scp.inject(atOnceUsers(1)))
		  .protocols(httpProtocol)
}