<%-- 
    Document   : org_menu
    Created on : May 30, 2014, 9:45:52 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link rel="stylesheet" href="style/dropdown.css">
        <title>Organization Menu</title>
        <script>
            $(function() {
                if ($.browser.msie && $.browser.version.substr(0,1)<7)
                {
                    $('li').has('ul').mouseover(function(){
                        $(this).children('ul').css('visibility','visible');
                    }).mouseout(function(){
                        $(this).children('ul').css('visibility','hidden');
                    })
                }
            });
        </script>
    </head>
    <body>
        <div class="tab">
            <a href="#">Organization Menu</a>
            <div>
                <ul>                
                    <li><a href="orgDetailEntryCont.do">Org. All in One</a></li>
                    <li><a href="orgNameCont.do">Org. Name</a></li>
                    <li><a href="orgTypeCont.do">Org. Type</a></li>
                    <li><a href="organisationSubTypeCont.do">Org. Sub Type</a></li>
                    <li><a href="mapOrgCont.do">Org. Map Relation</a></li>
                    <li><a href="orgOfficeTypeCont.do">Org. Office Type</a></li>                                        
                    <li><a href="organisationCont.do">Org. Office</a></li>
                    <li><a href="designationCont.do">Designation</a></li>
                    <li><a href="personCount.do">Org Person's Name</a></li>
                </ul>
            </div>
        </div>
    </body>
</html>
