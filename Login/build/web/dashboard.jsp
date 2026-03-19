
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
    </head>
    <body>
        <h1>Welcome to Dashboard</h1>
        <% 
            String user = (String)session.getAttribute("username");
            if(user == null){
                response.sendRedirect("login.html");
            }else{
                out.println("<h3>Hello, " + user + "</h3>");
            }
            %>
    </body>
</html>
