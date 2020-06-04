package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia15Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")


	val headers_0 = Map("Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive")

	object Home{
		val home= exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(7)
	}

	object Login{
		val login= exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(11)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "emp5")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(8)
	}

	object Colleagues{
		val colleagues= exec(http("Colleagues")
			.get("/employees/5/colleagues")
			.headers(headers_0))
		.pause(6)
	}
	object CasoNeg{
		val casoNeg= exec(http("CasoNeg")
			.get("/employees/4/colleagues")
			.headers(headers_0))
	}

	val scn1 = scenario("PerformanceH15Test").exec(Home.home,
	                                               Login.login,
						                           Colleagues.colleagues)

	val scn2 = scenario("PerformanceH15TestNeg").exec(Home.home,
	                                                  Login.login,
				                                      CasoNeg.casoNeg)


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