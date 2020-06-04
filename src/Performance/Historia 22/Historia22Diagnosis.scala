  	package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia22Diagnosis extends Simulation {

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

	val csvFeeder = csv("parks.csv")

	object Home{
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(28);
	} 

	object Login{
		val login = exec(http("Login")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(43)
		.exec(http("Logged")
			.post("/login")
			.headers(headers_5)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_6")
			.get("/")
			.headers(headers_1)))
		.pause(6)
	}

	object Parks{
		val parks = exec(http("Parks")
			.get("/parks")
			.headers(headers_0))
		.pause(8)
	}

	object NewPark{
		val newPark = exec(http("NewPark")
		.get("/parks/new")
		.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(18)
		.feed(csvFeeder)
		.exec(http("CreatePark")
			.post("/parks/new")
			.headers(headers_5)
			.formParam("id", "")
			.formParam("name", "${name}")
			.formParam("address", "${address}")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_12")
			.get("/parks")
			.headers(headers_1)))
		.pause(12)
	}

    object NewPark2{
		val newPark2 = exec(http("NewPark2")
		.get("/parks/new")
		.headers(headers_0)
		.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(18)
		.feed(csvFeeder)
		.exec(http("CreatePark")
			.post("/parks/new")
			.headers(headers_5)
			.formParam("id", "")
			.formParam("name", "")
			.formParam("address", "${address}")
			.formParam("_csrf", "${stoken}")
			.resources(http("request_12")
			.get("/parks")
			.headers(headers_1)))
		.pause(12)
	}

	val scp = scenario("Owner").exec(Home.home,
									Login.login,
									Parks.parks,
									NewPark.newPark	
									)
	val scn = scenario("Owner2").exec(Home.home,
									Login.login,
									Parks.parks,
									NewPark2.newPark2
									)

	setUp(scn.inject(rampUsers(3500) during (100 seconds)),
		  scp.inject(rampUsers(3500) during (100 seconds)))
		  .protocols(httpProtocol)
           .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}