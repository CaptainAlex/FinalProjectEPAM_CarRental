<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="UpdateUserInfo" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

	<table id="main-container">

		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>


		<tr >
			<td class="content center">
			<%-- CONTENT --%>

				
				<form id="login_form" action="controller" method="post">


					<input type="hidden" name="command" value="updateUserInfo"/>

					<fieldset >
						<legend><fmt:message key='update_client_info.firstName'/></legend>
						<input type="text" name="firstName" required pattern="\w{3,20}"  maxlength="20" 
						title="from 3 to 20 symbols (Latin alphabet, numbers)"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend><fmt:message key='update_client_info.lastName'/></legend>
						<input type="text" name="lastName" required pattern="\w{3,20}"  maxlength="20" 
						title="from 3 to 20 symbols (Latin alphabet, numbers)"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend><fmt:message key='update_client_info.passport'/></legend>
						<input type="text" name="passportNumber" required pattern="\w{3,20}"  maxlength="20" 
						title="from 3 to 20 symbols (Latin alphabet, numbers)"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend><fmt:message key='update_client_info.telephone'/></legend>
						<input type="text" name="phoneNumber" required pattern="\d{1,20}" 
						title="Only numbers, max 20 numbers"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend><fmt:message key='update_client_info.email'/></legend>
						<input type="email" name="email" required maxlength="40" 
						title="from 3 to 40 symbols (Latin alphabet, numbers, symbols)"/><br/>
					</fieldset><br/>
					
					
					<input type="submit" value="<fmt:message key='update_client_info.button'/>">								
				</form> 
				
				
			<%-- CONTENT --%>

			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>