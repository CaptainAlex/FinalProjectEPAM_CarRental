
<%--=========================================================================== 
JSTL core tag library.
===========================================================================--%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--=========================================================================== 
JSTL functions tag library.
===========================================================================--%> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%--=========================================================================== 
 JSTL fmt tag library.
===========================================================================--%> 

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" required="true"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="mark" required="true"%>
<%@ attribute name="carClass" required="true"%>
<%@ attribute name="carPrice" required="true"%>
<%@ attribute name="carDriverPrice" required="true"%>


<center>
	<table>
		<tr>
			<td rowspan="3" colspan="5" align="center">
			<img
				style="background: url('images/cars/id/${id}.jpg'); width:330px; height:200px; border: 0px; border-radius:20px; -webkit-border-radius:20px; -moz-border-radius:20px;">
			<td width="100px" align="center"><fmt:message key='carstable.number'/></td>
			<td width="100px" align="center"><fmt:message key='carstable.name'/></td>
			<td width="100px" align="center"><fmt:message key='carstable.mark'/></td>
			<td width="100px" align="center"><fmt:message key='carstable.class'/></td>
			<td width="100px" align="center"><fmt:message key='carstable.price'/></td>
			<td width="100px" align="center"><fmt:message key='carstable.driverPrice'/></td>
			
		</tr>
		<tr>
		<td width="100px" align="center">${id}</td>
			<td width="100px" align="center">${name}</td>
			<td width="100px" align="center">${mark}</td>
			<td width="100px" align="center">${carClass}</td>
			<td width="100px" align="center">${carPrice}</td>
			<td width="100px" align="center">${carDriverPrice}</td>
		</tr>
		</table>
</center>

<p>