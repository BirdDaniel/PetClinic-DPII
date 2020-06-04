package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia12Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept-Encoding" -> "gzip, deflate",
		"Accept-Language" -> "es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3",
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive",
		"User-Agent" -> "Microsoft-CryptoAPI/10.0")

    val uri2 = "http://ctldl.windowsupdate.com/msdownload/update/v3/static/trustedr/en/authrootstl.cab"

	object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object Login {
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(26)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "emp3")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(7)
	}

	object ShowAppointments {
		val showAppointments = exec(http("ShowAppointments")
			.get("/employees/3/appointments")
			.headers(headers_0))
		.pause(16)
	}

	object DeclineAppointment {
		val declineAppointment = exec(http("DeclineAppointment")
			.get("/employees/3/appointments/5/decline")
			.headers(headers_0))
		.pause(23)
	}

	object YouCantDoThis {
		val youCantDoThis = exec(http("YouCantDoThis")
			.get("/employees/1/appointments/1/decline")
			.headers(headers_0))
		.pause(12)
	}


	val positiveScn = scenario("Positive").exec(Home.home,
	                                            Login.login,
												ShowAppointments.showAppointments,
												DeclineAppointment.declineAppointment)

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