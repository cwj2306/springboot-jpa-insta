<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Explore | Nanogram</title>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="shortcut icon" href="/images/favicon.ico">
<link rel="stylesheet" href="/css/styles.css">
</head>
<body>

	<%@include file="../include/nav.jsp"%>

	<main id="explore">
		<ul class="explore__users u-default-box">

			<c:forEach var="follow" items="${follows}" varStatus="status">

				<li class="explore__user">
					<div class="explore__content">
<!-- 					<img src="/images/avatar.jpg" /> -->
						<img src="/upload/${follow.toUser.profileImage}" />
						<div class="explore__info">
							<span class="explore__username">${follow.toUser.name}</span>
						</div>
					</div>
					
					<div id="follow_item_${status.count}">
						<c:if test="${principal.user.id ne follow.toUser.id}">
							<c:choose>
								<c:when test="${follow.matpal eq true}">
									<button class="following_btn" onclick="follow(false,${follow.toUser.id},${status.count})">팔로잉</button>
								</c:when>
								<c:otherwise>
									<button class="follow_btn" onclick="follow(true,${follow.toUser.id},${status.count})">팔로우</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					
				</li>

			</c:forEach>

		</ul>
	</main>

	<%@ include file="../include/footer.jsp"%>
	
	<script type="text/javascript" src="/js/follow.js"></script>

</body>
</html>
