<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="InsertReg" method="post" enctype="multipart/form-data">
	<table border="">
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" /></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="pname" /></td>
		</tr>
		<tr>
			<td>암호</td>
			<td><input type="text" name="pw" /></td>
		</tr>
		<tr>
			<td>파일</td>
			<td><input type="file" name="upfile" /></td>
		</tr>
		<tr>
			<td>내용</td>
			<td><textarea name="content" cols="30" rows="5" >내용내용</textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="right">
			<a href="List?page=${param.page}">목록으로</a>
			<input type="submit" value="작성">
			<input type="reset" value="초기화">
			</td>
		</tr>
	</table>
</form>
