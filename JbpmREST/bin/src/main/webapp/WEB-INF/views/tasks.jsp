<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Task Instance</title>
</head>
<body>
	<section>
		<div class="jumbotron jumbotron-fluid" style="height: 200px;">
			<div class="container">
				<h2>JBPM REPOSITORY</h2>
				<p>Tasks</p>
				<button type="button" class="btn btn-default btn-sm pull-right"
					onclick="location.href='/workflow'">
					<span class="glyphicon glyphicon-log-out"></span> Log out
				</button>
			</div>
		</div>
	</section>

	<header class="navbar navbar-light navbar-toggleable-md bd-navbar">
		<nav class="container">
			<ul class="nav nav-tabs">
				<li><a href="/workflow/process/management/">Processes</a></li>
				<li class="active"><a href="/workflow/task/management/">Tasks</a></li>
				<li><a href="/workflow/process/status/">Process Instance</a></li>
				<li><a href="/workflow/task/status/">Task Management</a></li>
			</ul>
		</nav>
	</header>

	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">Tasks List</div>
			<div class="panel-body">

				<form method="get" commandName="task"
					modelAttribute="selectedTasksName">

					<div class="form-group col-xs-12 row">
						<p>&nbsp; &nbsp; &nbsp;Select a task to claim</p>
						<div class="col-xs-10">
							<select path="taskIdList" name="taskId"
								class="form-control btn-group inline pull-left">
								<!-- <option value="" selected>Please select process definition</option> -->
								<c:forEach items="${task.taskIdList }" var="item">
									<option value="${item}">${item}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2">
							<input type="submit" class="btn btn-primary" value="Claim"
								onclick="form.action='/workflow/task/start'" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>