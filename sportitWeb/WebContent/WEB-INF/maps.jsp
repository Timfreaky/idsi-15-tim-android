<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <title>SPORTIT</title>
    <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script>
    var geocoder;
    var map;
    function initialize() {
      geocoder = new google.maps.Geocoder();
      var mapOptions = {
        zoom: 8
      }
      map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
    }

    function codeAddress() {
      var address = document.getElementById("address").value;
      geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
          map.setCenter(results[0].geometry.location);
          var marker = new google.maps.Marker({
              map: map,
              position: results[0].geometry.location
          });
        } else {
          alert("Geocode was not successful for the following reason: " + status);
        }
      });
    }

  </script>
  <body onload="initialize()">
   <div id="map-canvas" style="width: 320px; height: 480px;"></div>
    <div>
      <input id="address" type="textbox" value="Genève, Battelle">
      <input type="button" value="Encode" onclick="codeAddress()">
    </div>
  </body>
</html>