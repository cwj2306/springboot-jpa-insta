<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Login | Nanogram</title>
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="shortcut icon" href="/images/favicon.ico">
<link rel="stylesheet" href="/css/styles.css">
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
</head>
<body>

	<main id="login">
		<div class="login__column">
			<img src="/images/phoneImage.png" />
		</div>
		<div class="login__column">
			<div class="login__box">
				<img src="/images/loginLogo.png" />
				<form action="/auth/loginProc" method="post" class="login__form">
					<input type="text" name="username" placeholder="Username" required><br/>
					<input type="password" name="password" placeholder="Password" required><br/>
					<input type="submit" value="Log in">
				</form>
				<span class="login__divider"> or </span> 
				
					<!-- 카카오 로그인 버튼 -->
					<a href="javascript:newLoginForm()" class="login__fb-link">
						<img src="/images/kakao_account_login_btn_medium_narrow.png" width="100%"/>
					</a>
					
					<a href="#" class="login__small-link">Forgot password?</a>
			</div>
			<div class="login__box">
				<span class="login__text"> Don't have an account? <br />
				</span> <a class="login__blue-link" href="/auth/join">Sign up</a>
			</div>
			<div class="login__t-box">
				<span class="login__text"> Get the app. </span>
				<div class="login__appstores">
					<img src="/images/ios.png" class="login__appstore" />
					<img src="/images/android.png" class="login__appstore" />
				</div>
			</div>
		</div>
	</main>

	<%@include file="../include/footer.jsp" %>

	<script type='text/javascript'>

		function newLoginForm(){
			Kakao.init('3380be8864c1fd2920e469746701bdc8');
			
			Kakao.Auth.loginForm({
				success: function(authObj){
					
					alert(JSON.stringify(authObj));
					
					Kakao.API.request({
							url: '/v2/user/me',
							success: function(res){
								alert(JSON.stringify(res));
								window.location.href='/auth/kakao/login';
							},
							fail: function(error){
								alert(JSON.stringify(error));
							}
					});
					
				},
				fail : function(err){
					alert(JSON.stringify(err));
				}
				
			});
		}
		
	</script>

</body>
</html>
