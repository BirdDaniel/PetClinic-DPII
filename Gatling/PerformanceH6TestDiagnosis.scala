package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class PerformanceH6TestDiagnosis extends Simulation {

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

	object Login{

		val login = exec(http("LOGIN")
			.get("/login")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(15)
		.exec(http("LOGGED")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "emp1")
			.formParam("password", "3mpl0")
			.formParam("_csrf", "${stoken}"))
		.pause(15)
	}


	object Inventory{
		
		val inventory = exec(http("INVENTORY")
			.get("/employees/1/itemsList")
			.headers(headers_0))
		.pause(5)
		
	}

	object Add{
		
		val add = exec(http("ADD_FORM")
			.get("/employees/1/itemsList/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10).exec(http("ADD")
			.post("/employees/1/itemsList/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Collares Valiosos")
			.formParam("price", "5.0")
			.formParam("sale", "0.5")
			.formParam("description", "Un collar valioso")
			.formParam("stock", "10")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	object Update{
		
		val update = exec(http("EDIT_FORM")
			.get("/employees/1/itemsList/14/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.pause(10).exec(http("UPDATE")
			.post("/employees/1/itemsList/14/edit")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Collares Baratisimos")
			.formParam("price", "10.0")
			.formParam("sale", "0.1")
			.formParam("description", "Un collar menos valioso, pero m�s caro")
			.formParam("stock", "5")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	object Remove{
		
		val remove = exec(http("request_9")
			.get("/employees/1/itemsList/14/delete")
			.headers(headers_0))
		.pause(5)
		
	}


	object NoAdd {

		val noAdd = exec(http("ADD_FORM")
			.get("/employees/1/itemsList/new")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken")))
		.exec(http("NO_ADD")
			.post("/employees/1/itemsList/new")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Collar caro")
			.formParam("price", "Ten")
			.formParam("sale", "2.0")
			.formParam("description", "Un collar valioso")
			.formParam("stock", "-5")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	object NoUpdate{

		val noUpdate = exec(http("EDIT_FORM")
			.get("/employees/1/itemsList/14/edit")
			.headers(headers_0)
			.check(css("input[name=_csrf]", "value").saveAs("stoken"))).
		exec(http("NO_UPDATE")
			.post("/employees/1/itemsList/1/edit")
			.headers(headers_3)
			.formParam("id", "")
			.formParam("name", "Pienso")
			.formParam("price", "TEN")
			.formParam("sale", "0.5")
			.formParam("description", "Description 1")
			.formParam("stock", "3")
			.formParam("_csrf", "${stoken}"))
		.pause(5)
		
	}

	object NoRemove{
		
		val noRemove = exec(http("NO_REMOVE")
			.get("/employees/1/itemsList/0/delete")
			.headers(headers_0))
		.pause(5)
		
	}

	object BadURL{
		
		val badURL = exec(http("BAD_URL")
			.get("/employees/0/itemsList")
			.headers(headers_0))
		.pause(5)
		
	}

	val addSnc = scenario("Add").exec(Home.home,
					Login.login,
					Inventory.inventory,
					Add.add)

	
	val editSnc = scenario("Edit").exec(Home.home,
					Login.login,
					Inventory.inventory,
					Update.update)

	val deleteSnc = scenario("Remove").exec(Home.home,
					Login.login,
					Inventory.inventory,
					Remove.remove)

	val noAddSnc = scenario("NoAdd").exec(Home.home,
					Login.login,
					Inventory.inventory,
					NoAdd.noAdd)

	
	val noEditSnc = scenario("NoEdit").exec(Home.home,
					Login.login,
					Inventory.inventory,
					NoUpdate.noUpdate)

	val noDeleteSnc = scenario("NoRemove").exec(Home.home,
					Login.login,
					Inventory.inventory,
					NoRemove.noRemove)

	val badURLSnc = scenario("BadURL").exec(Home.home,
					Login.login,
					BadURL.badURL)

	setUp(addSnc.inject(rampUsers(20000) during (10 seconds)),
	      editSnc.inject(rampUsers(20000) during (10 seconds)),
	      deleteSnc.inject(rampUsers(20000) during (10 seconds)),
	      noAddSnc.inject(rampUsers(20000) during (10 seconds)),
	      noEditSnc.inject(rampUsers(20000) during (10 seconds)),
	      noDeleteSnc.inject(rampUsers(20000) during (10 seconds)),
	      badURLSnc.inject(rampUsers(20000) during (10 seconds))
	).protocols(httpProtocol).assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}
