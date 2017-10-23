<%-- 
    Document   : index2
    Created on : Oct 19, 2017, 6:36:24 PM
    Author     : James
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<link rel="stylesheet" type="text/css" href="style/index2.css">


<html>
    <head>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <title>Observation Deck</title>
    </head>
    <body>
        <font size="20">Observe</font>
        
        <div style="transform: translateY(150%)">
            
            <font size="5">Date:  <%= new java.util.Date() %></font>
            
        </div>
            <div style="width: 500px; height: 500px; transform: translateX(250%); background-color: red">
                <a href="http://kanishkkunal.in" 
  target="popup" 
  onclick="window.open('http://kanishkkunal.in','popup','width=600,height=600'); return false;">
    Open Link in Popup
</a>
            </div>
    </body>
</html>

<script src="js/index2.menu.js"/>



<script>
    $(document).ready(function() {
  $.simpleWeather({
    location: 'Austin, TX',
    woeid: '',
    unit: 'f',
    success: function(weather) {
      html = '<h2><i class="icon-'+weather.code+'"></i> '+weather.temp+'&deg;'+weather.units.temp+'</h2>';
      html += '<ul><li>'+weather.city+', '+weather.region+'</li>';
      html += '<li class="currently">'+weather.currently+'</li>';
      html += '<li>'+weather.wind.direction+' '+weather.wind.speed+' '+weather.units.speed+'</li></ul>';
  
      $("#weather").html(html);
    },
    error: function(error) {
      $("#weather").html('<p>'+error+'</p>');
    }
  });
});
    
    
    
</script>