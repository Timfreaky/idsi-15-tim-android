<%@ page pageEncoding="UTF-8" %>
	<!DOCTYPE html>
	<html>
	    <head>
	        <meta charset="utf-8" />
	        <title>Localisation</title>
	    </head>
	    <body>
	        <div>
	            <form method="get" action="carte">
	                <fieldset>
	                    <legend>Informations pour la localisation</legend>
	                    
	                    <label for="rue">Rue <span class="requis">*</span></label>
	                    <input type="text" id="rue" name="rue" value="" size="20" maxlength="20" />
	                    <br />
	    
	                    <label for="numero">Numéro </label>
	                    <input type="text" id="numero" name="numero" value="" size="20" maxlength="20" />
	                    <br />
	                </fieldset>
	                
	                <input type="submit" value="Valider"  />
	            
	            </form>
	        </div>
	    </body>
	</html>