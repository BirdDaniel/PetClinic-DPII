package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia16Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_8 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Purpose" -> "prefetch",
		"Upgrade-Insecure-Requests" -> "1")


	object Home {

		val home = exec(http("HOME")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login{

		val login = exec(http("LOGIN")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LOGGED")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}

	object Clinics{
		
		val clinics = exec(http("CLINIC_LIST")
			.get("/clinic/findAll")
			.headers(headers_0))
		.pause(5)
	}

	object ClinicDetails{
		
		val clinicD = exec(http("CLINIC_DETAILS")
			.get("/clinic/1")
			.headers(headers_0))
		.pause(5)
	}

	object CreatedReqCli{
		
		val created = exec(http("FORM_REQ4CLINIC")
			.get("/createRequest/clinic/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10).exec(http("CREATED")
			.post("/createRequest/clinic/1")
			.headers(headers_3)
			.formParam("requestDate", "2020/05/28 09:25")
			.formParam("finishDate", "2050/12/29 23:59")
			.formParam("serviceDate", "2020/06/06 15:00")
			.formParam("pet", "Freddy")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
	}

	object NoCreatedReqCli{

		val noCreated = exec(http("FORM_REQ4CLINIC")
			.get("/createRequest/clinic/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))).		
		exec(http("NO_CREATED")
			.post("/createRequest/clinic/1")
			.headers(headers_3)
			.formParam("requestDate", "2020/05/28 09:27")
			.formParam("finishDate", "2050/12/29 23:59")
			.formParam("serviceDate", "2020/05/13 17:41")
			.formParam("pet", "Leo")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	


	val reqCSnc = scenario("Request_Clinic").exec(Home.home,
						Login.login,
						Clinics.clinics,
						ClinicDetails.clinicD,
						CreatedReqCli.created)
	
	val noReqCSnc = scenario("No_Request_Clinic").exec(Home.home,
						Login.login,
						Clinics.clinics,
						ClinicDetails.clinicD,
						NoCreatedReqCli.noCreated)
	
	setUp(
		reqCSnc.inject(rampUsers(3500) during (100 seconds)),
	    noReqCSnc.inject(rampUsers(3500) during (100 seconds)),
	).protocols(httpProtocol)
	.assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}
