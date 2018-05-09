<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="ReplyReg" method="post">
<input type="hidden" name="id" value="${data.id }" />
<input type="hidden" name="page" value="${param.page }" />
	<table border="">
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" value="${data.title }"></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="pname"></td>
		</tr>
		<tr>
			<td>암호</td>
			<td><input type="text" name="pw"></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea name="content" rows="5" cols="30">${data.content }</textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="작성" />
			<a href="Detail?page=${param.page }&id=${data.id }">뒤로</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>