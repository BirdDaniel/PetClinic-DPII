package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia19Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	object Home{
		val home=exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(30)
	}

	object Login{
		val login=exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "owner3")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object Residences{
		val residences=exec(http("Residences")
			.get("/residence/findAll")
			.headers(headers_0))
		.pause(7)
	}
	object ResidenceDetails{
		val residenceDetails=exec(http("ResidenceDetails")
			.get("/residence/1")
			.headers(headers_0))
		.pause(10)
	}

	object LoginNeg{
		val loginNeg=exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(16)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "emp3")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(12)
	}

	object Forbidden{
		val forbidden=exec(http("Forbidden")
			.get("/residence/1")
			.headers(headers_0)
			.check(status.is(403)))
		.pause(1)
	}


	val scn1 = scenario("PerformanceH19Test").exec(Home.home,Login.login,
		Residences.residences,ResidenceDetails.residenceDetails)
	
	val scn2 = scenario("PerformanceH19Test2").exec(Home.home,LoginNeg.loginNeg,Forbidden.forbidden)

	setUp(
		scn1.inject(rampUsers(3500) during (100 seconds)),
		scn2.inject(rampUsers(3500) during (100 seconds))
		).protocols(httpProtocol)
        .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}