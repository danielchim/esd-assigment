<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="/content/headconfigs.jsp" />
    <%@page import="res.bean.UserBean, java.util.ArrayList, res.db.UserDB" %>
    <title>SB Admin 2 - Dashboard</title>
    
    
    <%!
        ArrayList<UserBean> userList = new ArrayList<UserBean>();
        String dbUser = "root";
        String dbPassword = "";
        String dbUrl = "jdbc:mysql://localhost:3306/itp4511_db";
        UserDB userDB = new UserDB(dbUrl, dbUser, dbPassword);
        UserBean ub = new UserBean();
    %>
    <%
        if(request.getAttribute("userList") != null){
            userList = (ArrayList<UserBean>)request.getAttribute("userList");
        }else{
            response.sendRedirect(request.getContextPath() + "/Account" );
        }
        if(request.getAttribute("dataact") != null){
            if(request.getAttribute("dataact").equals("editdata")){
                ub = (UserBean)userDB.searchUser("#" + request.getAttribute("editID"), null).get(0);
                out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>"
                        + "<script type='text/javascript'>"
                        + "$(document).ready(function(){"
                        + "$('#editModal').modal('show');"
                        + "});"
                        + "</script>");
            }else if(request.getAttribute("dataact").equals("deldata")){
                ub = (UserBean)userDB.searchUser("#" + request.getAttribute("delID"), null).get(0);
                out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>"
                        + "<script type='text/javascript'>"
                        + "$(document).ready(function(){"
                        + "$('#delModal').modal('show');"
                        + "});"
                        + "</script>");
            }
        }
    %>
</head>

<body id="page-top">
    
    <!-- Page Wrapper -->
    <div id="wrapper">

        <jsp:include page="/content/sidebar.jsp" />

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <jsp:include page="/content/topbar.jsp" />

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Account Management</h1>
                    </div>

                    <div class="container-fluid card rounded border shadow mt-3 mb-3 p-0">
                        <!-- Content Row -->
                        
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row container-fluid">
                                    <div class="col-md-6 p-4">
                                        <div class="form-group">
                                            <label for="inputKey">Key Word</label>
                                            <input type="text" class="form-control" id="inputKey" />
                                        </div>
                                    </div>
                                    <div class="col-md-2 p-4">
                                        <div class="form-group">
                                            <label for="inputAva">Status</label>
                                            <select id="inputAva" class="form-control">
                                                <option selected value="">Choose...</option>
                                                <option>Restricted</option>
                                                <option>Overdue</option>
                                                <option>Active</option>
                                                <option>Hidden</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2 p-4">
                                        <div class="form-group">
                                            <label for="inputType">Type</label>
                                            <select id="inputType" class="form-control">
                                                <option selected value="">Choose...</option>
                                                <option>Student</option>
                                                <option>Technician</option>
                                                <option>Senior Technician</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-md-2 p-4">
                                        <div class="form-group">
                                            <label for="inputRange">Overdue Count Range</label>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <input type="text" id="inputRangeS" class="form-control" placeholder="From" />
                                                </div>
                                                <div class="col-md-6">
                                                    <input type="text" id="inputRangeE" class="form-control" placeholder="To" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row justify-content-end">
                                    <div class="text-right" style="padding-right:50px ; padding-bottom: 20px;"> 
                                        <button type="button" class="btn btn-success" id="resetbtn" style="margin-right: 10px;">
                                          Clear
                                          <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-counterclockwise" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
                                            <path d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
                                          </svg>
                                        </button>
                                        <button type="button" class="btn btn-primary" id="search">
                                          Search
                                          <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path fill-rule="evenodd" d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"/>
                                            <path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"/>
                                          </svg>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <hr class="m-4">
                        
                        <!-- Content Row -->
                        <div class="row m-4">
                            <table class="table table-striped">
                                <th>ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Tel</th>
                                <th>E-mail</th>
                                <th>Status</th>
                                <th>Type</th>
                                <th>Register Date</th>
                                <th>Overdue Count</th>
                                <th>Actions</th>
                                <%
                                    if(userList.size() > 0){
                                        for(UserBean data : userList){
                                            out.println("<tr>");
                                            out.println("<td id='data-" + data.getUserID() + "'>" + data.getUserID() + "</td>");
                                            out.println("<td>" + data.getFirstName() + "</td>");
                                            out.println("<td>" + data.getLastName() + "</td>");
                                            out.println("<td>" + data.getTel() + "</td>");
                                            out.println("<td>" + data.getEmail() +"</td>");
                                            out.println("<td>" + data.getStatus() +"</td>");
                                            out.println("<td>" + data.getType() +"</td>");
                                            out.println("<td>" + data.getRegisterDate() +"</td>");
                                            out.println("<td>" + data.getOverdueCount() +"</td>");
                                            out.println("<td><div class='row'>"  
                                                    + "<form method='get' action='/NewReservationSystem/Account'><input type='hidden' name='dataact' value='editdata' /><input type='hidden' name='editID' value='"+ data.getUserID() +"' />"
                                                    + "<button type='submit' class='btn btn-outline-primary rounded-pill modalbtn'>Edit</button></form> "
                                                    + "<form method='get' action='/NewReservationSystem/Account'><input type='hidden' name='dataact' value='deldata'><input type='hidden' name='delID' value='"+ data.getUserID() +"' />"
                                                    + "<button type='submit' class='btn btn-outline-danger rounded-pill modalbtn'>Delete</button></form>"
                                                    + "</div></td></tr>");
                                        }
                                    }else{
                                        out.println("<tr><td colspan='7'>No data<td></tr>");
                                    }
                                %>
                            </table>
                        </div>
                    </div>
                    

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <jsp:include page="/content/footer.jsp" />

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    
    <jsp:include page="/content/addins.jsp" />

</body>

</html>