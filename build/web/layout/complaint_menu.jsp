<%-- 
    Document   : complaint_menu
    Created on : May 19, 2014, 10:47:58 AM
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
        <title>Complaint Tab</title>
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
            <a href="#">Complaint</a>
            <div>
                <ul>
                    <li><a href="complaintregisterCont.do">Complaint Register</a></li>
                    <li><a href="complaintStatusCont.do">Complaint Status</a></li>
                    <li style="display: none"><a href="complaintTypeCont.do">Complaint Type</a></li>
                    <li><a href="complaintTypesCont.do"> Complaint Type</a></li>
                    <li><a href="complaintSubTypesCont.do"> Complaint Sub Type</a></li>
                    <li><a href="complaintAssingnedForCont.do"> Complaint Assinged For</a></li>
                    <li><a href="complaintFeedbackTypeCont.do">Feedback Type</a></li>
                   <li><a href="complaintFeedbackCont.do">Complaint Feedback </a></li>
                  <li><a href="complaintHistoryReportCont.do">Complaint History Report </a></li>
                  <li><a href="complaintMISCont.do">Complaint MIS </a></li>
                  <li><a href="designationHierarchyCont.do">Designation Hierarchy</a></li>
                  <li><a href="personHierarchyCont.do">Person Hierarchy</a></li>
                   <!--<li><a href="complaint_helpfile_view">Help File </a></li>-->
                </ul>
            </div>
        </div>
    </body>
</html>
