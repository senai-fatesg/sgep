<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Título aqui</title>
	
	    <link href="css/bootstrap.css" rel="stylesheet" />
	    <link href="css/bootstrap-responsive.css" rel="stylesheet" />
	    <link href="css/styles.css" rel="stylesheet" />
	    
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script>
			function MascaraCPF(j_username){
		        if(mascaraInteiro(j_username)==false){
		                event.returnValue = false;
		        }       
		        return formataCampo(j_username, '000.000.000-00', event);
			}
			
			function focar() {
				document.getElementById("j_username").focus();
			}
		</script>
		<style>
			input.span3, textarea.span3, .uneditable-input.span3 {
			    width: 210px;
			}
			.btn-tamanho {
				width: 90%;
				white-space: normal;
				margin-top: 20px;
	    	}
	    	body{
	    		background-color: #70BFBF;
	    	}
		</style>
	</head>
	<body>
		<form action="j_spring_security_check" method="post">
			<div class="wrapper">
			    <div class="span4 sidebar" style="text-align: center">
					<div class="well quickSignupForm">
		                <img src="imagens/sgep.png" alt="logo" style="width: 160px"/>
		                <form class="form-signin" action="j_spring_security_check" method="post" id="form1">
		                <%
							if (request.getParameter("msg") != null) {
							    out.print("<div class=\"col-md-12\"><span style='color: red;font-weight: bold;'>Usuário ou senha incorretos</span></div>");
							}
						%>
		                <div class="col-md-12" style="margin-bottom: 5px; margin-top: 10px; text-align: left">
					 		<label for="j_username">CPF:</label> 
							<input type="text" onkeypress="MascaraCPF(form1.j_username);" maxlength="14" id=j_username name="j_username" class="form-control" placeholder="Informe o CPF" required="true" autofocus="true" />
						</div>
		                </br>
		                <div class="col-md-12" style="margin-bottom: 5px; text-align: left"">	  	
							<label for="j_password">SENHA:</label>
							<input name="j_password" class="form-control" type="password" placeholder="Informe sua senha."   />
						</div>
						<div>
		                	<button class="btn btn-xg btn-primary btn-tamanho" type="submit">Login</button>
						</div>
		                </br></br>
		                </form>
                	</div>
	             </div>
			  </div>
		</form>
	</body>
</html>