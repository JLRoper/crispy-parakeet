$(document).ready(function () {

    var setit = null;
    var setTileLocation = function (tile, posX, posY) {
        var startPosX = tile.parent().offset().left;
        var startPosY = tile.parent().offset().top;
        tile.css("left", posX - startPosX);
        tile.css("top", posY - startPosY);
    };
    var setTileToMarker = function (tile, viewportMarker) {
        setTileLocation(tile,
                viewportMarker.offset().left,
                viewportMarker.offset().top);
    };
    var getIntervalFunction = function (tile) {
        return function () {
            var viewMarker = $('.viewport-tile-marker');
            setTileToMarker(tile, viewMarker);
        };
    };
//    var expandBox = function () {
//        $(this).toggleClass('classWithShadow');
//        $(this).parent().toggleClass('parentBoxClassWithShadow');
//        if (!setit) {
//            setit = setInterval(getIntervalFunction($(this)), 100);
//        } else {
//            clearInterval(getIntervalFunction($(this)));
//            setit = null;
//            $(this).css("left", 0);
//            $(this).css("top", 0);
//        }
//    };

    /* This is just a toggle switch because I don't know of 
     * a better way atm... */
    var expandBox = function () {
        var tile = $(this);
        if (tile.hasClass('classWithShadow')) {
            closeTile(tile);
        } else {
            openTile(tile);
        }
    };

    var openTile = function (tile) {
        tile.toggleClass('classWithShadow');
        tile.parent().toggleClass('parentBoxClassWithShadow');
        setit = setInterval(getIntervalFunction(tile), 100);
    };

    var closeTile = function (tile) {
        clearInterval(setit);
        tile.parent().removeClass('parentBoxClassWithShadow');
        tile.removeClass('classWithShadow');
        tile.css("left", 0);
        tile.css("top", 0);
    };



    var intervalFunc = function () {
        var viewMarker = $('.viewport-tile-marker');
        setTileToMarker($('.classWithShadow'), viewMarker);
    };


    $(".flex-tile").click(expandBox);
    $("#tickerBox").unbind("click", expandBox);
    $("#minimizeButton").click(function () {
        $("#tickerBox").toggleClass('classWithShadow');
    });
//        $("#flex-item-interior").bind("mouseleave", function () {
//            $(".flex-tile").bind("click", expandBox);
//        });
//        $("#flex-item-interior").bind("mouseover", function () {
//            $(".flex-tile").unbind("click", expandBox);
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
                if (quoteInfo) {
                    for (var i = 0; i < quoteInfo.length; i++) {
                        if (quoteInfo[i]) {
//                                symbol = (quotInfo[i]);
//                                
//                                printStr += "<div id=\"" + symbol  "\" style=\"background-color:"
//                                        + true ? "green" : "red"
//                                        + ";\">";
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
    $(".flex-tile").mousedown(function ()
    {
        $(this).addClass('observer-button-pressed');
    });
    $(".owl-carousel").owlCarousel(
            {
                items: 1,
                autoWidth: true
            });
    $(".flex-tile").mouseup(function ()
    {
        $(this).removeClass('observer-button-pressed');
    });
    $("#symbol").keyup(function () {
        var symbols = $("#symbol").val();
        if (symbols.substring(symbols.length - 1, symbols.length) == ',') {
            return;
        }
        pollStockInfo();
    });
});