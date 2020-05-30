<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->
<%@ attribute name="name" required="true" rtexprvalue="true"
	description="Name of the active menu: home, owners, vets or error"%>

<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="<spring:url value="/" htmlEscape="true" />"><span></span></a>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#main-navbar">
				<span class="sr-only"><os-p>Toggle navigation</os-p></span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="navbar-collapse collapse" id="main-navbar">
			<ul class="nav navbar-nav">
				
				<petclinic:menuItem active="${name eq 'home'}" url="/"
					title="home page">
					<span class="glyphicon glyphicon-home" aria-hidden="true"></span>
					<span>Home</span>
				</petclinic:menuItem>
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'owners'}" url="/owners/${loggedUser}"
					title="My Profile">
					<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
					<span>Profile</span>
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'requestsOwner'}" url="/owners/${loggedUser}/myRequestList"
					title="Requests">
					<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
					<span>Requests</span>
				</petclinic:menuItem>
				</sec:authorize>

				<sec:authorize access="hasAuthority('employee')">
				<petclinic:menuItem active="${name eq 'requestsEmployee'}" url="/employees/${loggedUser}/requests"
					title="Requests">
					<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
					<span>Requests</span>
					</petclinic:menuItem>
					
					<petclinic:menuItem active="${name eq 'requestsPayEmployee'}" url="/employees/${loggedUser}/payments"
					title="Payments">
					<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
					<span>Payments</span>
					
				</petclinic:menuItem>
							
				<sec:authorize access="hasAuthority('employee')">
				<petclinic:menuItem active="${name eq 'pets'}" url="/employees/${loggedUser}/pets"
					title="Pets">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>My Pets</span>
				</petclinic:menuItem>
				</sec:authorize>
					<petclinic:menuItem active="${name eq 'colleaguesEmployee'}" url="/employees/${loggedUser}/colleagues"
					title="Colleagues">
					<span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
					<span>Colleagues</span>
					
				</petclinic:menuItem>
				<petclinic:menuItem active="${name eq 'appointmentsEmployee'}" url="/employees/${loggedUser}/appointments"
					title="Appointments">
					<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
					<span>Appointments</span>
				</petclinic:menuItem>
				</sec:authorize>
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'residences'}" url="/residence/findAll"
					title="residences">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Residences</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'clinics'}" url="/clinic/findAll"
					title="clinics">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Clinics</span>
				</petclinic:menuItem>
				</sec:authorize>
				<sec:authorize access="hasAuthority('owner')">
				<petclinic:menuItem active="${name eq 'parks'}" url="/parks"
					title="clinics">
					<span class="glyphicon glyphicon-tree-deciduous" aria-hidden="true"></span>
					<span>Parks</span>
				</petclinic:menuItem>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('employee')">
				<petclinic:menuItem active="${name eq 'items'}" url="/employees/${loggedUser}/itemsList"
					title="Inventory">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					<span>Inventory</span>
				</petclinic:menuItem>
				</sec:authorize>
	
				
			</ul>




			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="<c:url value="/login" />">Login</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<span class="glyphicon glyphicon-chevron-down">
							</span>
							Register
						</a>
							<ul class="dropdown-menu">
								<li>
									<a href="<c:url value="/users/newOwner" />"class="btn btn-success btn-sm">As Owner</a>
								</li>
								<li>
									<a href="<c:url value="/users/newEmployee" />"class="btn btn-success btn-sm">As Employee</a>
								</li>
						</ul>
					</li>
					<li class="divider"></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span class="glyphicon glyphicon-user"></span>�
							<strong><sec:authentication property="name" /></strong> <span
							class="glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul class="dropdown-menu">
							<li>
								<div class="navbar-login">
									<div class="row">
										<div class="col-lg-4">
											<p class="text-center">
												<span class="glyphicon glyphicon-user icon-size"></span>
											</p>
										</div>
										<div class="col-lg-8">
											<p class="text-left">
												<strong><sec:authentication property="name" /></strong>
											</p>
											<p class="text-left">
												<a href="<c:url value="/logout" />"
													class="btn btn-primary btn-block btn-sm">Logout</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
<!-- 							
                            <li> 
								<div class="navbar-login navbar-login-session">
									<div class="row">
										<div class="col-lg-12">
											<p>
												<a href="#" class="btn btn-primary btn-block">My Profile</a>
												<a href="#" class="btn btn-danger btn-block">Change
													Password</a>
											</p>
										</div>
									</div>
								</div>
							</li>
-->
						</ul></li>
				</sec:authorize>
			</ul>
		</div>



	</div>
</nav>
