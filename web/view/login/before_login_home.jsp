<%--
    Document   : before_login_home
    Created on : Sep 21, 2017, 11:01:48 AM
    Author     : Com7_2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Health Department</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/login.css" type="text/css" rel="stylesheet" media="Screen"/>
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>


            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <div id="login">
                            <h2>Login </h2>
                            <div id="loginborder"></div>
                            <form  action="LoginController" method="post">
                                <fieldset>
                                    <p>
                                        <span>User Name</span>
                                        <input id="name" name="user_name" type="text" class="text" placeholder="Enter User Name.." />
                                    </p>
                                    <p>
                                        <span >Password</span>
                                        <input id="password" name="password" class="text" type="password" placeholder="Enter Password.." />
                                    </p>
                                    <p>
                                        <span style="background-color:${msgColor}">${message}</span>
                                    </p>
                                    <p>
                                        <input  type="submit" class="button1" name="task" id="login12" value="login"/>
                                    </p>

                                </fieldset>
                            </form>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
