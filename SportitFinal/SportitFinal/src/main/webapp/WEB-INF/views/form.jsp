<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Adresse</title>
        <link type="text/css" rel="stylesheet" href="form.css" />
    </head>
    <body>
        <form method="post" action="adresse">
	    <fieldset>
                <legend>Adresse à Genève</legend>
                <p>Veuillez entrer votre adresse pour afficher les chemins pédestres qui vous entourent.</p>

                <label for="adresse">Votre adresse (Exemple: Rue de la Servette 12)<span class="requis">*</span></label>
                <input type="text" id="adresse" name="adresse" value="" size="20" maxlength="60" />
                <br />

                <input type="submit" value="Afficher la carte" class="sansLabel" />
                <br />
            </fieldset>
        </form>
    </body>
</html>