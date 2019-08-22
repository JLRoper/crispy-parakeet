<%-- 
    Document   : graphTesting
    Created on : Jan 20, 2019, 11:32:54 PM
    Author     : Jacob
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="js/freewall.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="free-wall">
            <div class="brick size320"></div>
            <div class="brick size320"></div>
            <div class="brick size320"></div>
            <div class="brick size320"></div>
            <div class="brick size320"></div>
            <div class="brick size320"></div>
        </div>
    </body>
</html>
<style> 
    .size320 {
        width: 320px;
        height: 320px;
    }
</style>
<script>
    var colour = [
        "rgb(142, 68, 173)",
        "rgb(243, 156, 18)",
        "rgb(211, 84, 0)",
        "rgb(0, 106, 63)",
        "rgb(41, 128, 185)",
        "rgb(192, 57, 43)",
        "rgb(135, 0, 0)",
        "rgb(39, 174, 96)"
    ];

    $(".free-wall .size320").each(function () {
        var backgroundColor = colour[colour.length * Math.random() << 0];
        var bricks = $(this).find(".brick");
        !bricks.length && (bricks = $(this));
        bricks.css({
            backgroundColor: backgroundColor
        });
    });

    $(function () {
        $(".free-wall").each(function () {
            var wall = new Freewall(this);
            wall.reset({
                draggable: false,
                delay: 0,
                fixSize:null,
                selector: '.size320',
                cellW: 320,
                cellH: 320,
//                fixSize: 0,
                gutterY: 10,
                gutterX: 10,
                onResize: function () {
                    wall.fitWidth();
                }
            })
            wall.fitWidth();
        });
        $(window).trigger("resize");
    });
</script>