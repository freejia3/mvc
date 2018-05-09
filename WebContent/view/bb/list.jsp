<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table border="">
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>작성자</td>
		<td>작성일</td>
	</tr>
	<c:choose>
		<c:when test="${data.size()==0 }">
			<tr>
			<td colspan="4" align="center">내용이 없어연.</td>
			</tr>
		</c:when>
		<c:otherwise>

		<!-- 리스트 뽑기  -->
		<c:forEach var="ee" items="${data }" varStatus="no">
			<tr>
				<td>${no.index+start }</td>
				<td>
				<c:if test="${ee.lev>0 }">
					<c:forEach begin="1" end="${ee.lev }">
					&nbsp;&nbsp;
					</c:forEach>
					└
				</c:if>
				<a href="Detail?id=${ee.id }&page=${page}">${ee.title }</a></td>
				<td>${ee.pname }</td>
				<td><fmt:formatDate value="${ee.reg_date }" pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:forEach>
		<!-- 페이지 번호 보여주기  -->
			<tr>
				<td>
					<c:if test="${startPage > 1 }">
						<a href="List?page=1">[처음]</a>
						<a href="List?page=${startPage-1 }">이전</a>
					</c:if>

					<c:forEach var="i" begin="${startPage }" end="${endPage }">
						<c:choose>
							<c:when test="${i==page }">
							[${i }]
							</c:when>
							<c:otherwise>
							<a href="List?page=${i }">${i }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${endPage < totalPage }">
						<a href="List?page=${endPage+1 }">다음</a>
						<a href="List?page=${totalPage }">[]마지막]</a>
					</c:if>
				</td>
			</tr>
		</c:otherwise>

	</c:choose>
	<tr>
		<td colspan="4" align="center">
			<a href="WriteForm?page=${page }">글쓰기</a>
		</td>
	</tr>
</table>