<%-- 
    Document   : CompanyVehiclesSale
    Created on : Oct 14, 2016, 12:16:20 PM
    Author     : Roper
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java"%>
<!--<script>
    //Show tooltip on mouseover
    //    $('body').on('click', '#button_1', function() {
    //        $('.instructions').fadeIn('slow');
    //    });

    //Close tooltip 
    $('body').on('click', 'div.instructions .close', function() {
        $('.instructions').hide();
    });  
</script>-->
<html>
    <span class="text" >

        <body>
            <div style='text-align: center; font-size: 18px; font-weight: bold; padding: 2%'>
                Southeastern Freight Lines Is Happy to Announce a New Benefit for All Employees
            </div>
            <div style='text-align: center;  font-size: 12px; padding: 2%; text-indent: 5% '>
                How would you like to purchase one of our used company vehicles online at a very reasonable price?
                If you're in the market for a used vehicle, you can now include our used fleet vehicles in your mix of options. 
                Element Fleet Management, our fleet management provider, has developed a special website where you
                can shop for the best of our company vehicles that have been turned in for sale. The vehicles are 
                priced substantially below what you would pay for a similar vehicle at a dealer lot. The site includes pictures
                of the vehicles for sale, information on vehicle equipment, and a condition report on the vehicle. When you 
                find a vehicle you want to purchase, you can use the financing available through the website, or you 
                can arrange your own. The site also includes information about service contracts you can buy for the vehicle. 
                Happy shopping!
            </div>

            <div id="button_mount"style="text-align: center;">
                <div class="button" id="button_1" onClick=" $('.instructions').fadeIn('slow');">
                    Click here for instructions. 
                </div>
                <div class="button" id="button_2"
                     onclick="window.open('https://empvehiclepurchase.elementfleet.com/index.cfm', '_blank').focus();">
                    Element Fleet Management.  
                </div>
            </div>

            <div class="instructions">
                <span class="close" onClick="$('.instructions').hide();"> Close. </span>
                <p>If you're in the market for a used vehicle, you can now include our used fleet vehicles in your mix of options. 
                    Element Fleet Management, our fleet management provider, has developed a special website for us that lists the best of our company vehicles that have been turned in for sale. 
                    The vehicles on this site are priced substantially below what you would pay for a similar vehicle at a dealer lot. The site includes pictures of the vehicles for sale, information on vehicle equipment, and a condition report on the vehicle. 
                    To access the website, go to empvehiclepurchase.elementfleet.com .  The login is sefreight and the password is sefreight.  Both are case-sensitive.
                    When you find a vehicle you want to purchase, you can use the financing available through the web site, or you can arrange your own.  The site also includes information about service contracts you can buy for the vehicle. 
                    If you want to see the actual vehicle, the location where the vehicle is being stored is listed on the website. 
                    Because of insurance restrictions, test drives are not allowed. Please keep in mind that each vehicle will be posted for 14 days on empvehiclepurchase.elementfleet.com.
                </p>
            </div>


        </body>
        <div style="text-align: center">
            <img src="/vspics/CompanyCar.png"  style="width:358px;height:268px; padding: 20px">
        </div>

    </span>
    <style>
        .button{
            display: inline-block; 
            font-family: sans-serif; 
            font-weight: bold; 
            text-align: center; 
            font-size: 2.4ex; 
            line-height: 1.8ex; 
            border-radius: 1.2ex; 
            margin: 4px; 
            padding: 5px; 
            color: #660000; 
            background: white; 
            border: 1px solid black; 
            text-decoration: none; 
            cursor: pointer; 
            z-index: 1;
        }
        .button:hover { 
            color: white; 
            background: #660000; 
            border-color: black; 
            text-decoration: none; 
        }
        .instructions { 
            color: #000000; 
            background-color :#FFFFFF; 
            border: 1px solid black; 
            border-radius : .5em; 
            padding: 10px 20px; 
            height: auto;  
            width:500px; 
            position: absolute; 
            display: none; 
            padding-top:15px;
        }
        .close{
            border: 1px solid black; 
            padding: 0 5px; border-radius: .25em; 
            background-color: #993030; 
            color: #ffffff; 
            position: absolute; 
            right: 10px; 
            top: 10px; 
            cursor: pointer; 
            text-align: right;
        }
    </style>
</html>

