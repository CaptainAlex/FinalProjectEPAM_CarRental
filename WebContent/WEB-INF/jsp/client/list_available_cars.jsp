<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<%-- head start --%>
<c:set var="title" value="Cars" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%--	head end  --%>
<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content center">
			
			<form>
					<input type="hidden" name="command" value="availableCarsList" /> <select
						name="sort">
						<option value="name"><fmt:message key='list_available_cars.name'/></option>
						<option value="price"><fmt:message key='list_available_cars.price'/></option>
					</select>
					<p>
						<input type="submit" value="<fmt:message key='list_available_cars.sort'/>" />
					</p>
				</form>
			<c:forEach var="car"
					items="${availableCarsListById}">

					<myT:carstable carDriverPrice="${car.carDriverPrice}"
						name="${car.name}" carPrice="${car.carPrice}"
						carClass="${car.carClass}" mark="${car.mark}" id="${car.id}"></myT:carstable>

				</c:forEach> 
				<%-- CONTENT --%>
				 <%--	Cars sorting form  --%>
				 <c:if test="${fn:length(param.sort) > 0}">
					<c:forEach var="car" items="${availableCarsList}">

						<myT:carstable carDriverPrice="${car.carDriverPrice}"
							name="${car.name}" carPrice="${car.carPrice}"
							carClass="${car.carClass}" mark="${car.mark}" id="${car.id}"></myT:carstable>

					</c:forEach>
				</c:if> 
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>

	<%-- END BODY CLASS--%>

	
	<%--footer end --%>
</body>
</html>