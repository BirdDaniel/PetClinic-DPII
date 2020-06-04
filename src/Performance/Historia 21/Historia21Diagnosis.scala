package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia21Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.png""", """.*.js""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	object Home{
		val home = exec(http("Home")
			.get("/"))
		.pause(5)
	}
	object Login{
		val login = exec(http("Login")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(12)
		.exec(http("LoggedOwner")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object Login2{
		val login2 = exec(http("Login2")
			.get("/login")
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(12)
		.exec(http("LoggedEmployee")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}
	 
	object Parks{
		val parks = exec(http("Parks")
			.get("/parks"))
		.pause(9)
	}

	object Error{
		val error = exec(http("Error")
			.get("/parks")
			.check(status.is(403)))
		.pause(4)
	}
	

	val OwnersScn = scenario("Owners").exec(Home.home, 
												Login.login,
												Parks.parks)

	val EmployeesScn = scenario("Employees").exec(Home.home, 
												Login2.login2,
												Error.error)				
								

	setUp(OwnersScn.inject(rampUsers(3500) during (100 seconds)),
	 EmployeesScn.inject(rampUsers(3500) during (100 seconds)) 
	 ).protocols(httpProtocol)
     .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}