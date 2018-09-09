$(document).ready(function () {

    TileHandler = {
        MISC_DATA: {
            autoIDCount_home: 0,
            autoIDCount_view: 0,
            autoIDCount_tile: 0,
            tileIDText: 'TileHandler-tileID',
            homeIDText: 'TileHandler-homeSpotID',
            viewIDText: 'TileHandler-viewSpotID'
        },
        TILE_DATA: {
            /* Contains data needed for tile manipulation. 
             * <tile ID>: {
             *      name: <reference name>,
             *      description: <descriptor?>,
             *      homeID: <home spot ID>,
             *      viewID: <view spot ID>
             *      viewPortIntFunc: <view spot ID>
             *      currentSize: {
             *          width:
             *          height: 
             *      }
             */
        },
        VIEWPORT_HANDLING: {
            VIEWPORT_DATA: {
                /*
                 * viewportID:{
                 *      inUse: <true when item is attached.> 
                 *      }
                 *
                 */
            },
            setViewPortInUse: function (vpID, use) {
                this.VIEWPORT_DATA[vpID].inUse = use;
            },
            setViewportPosition: function (id, left, top) {
                if (!id.includes("view")) {
                    console.log("setViewportPosition BAD ID: " + id);
                }
                var viewport = $('#' + id);
                if (viewport) {
                    viewport.css('left', left);
                    if (top && typeof top != 'undefined' && top != null) {
                        viewport.css('top', top);
                    }
                }
            }
        },
        setTileSize: function (height, width, tileID) {
            $('#' + tileID).css('width', width);
            $('#' + tileID).css('height', height);
        },
        generateID: function (type) {
            return this.MISC_DATA[type + 'IDText'] + this.MISC_DATA['autoIDCount_' + type]++;
        },
        hasViewPort: function (tID) {
            var viewID = this.getTileData(tID).viewID;
            if (typeof viewID === 'undefined'
                    || viewID === "null"
                    || viewID === null) {
                return false;
            }
            if (viewID.includes(TileHandler.MISC_DATA.viewIDText)) {
                return true;
            }
            return false;
        },
        getTileData: function (tID) {
            return this.TILE_DATA[tID];
        },
        setViewPortID: function (tID, vID) {
            return  this.TILE_DATA[tID].viewID = vID;
        },
        removeViewPortID: function (tID) {
            if (!TileHandler.hasViewPort(tID)) {
                return;
            }
            var vID = this.getViewPortID(tID);
            TileHandler.TILE_DATA[tID].viewID = '';
            TileHandler.VIEWPORT_HANDLING.setViewPortInUse(vID, false);
        },
        getViewPortID: function (tID) {
            return this.TILE_DATA[tID].viewID;
        },
        nextAvailableViewPort: function () {
            var nextViewPort;
            var vpData = this.VIEWPORT_HANDLING.VIEWPORT_DATA;
            var varIDs = Object.keys(vpData);
            varIDs.every(function (varID) {
                if (typeof vpData[varID].inUse === 'undefined'
                        || vpData[varID].inUse === false) {
                    nextViewPort = varID;
                    return false;
                }
                return "nope";
            });
            return nextViewPort; // If non available. 
        },
        assignNextViewPort: function (tID) {
            var nextViewPort = TileHandler.nextAvailableViewPort();
//            $(nextViewPort).attr('id', nextViewPort);
            this.setViewPortID(tID, nextViewPort);
        },
        getHomeID: function (tID) {
            return this.TILE_DATA[tID].homeID;
        },
        registerTile: function (tID, hID) {
            if (tID) {
                this.TILE_DATA[tID] = {
                    homeID: hID,
                    currentSize: {
                        width: 150,
                        height: 200
                    }
                };
            }
        },
        initialize: function () {
            var tile;
            var tileParent;
            var tileID;
            var homeID;
            $('.flex-tile').each(function () {
                tile = $(this);
                tileParent = tile.parent();
                tileID = tile.attr('id') ? tile.attr('id') : TileHandler.generateID('tile');
                homeID = tileParent.attr('id') ? tileParent.attr('id') : TileHandler.generateID('home');
                tile.attr('id', tileID);
                tileParent.attr('id', homeID);
                TileHandler.registerTile(tileID, homeID);
                TileHandler.hasViewPort(tileID);

            });
            var port;
            var portID;
            var vpData = TileHandler.VIEWPORT_HANDLING.VIEWPORT_DATA;
            var lastPosition = 75;
            $('.viewport-tile-marker').each(function () {
                port = $(this);
                portID = TileHandler.generateID('view');
                vpData[portID] = {
                    portID: portID,
                    inUse: false
                };
                port.attr('id', portID);
                TileHandler.VIEWPORT_HANDLING.setViewportPosition(portID, 15, lastPosition);
                lastPosition += 210;
            });
        }
    };


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
        TileHandler.assignNextViewPort(tile.attr('id'));
        var viewMarker = $('#' + TileHandler.getViewPortID(tile.attr('id')));
        return function () {
            setTileToMarker(tile, viewMarker);
        };
    };

    /* This is just a toggle switch because I don't know of 
     * a better way atm... */
    var expandBox = function (tileIn) {
        var tile = $(this);
        if (tileIn) {
            tile = tileIn;
        }
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
        var tileData = TileHandler.TILE_DATA[tile.attr('id')]
        TileHandler.VIEWPORT_HANDLING.setViewPortInUse(tileData.viewID, true);
        tileData.viewPortIntFunc = setit;
        TileHandler.setTileSize(tileData.currentSize.height,
                tileData.currentSize.width, tile.attr('id'));
    };

    var closeTile = function (tile) {
        var tileData = TileHandler.TILE_DATA[tile.attr('id')];
        clearInterval(tileData.viewPortIntFunc);
        tile.parent().removeClass('parentBoxClassWithShadow');
        tile.removeClass('classWithShadow');
        tile.css("left", 0);
        tile.css("top", 0);
        TileHandler.removeViewPortID(tile.attr('id'));
        TileHandler.setTileSize(113, 113, tile.attr('id'));

    };

    var unbindMe;
    $(".flex-tile").click(unbindMe = function () {
        expandBox($(this))
    });
    $(".tickerBox").unbind("click", unbindMe);
    $(".minimizeButton").click(function () {
        expandBox($(this).parent());
    });

    var interval = function () {
        setInterval(function () {
            pollStockInfo();
        }, 10000);
    };
    interval();
    var test = {
        sup1: {test1: "DATA1", test3: "Data3"},
        sup2: {test1: "DATA1", test3: "Data3"},
        sup3: {test1: "DATA1", test3: "Data3"}
    };


    var pollStockInfoSuccess = function (data, doMe) {
        var printStr = "";
        var symbol;
        var quoteInfo = data.quoteInfo;
        if (quoteInfo) {
            for (var i = 0; i < quoteInfo.length; i++) {
                if (quoteInfo[i]) {
                    symbol = quoteInfo[i].split("- ");
                    printStr += "<div id=\"" + symbol[0].trim() + "\">";
                    printStr += symbol[0] + symbol[1] + symbol[2];
                    printStr += "</div>";
                    printStr += "<div>";
                    printStr += "Sent to server: " + $(doMe).val();
                    printStr += "</div>";
                }
            }
            $(doMe).siblings(".stockInfoResults").html(printStr);
        }
    };

    var pollStockInfo = function (doMe) {
        if (!doMe) {
            $(".symbol").each(function () {
                console.log(this);
                pollStockInfo(this);

            });
        }
        var payload = {
            symbol: $(doMe).val()
        };
        defaultAJAX("GET_SINGLE_QUOTE", payload, pollStockInfoSuccess, doMe);
//        $.ajax({
//            url: "PracticeServlet",
//            type: "POST",
//            dataType: "JSON",
//            data: {
//                action: "ANOTHER_ACTION",
//                symbol: $(doMe).val(),
//                lastName: JSON.stringify(test)
//            },
//            success: function (data) {
//                var printStr = "";
//                var symbol;
//                var quoteInfo = data.quoteInfo;
//                if (quoteInfo) {
//                    for (var i = 0; i < quoteInfo.length; i++) {
//                        if (quoteInfo[i]) {
//                            symbol = quoteInfo[i].split("- ");
//                            printStr += "<div id=\"" + symbol[0].trim() + "\">";
//                            printStr += symbol[0] + symbol[1] + symbol[2];
//                            printStr += "</div>";
//                            printStr += "<div>";
//                            printStr += "Sent to server: " + $(doMe).val();
//                            printStr += "</div>";
//                        }
//                    }
//                    $(doMe).siblings(".stockInfoResults").html(printStr);
//                }
//            },
//            error: function (data, status, er) {
////                alert("error: " + data + " status: " + status + " er:" + er);
//            }
//        });
        return false;
    };




    var defaultAJAX = function (action, payloadIn, successFunc, callingID) {
        $.ajax({
            url: "PracticeServlet",
            type: "POST",
            dataType: "JSON",
            data: {
                action: action,
                payload: JSON.stringify(payloadIn)
            },
            success: function (data) {
                successFunc(data, callingID);
            },
            error: function (data, status, er) {
//                alert("error: " + data + " status: " + status + " er:" + er);
            }
        });
    };



    var openFullItem = function () {
        $(this).parent().parent().parent().parent().parent().toggleClass('observer-item-open');
    };
    $(".observer-carousel-unit").click(openFullItem());
    $(".flex-tile").mousedown(function ()
    {
        $(this).addClass('observer-button-pressed');
    });
//    $(".owl-carousel").owlCarousel(
//            {
//                items: 1,
//                autoWidth: true
//            });
    $(".flex-tile").mouseup(function ()
    {
        $(this).removeClass('observer-button-pressed');
    });
    $(".symbol").keyup(function () {
        var symbols = $(this).val();
        if (symbols.substring(symbols.length - 1, symbols.length) == ',') {
            return;
        }
        pollStockInfo(this);
    });


    TileHandler.initialize();



//    
//        var imported2 = document.createElement('script');
//imported2.src = 'http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js';
//document.head.appendChild(imported2);
//    var imported = document.createElement('script');
//imported.src = 'http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js';
//document.head.appendChild(imported);
});