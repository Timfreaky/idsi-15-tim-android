<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>SPORTIT à Genève</title>
        <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
        <script src="../KmlMapParser.js" type="text/javascript"></script>
       	<style>
			.map {
				width: 650px;
				height: 600px;
				margin: 20px;
			}
			.sidebar-ct {
				width: 300px;
			}
		</style>

        <script type="text/javascript">
        
            var xml;
            var map;
            
            function initialize() {
            	var myLatlng = new google.maps.LatLng(46.2, 6.1667);
            	
            	var myOptions = { 
            		zoom: 14,
            		center: myLatlng,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
					mapTypeControl: true,
					navigationControl: true,
					draggingCursor: 'move'
		            };
           
            <%--Initialisation de la map--%>
            map = new google.maps.Map(document.getElementById('sidebarMap'), myOptions);
            <%-- Initialisation du Parser kml--%>
            xml = new KmlMapParser({ map: map,
                kml: '../doc.kml',
                showSidebar: true,
				showFolders: true,
				showRootName: false,
				showSidebarDescriptions: true,
				showSidebarBubble: false,
				showMultiPointsAsMarkers: false,
				allFoldersOpen: true,
				showImageShadow: false
                });
            }
               
            </script>
    </head>
        
    <body onload="initialize()">
    	<c:out value="${nomInfra}"/>
    	<div class="map-ct"  ></div>
       	<div id="sidebarMap" class="map"></div>
       	<div class="sidebar-ct" ></div>
       	<div id="the_side_bar" class="sidebar" ></div>
    </body>
</html>