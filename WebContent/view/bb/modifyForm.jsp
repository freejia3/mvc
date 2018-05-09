<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script>
<c:if test="${msg!=null }">
	alert("${msg}");
</c:if>

function fileDelete(){
	if(confirm('파일을 삭제하시겠습니까?')){
		var frm = document.frm;
		frm.action="FileDelete";
		frm.submit();
	}
}
</script>
<body>
<form name="frm" action="ModifyReg" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" value="${data.id }" >
	<input type="hidden" name="page" value="${param.page }" >
	<input type="hidden" name="seq" value="${data.seq }">

	<table border="">
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" value="${data.title }" /></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="pname" value="${data.pname }" /></td>
		</tr>
		<tr>
			<td>암호</td>
			<td><input type="text" name="pw" /></td>
		</tr>

<!-- 원글인 경우, 파일이 보인다. 아닌 경우 파일이 없음-->
		<c:choose>
		<c:when test="${data.seq==0 }">
		<tr>
			<td>파일</td>
			<td>
			<!-- 파일이 있는경우 파일삭제 버튼이 보이고, 파일이 없는경우 파일업로드 버튼이 보인다 -->
				<c:choose>
					<c:when test="${data.upfile!='' }">
					${data.upfile}
					<input type="hidden" name="upfile" value="${data.upfile }">
					<input type="button" onClick="fileDelete()" value="파일삭제">
					</c:when>
					<c:otherwise>
						<input type="file" name="upfile">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:when>
		<c:otherwise>
			<input type="hidden" name="upfile" value="">
		</c:otherwise>
		</c:choose>
		<tr>
			<td>내용</td>
			<td><textarea name="content" rows="5" cols="30">${data.content }</textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="수정" />
				<a href="ModifyForm?page=${param.page }&id=${data.id }">초기화</a>
				<a href="Detail?page=${param.page }&id=${data.id }">뒤로</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>