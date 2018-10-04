<%-- 
    Document   : index
    Created on : Oct 1, 2017, 11:29:49 PM
    Author     : Jacob and James
--%>

<%@page import="htmlunit.QuoteHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--<script src="js/jquery-1.11.3.js"></script>-->
<!--<script src="owlcarousel/owl.carousel.min.js"></script>-->
<script src="js/jquery-3.2.1.min.js"></script>

<!--<link rel="stylesheet,jnh"  type="text/css" href="style/mystyle.css">-->
<link rel="stylesheet" type="text/css" href="style/index.css">
<!--<link rel="stylesheet" href="owlcarousel/assets/owl.carousel.css">-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--<link rel="stylesheet" href="owlcarousel/assets/owl.theme.css">-->
<script>var TileHandler;</script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
<script src="js/tileView.js"></script>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Observer</h1>
        <div id="viewport-container">
                        <div class="viewport-tile-marker"></div>
                        <div class="viewport-tile-marker"></div>
                        <div class="viewport-tile-marker"></div>
                        <div class="viewport-tile-marker"></div>
                        <div class="viewport-tile-marker"></div>
                        <div class="viewport-tile-marker"></div>
            <!--            <div class="viewport-tile-marker"style="width: 900px;"></div>
                        <div class="viewport-tile-marker" style="left: 180px;"></div>
                        <div class="viewport-tile-marker" style="left: 340px;"></div>
                        <div class="viewport-tile-marker" style="left: 500xp;"></div>-->
        </div>
        <div id="displayContainer">
            <div class="flex-container">

                <div class="flex-tile-location">
                    <div class="flex-tile">Item 2 
                    </div>
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div  style='overflow: hidden; 'class="flex-tile">Watch Me!</br>
                        <a style='color: white; font-font-variant: all-petite-caps; display: inline-block;'  target='_blank' href='https://www.facebook.com/Millerfly2010'>Touch Me</a>
                        <iframe  src="https://www.youtube.com/embed/IrdYueB9pY4">
                            <p>Your browser does not support iframes.</p>
                        </iframe>
                    </div>
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 4     
                        <!--                        <div class="owl-carousel" style="width: 100%; height: 50px;" >
                                                    <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #3333ff"><h4>Hello I am a Block</h4></div>
                                                    <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #ff3399"><h4>Hello I am a Block</h4></div>
                                                    <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #00cc33"><h4>Hello I am a Block</h4></div>
                                                    <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #00cccc"><h4>Hello I am a Block</h4></div>
                                                </div>-->
                    </div> 
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile">
                        <form action="PracaticeServlet">
                            First name: <input type="text" name="param1" value="Mickey"><br>
                            Last name: <input type="text" name="LastName" value="Mouse"><br>
                            <input type="submit" value="Submit">
                        </form>
                    </div> 
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile" id="AJAXTestDiv">Code Flip test box
                        <form action="PracticeServlet">
                            Css Input: <input type="text"name="smbol" value="css stuff.."><br>
                            <input type="submit" value="Submit">
                        </form>
                    </div> 
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile observer-expand" id="testTileRED" style="background-color: red"></div>
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile tickerBox" id="tickerBox">
                        <div class="minimizeButton" style="width: 100%; height:30px;text-align: center; background-color: rgba(255, 255, 255, 0)">Quote Ticker</div>
                        <div class="flex-item-interior"style="z-index: 11;">
                            Symbol: <input type="text"class="symbol" value="AMD"><br>
                            <div class="stockInfoResults" style="margin: 3px;">
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile tickerBox" id="tickerBox1">
                        <div class="minimizeButton" style="width: 100%; height:30px;text-align: center; background-color: rgba(255, 255, 255, 0)">Quote Ticker</div>
                        <div class="flex-item-interior"style="z-index: 11;">
                            Symbol: <input type="text"class="symbol" value="AMD"><br>
                            <div class="stockInfoResults" style="margin: 3px;">
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile tickerBox" id="tickerBox2">
                        <div class="minimizeButton" style="width: 100%; height:30px;text-align: center; background-color: rgba(255, 255, 255, 0)">Quote Ticker</div>
                        <div class="flex-item-interior"style="z-index: 11;">
                            Symbol: <input type="text"class="symbol" value="AMD"><br>
                            <div class="stockInfoResults" style="margin: 3px;">
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">
                        <iframe 
                            height="355" 
                            width="430" 
                            src="https://sslcharts.forexprostools.com/index.php?force_lang=1&pair_ID=1&timescale=300&candles=50&style=candles"></iframe>
                        <br />
                        <div style="width:500px">
                            <a target="_blank" href="https://www.investing.com">
                                <img src="https://wmt-invdn-com.akamaized.net/forexpros_en_logo.png" alt="Investing.com" title="Investing.com" style="float:left" border="0"/>
                            </a>
                            <span style="float:right">
                                <span style="font-size: 11px;color: #333333;text-decoration: none;">
                                    Forex Charts powered by <a href="https://www.investing.com/" rel="nofollow" target="_blank" style="font-size: 11px;color: #06529D; font-weight: bold;" class="underline_link">Investing.com</a>
                                </span>
                            </span>
                        </div>
                    </div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3
                        <iframe height="100%" width="100%" src="https://ssltvc.forexprostools.com/?pair_ID=8862&height=480&width=640&interval=60&plotStyle=candles&domain_ID=1&lang_ID=1&timezone_ID=7"></iframe>
                    </div> 
                </div> 
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
                <div class="flex-tile-location">
                    <div class="flex-tile">Item 3</div> 
                </div>
            </div>
            <script>
    var tileContainer = $(".flex-container");
    var contents = tileContainer.html();
    for (var i = 0; i < 20; i++) {
        contents += "<div class =\"flex-tile-location\"><div class=\"flex-tile\">AutoGenerated</div></div>"
    }
    tileContainer.html(contents)
            </script>
        </div>
    </body>
</html>
