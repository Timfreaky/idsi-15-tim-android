<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adresse</title>
</head>
<body>
<h1>Bienvenu sur Sportit!</h1>
<p>Veuillez entrer votre adresse pour connaître les infrastructures et chemins pédestres qui vous entourent</p>
	<form action="GetAdresse.java" method=get>
		<input type="text" name="adresse" id="adresse"></input>
		<input type="submit" value="Voir la carte">
	</form>
</body>
</html>