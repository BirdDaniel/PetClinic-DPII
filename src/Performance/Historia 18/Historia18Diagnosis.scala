package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Historia18Diagnosis extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Proxy-Connection" -> "Keep-Alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "Keep-Alive")

	val headers_6 = Map(
		"Accept-Language" -> "es-ES,es;q=0.9,en;q=0.8",
		"Pragma" -> "no-cache",
		"Proxy-Connection" -> "keep-alive",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_10 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Upgrade-Insecure-Requests" -> "1")

    val uri1 = "https://www.sandbox.paypal.com/signin"
	

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
		.pause(13)
		.exec(http("request_5")
			.post("/login")
			.headers(headers_0)
			.formParam("username", "owner1")
			.formParam("password", "0wn3r")
			.formParam("_csrf", "${stoken}"))
		.pause(11)
	}

	object Appointments{
		val appointments = exec(http("RequestList")
			.get("/owners/1/myRequestList")
			.headers(headers_0))
		.pause(1)
		.exec(http("Appointments")
			.get("/owners/1/appointments")
			.headers(headers_0))
		.pause(16)
	}

	object Pay{
		val pay = exec(http("Pay")
			.get("/pay/4")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(24)
		.exec(http("SandBox")
			.post(uri1 + "?intent=checkout&ctxId=xo_ctx_EC-1CX9729382470825S&returnUri=%2Fwebapps%2Fhermes&state=%3Fflow%3D1-P%26ulReturn%3Dtrue%26token%3DEC-1CX9729382470825S&locale.x=es_ES&country.x=ES&flowId=EC-1CX9729382470825S")
			.headers(headers_10)
			.formParam("_csrf", "${stoken}")
			.formParam("_sessionID", "uAvXdbSMiGIpbwn_FHAo2q-oncTZhe1K")
			.formParam("locale.x", "es_ES")
			.formParam("processSignin", "main")
			.formParam("fn_sync_data", "fn_sync_data")
			.formParam("intent", "checkout")
			.formParam("ads-client-context", "checkout")
			.formParam("flowId", "EC-1CX9729382470825S")
			.formParam("ads-client-context-data", """{"context_id":{"context_id":"EC-1CX9729382470825S","channel":0,"flow_type":"checkout"}}""")
			.formParam("ctxId", "xo_ctx_EC-1CX9729382470825S")
			.formParam("requestUrl", "/signin?intent=checkout&ctxId=xo_ctx_EC-1CX9729382470825S&returnUri=%2Fwebapps%2Fhermes&state=%3Fflow%3D1-P%26ulReturn%3Dtrue%26token%3DEC-1CX9729382470825S&locale.x=es_ES&country.x=ES&flowId=EC-1CX9729382470825S")
			.formParam("forcePhonePasswordOptIn", "")
			.formParam("returnUri", "/webapps/hermes")
			.formParam("state", "?flow=1-P&ulReturn=true&token=EC-1CX9729382470825S")
			.formParam("phoneCode", "ES +34")
			.formParam("login_email", "")
			.formParam("captchaCode", "")
			.formParam("login_phone", "")
			.formParam("initialSplitLoginContext", "inputEmail")
			.formParam("isTpdOnboarded", "")
			.formParam("login_password", "")
			.formParam("captcha", "")
			.formParam("btnLogin", "Login")
			.formParam("splitLoginContext", "inputEmail"))
		.pause(70)
	}

	object Success{
		val success = exec(http("Success")
			.get("/success")
			.headers(headers_0))
		.pause(5)
		.exec(http("request_12")
			.get("/")
			.headers(headers_0))
		.pause(9)
	}

	object Cancel{
		val cancel = exec(http("Cancel")
			.get("/cancel")
			.headers(headers_0))
		.pause(5)
		.exec(http("request_12")
			.get("/")
			.headers(headers_0))
		.pause(9)
	}

	val scp = scenario("Owner").exec(Home.home,
									Login.login,
									Appointments.appointments,
									Pay.pay,
									Success.success)
	val scn = scenario("Owner2").exec(Home.home,
									Login.login,
									Appointments.appointments,
									Pay.pay,
									Cancel.cancel)
		

	setUp(scn.inject(rampUsers(70000) during (10 seconds)),
		  scp.inject(rampUsers(70000) during (10 seconds)))
		  .protocols(httpProtocol)
		 
}