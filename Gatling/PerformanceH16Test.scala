package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PerformanceH16Test extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36")

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

	object Residences{
		
		val residences = exec(http("RESIDENCE_LIST")
			.get("/residence/findAll")
			.headers(headers_0))
		.pause(10)
		
	}

	object ResidenceDetails{
		
		val residenceD = exec(http("RESIDENCE_DETAILS")
			.get("/residence/1")
			.headers(headers_0))
		.pause(15)
		
	}

	object CreatedReqRes{
		

		val created = exec(http("FORM_REQ4CLINIC")
			.get("/createRequest/residence/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15).exec(http("CREATED")
			.post("/createRequest/residence/1")
			.headers(headers_3)
			.formParam("requestDate", "2020/05/28 20:51")
			.formParam("serviceDate", "2020/05/30 20:51")
			.formParam("finishDate", "2020/05/31 20:51")
			.formParam("pet", "Leo")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
		
	}

	object NoCreatedReqRes{
		
		val noCreated = exec(http("FORM_REQ4CLINIC")
			.get("/createRequest/residence/1")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10)
		.exec(http("NO_CREATED")
			.post("/createRequest/residence/1")
			.headers(headers_3)
			.formParam("requestDate", "2020/05/28 20:52")
			.formParam("serviceDate", "2020/05/13 17:41")
			.formParam("finishDate", "2020/03/19 00:00")
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

	val reqRSnc = scenario("Request_Residence").exec(Home.home,
						Login.login,
						Residences.residences,
						ResidenceDetails.residenceD,
						CreatedReqRes.created)
	
	val noReqRSnc = scenario("No_Request_Residence").exec(Home.home,
						Login.login,
						Residences.residences,
						ResidenceDetails.residenceD,
						NoCreatedReqRes.noCreated)
	
	setUp(reqCSnc.inject(atOnceUsers(1)),
	      noReqCSnc.inject(atOnceUsers(1)),
	      reqRSnc.inject(atOnceUsers(1)),
	      noReqRSnc.inject(atOnceUsers(1))).protocols(httpProtocol)
}
