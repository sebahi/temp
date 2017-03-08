<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="bootstrap.confirm.js"></script>

<title>Task Form</title>
</head>
<body>
	<section>
	<div class="jumbotron jumbotron-fluid" style="height: 200px;">
		<div class="container">
			<h2>JBPM REPOSITORY</h2>
			<p>Task Management</p>
			<button type="button" class="btn btn-default btn-sm pull-right"
				onclick="location.href='/workflow'">
				<span class="glyphicon glyphicon-log-out"></span> Log out
			</button>
			<button type="button" class="btn btn-primary btn-sm pull-right"
				onclick="location.href='/workflow/process/management/'">
				<span class="glyphicon glyphicon-home"></span> Home
			</button>
		</div>
	</div>
	</section>

	<form:form method="get" modelAttribute="resubmit">
		<div class="container">

			<div class="panel panel-primary">
				<div class="panel-heading">Task</div>
				<div class="panel-body">
					<input type="checkbox" data-toggle-key-codes="[32]" name="resubmit"
						path="resubmit"> <input class="btn btn-default pull-right"
						type="submit" path="resubmit"
						onclick="form.action='/workflow/task/claim'">This bag
					is already exists, do you want to re-submit the bag again?
				</div>
			</div>
		</div>

	</form:form>

</body>
</html>