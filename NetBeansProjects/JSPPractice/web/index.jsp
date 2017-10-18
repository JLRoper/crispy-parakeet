<%-- 
    Document   : index
    Created on : Oct 1, 2017, 11:29:49 PM
    Author     : Jacob
--%>

<%@page import="htmlunit.QuoteHandler"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="js/jquery-3.2.1.min.js"></script>
<script src="js/jquery-1.11.3.js"></script>

<!--<link rel="stylesheet"  type="text/css" href="style/mystyle.css">-->
<link rel="stylesheet" type="text/css" href="style/index.css">
<link rel="stylesheet" href="owlcarousel/assets/owl.carousel.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="owlcarousel/owl.carousel.min.js"></script>
<link rel="stylesheet" href="owlcarousel/assets/owl.theme.css">

<script>
    var expandBox = function () {
        $(this).toggleClass('classWithShadow');
    };

    $(document).ready(function () {
        $(".flex-item").click(expandBox);
        $("#tickerBox").unbind("click", expandBox);
        $("#minimizeButton").click(function () {
            $("#tickerBox").toggleClass('classWithShadow');
        });

//        $("#flex-item-interior").bind("mouseleave", function () {
//            $(".flex-item").bind("click", expandBox);
//        });
//        $("#flex-item-interior").bind("mouseover", function () {
//            $(".flex-item").unbind("click", expandBox);
//        });

        var interval = function () {
            setInterval(function () {
                pollStockInfo();
            }, 15000);
        };
        interval();



        var test = {
            sup1: {test1: "DATA1", test3: "Data3"},
            sup2: {test1: "DATA1", test3: "Data3"},
            sup3: {test1: "DATA1", test3: "Data3"}
        };



        var pollStockInfo = function () {
            $.ajax({
                url: "PracticeServlet",
                action: "GetSingleQuote",
                type: "POST",
                dataType: "JSON",
                data: {
                    symbol: $("#symbol").val(),
                    lastName: JSON.stringify(test)
                },
                success: function (data) {
                    var printStr = "";
                    var quoteInfo = data.quoteInfo;
                    var count = data.count;
                    if (quoteInfo) {
                        for (var i = 0; i < count; i++) {
                            if (quoteInfo[i]) {
                                printStr += "<div>";
                                printStr += quoteInfo[i];
                                printStr += "</div>";
                            }
                        }
                        $("#stockInfoResults").html(printStr);
                    }
                },
                error: function (data, status, er) {
//                alert("error: " + data + " status: " + status + " er:" + er);
                }
            });
            return false;
        };


        var openFullItem = function () {
            $(this).parent().parent().parent().parent().parent().toggleClass('observer-item-open');
        };
        $(".observer-carousel-unit").click(openFullItem());

        $(".flex-item").mousedown(function ()
        {
            $(this).addClass('observer-button-pressed');
        });

        $(".owl-carousel").owlCarousel(
                {
                    items: 1,
                    autoWidth: true
                });

        $(".flex-item").mouseup(function ()
        {
            $(this).removeClass('observer-button-pressed');
        });
//tst

        $("#symbol").keyup(function () {
            var symbols = $("#symbol").val();
            if (symbols.substring(symbols.length - 1, symbols.length) == ',') {
                return;
            }
            pollStockInfo();
        });



    });

</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Observer</h1>
        <div id="displayContainer">
            <div class="flex-container">

                <div class="flex-item">Item 2 
                </div>
                <div class="flex-item">Item 3</div> 
                <div  style='overflow: hidden; 'class="flex-item">James' Dick Box </br>
                    <a style='color: white; font-font-variant: all-petite-caps; display: inline-block;'  target='_blank' href='https://www.facebook.com/Millerfly2010'>Touch Me</a>
                    <iframe  src="https://www.youtube.com/embed/IrdYueB9pY4">
                        <p>Your browser does not support iframes.</p>
                    </iframe>
                </div>
                <div class="flex-item">Item 4     
                    <div class="owl-carousel" style="width: 100%; height: 50px;" >
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #3333ff"><h4>Hello I am a Block</h4></div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #ff3399"><h4>Hello I am a Block</h4></div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #ffff33"><h4>Jacob Roper</h4>
                        </div>
                        <div class="observer-carousel-unit observer-carousel-item" style=" color: #ffff33; background: #cc0099"><h4>Charmaine Jenkins</h4>
                        </div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: teal"><h4>Hello I am a Block</h4></div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: plum"><h4>Hello I am a Block</h4></div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #00cc33"><h4>Hello I am a Block</h4></div>
                        <div class="observer-carousel-unit observer-carousel-item" style="color:crimson; background: #00cccc"><h4>Hello I am a Block</h4></div>
                    </div></div> 
                <div class="flex-item">
                    <form action="PracticeServlet">
                        First name: <input type="text" name="param1" value="Mickey"><br>
                        Last name: <input type="text" name="LastName" value="Mouse"><br>
                        <input type="submit" value="Submit">
                    </form>
                </div> 
                <div class="flex-item">Item 3</div> 
                <div class="flex-item" id="AJAXTestDiv">Code Flip test box
                    <form action="PracticeServlet">
                        Css Input: <input type="text"name="smbol" value="css stuff.."><br>
                        <input type="submit" value="Submit">
                    </form>
                </div> 
                <div class="flex-item" id="tickerBox">
                    <div id="minimizeButton" style="width: 100%; height:30px;text-align: center; background-color: white">Title of Box</div>
                    <div class="flex-item-interior"style="z-index: 11;">
                        Symbol: <input type="text"id="symbol" value="AMD"><br>
                        <!--<input id="submitSymbolButton"  onclick="pollStockInfo()" type="submit" value="Submit">-->
                        <div id="stockInfoResults">
                        </div>
                    </div>
                </div> 
                <div class="flex-item"><iframe height="355" width="430" src="https://sslcharts.forexprostools.com/index.php?force_lang=1&pair_ID=1&timescale=300&candles=50&style=candles"></iframe><br /><div style="width:500px"><a target="_blank" href="https://www.investing.com"><img src="https://wmt-invdn-com.akamaized.net/forexpros_en_logo.png" alt="Investing.com" title="Investing.com" style="float:left" border="0"/></a><span style="float:right"><span style="font-size: 11px;color: #333333;text-decoration: none;">Forex Charts powered by <a href="https://www.investing.com/" rel="nofollow" target="_blank" style="font-size: 11px;color: #06529D; font-weight: bold;" class="underline_link">Investing.com</a></span></span></div></div> 
                <div class="flex-item">Item 3
                    <iframe height="480" width="640" src="https://ssltvc.forexprostools.com/?pair_ID=8862&height=480&width=640&interval=300&plotStyle=candles&domain_ID=1&lang_ID=1&timezone_ID=7"></iframe></div> 
                <div class="flex-item">Item 3</div> 
                <div class="flex-item">Item 3</div> 
            </div>

        </div>
    </body>
</html>
