package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PerformanceH2Test extends Simulation {

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

	object Home {

		val home = exec(http("HOME")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object LoginOwner{

		val login = exec(http("LOGIN")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LOGGED_AS_OWNER")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}

	object LoginEmp{

		val login = exec(http("LOGIN")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LOGGED_AS_EMP")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}

	object Residences{
		
		val residences = exec(http("RESIDENCES")
			.get("/residence/findAll")
			.headers(headers_0))
		.pause(5)
	}

	object ResidenceErrors{
		
		val residenceE = exec(http("ERROR_RESIDENCE")
			.get("/residence/findAll")
			.headers(headers_0)
			.check(status.is(403)))
		.pause(5)
		
	}

	object Clinics{
		
		val clinics = exec(http("LIST")
			.get("/clinic/findAll")
			.headers(headers_0))
		.pause(5)
	}

	object ClinicErrors{
		
		val clinicC = exec(http("ERROR_CLINIC")
			.get("/clinic/findAll")
			.headers(headers_0)
			.check(status.is(403)))
		.pause(5)
		
	}


	val residenceSnc = scenario("Residence").exec(Home.home,
						LoginOwner.login,
						Residences.residences)
	
	val noResidenceSnc = scenario("Residence_Negative").exec(Home.home,
						LoginEmp.login,
						ResidenceErrors.residenceE)
	
	val clinicSnc = scenario("Clinic").exec(Home.home,
						LoginOwner.login,
						Clinics.clinics)

	val noClinicSnc = scenario("Clinic_Negative").exec(Home.home,
						LoginEmp.login,
						ClinicErrors.clinicC)
	
	setUp(residenceSnc.inject(atOnceUsers(1)),
	      noResidenceSnc.inject(atOnceUsers(1)),
	      clinicSnc.inject(atOnceUsers(1)),
	      noClinicSnc.inject(atOnceUsers(1))).protocols(httpProtocol)
}
