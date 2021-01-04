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
        UserBean ub = null;
    %>
    <%
        if(request.getAttribute("userList") != null){
            userList = (ArrayList<UserBean>)request.getAttribute("userList");
        }else{
            response.sendRedirect(request.getContextPath() + "/Account" );
        }
        if(request.getAttribute("udataact") != null){
            if(request.getAttribute("udataact").equals("editdata")){
                ub = (UserBean)userDB.searchUser("#" + request.getAttribute("ueditID"), null, null, -1, -1).get(0);
                out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>"
                        + "<script type='text/javascript'>"
                        + "$(document).ready(function(){"
                        + "$('#editModal').modal('show');"
                        + "});"
                        + "</script>");
            }else if(request.getAttribute("udataact").equals("deldata")){
                ub = (UserBean)userDB.searchUser("#" + request.getAttribute("udelID"), null, null, -1, -1).get(0);
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
                        <form method="get" action="/NewReservationSystem/Account">
                            <input type="hidden" name="action" value="search" />
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row container-fluid">
                                        <div class="col-md-6 p-4">
                                            <div class="form-group">
                                                <label for="input-s-key">Key Word</label>
                                                <input type="text" class="form-control" id="inputKey" name="s-keyword" />
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="input-s-status">Status</label>
                                                <select id="input-s-status" class="form-control" name="s-status">
                                                    <option selected value="">Choose...</option>
                                                    <option>Disabled</option>
                                                    <option>Overdue</option>
                                                    <option>Active</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="input-s-type">Type</label>
                                                <select id="input-s-type" class="form-control" name="s-type">
                                                    <option selected value="">Choose...</option>
                                                    <option>Student</option>
                                                    <option>Technician</option>
                                                    <option>Senior Technician</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="input-s-range">Overdue Count Range</label>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <input type="text" id="input-s-range" class="form-control" placeholder="From" name="s-odCount0" />
                                                    </div>
                                                    <div class="col-md-6">
                                                        <input type="text" id="input-s-range2" class="form-control" placeholder="To" name="s-odCount1" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row justify-content-end">
                                        <div class="text-right" style="padding-right:50px ; padding-bottom: 20px;"> 
                                            <button type="reset" class="btn btn-success" id="resetbtn" style="margin-right: 10px;">
                                              Clear
                                              <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-arrow-counterclockwise" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M8 3a5 5 0 1 1-4.546 2.914.5.5 0 0 0-.908-.417A6 6 0 1 0 8 2v1z"/>
                                                <path d="M8 4.466V.534a.25.25 0 0 0-.41-.192L5.23 2.308a.25.25 0 0 0 0 .384l2.36 1.966A.25.25 0 0 0 8 4.466z"/>
                                              </svg>
                                            </button>
                                            <button type="submit" class="btn btn-primary" id="search">
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
                        </form
                        
                        <hr class="m-4">
                        
                        <div class="row ml-4">
                                <button type="button" class="btn btn-outline-success rounded-pill" id="create" data-toggle="modal" data-target="#createModal">
                                    <i class="fas fa-plus"></i>
                                    Create
                                </button>
                        </div>
                        
                        <!-- Content Row -->
                        <div class="row m-4">
                            <table class="table table-striped">
                                <th>ID</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Password</th>
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
                                            out.println("<td>" + data.getPassword() + "</td>");
                                            out.println("<td>" + data.getTel() + "</td>");
                                            out.println("<td>" + data.getEmail() +"</td>");
                                            out.println("<td>" + data.getStatus() +"</td>");
                                            out.println("<td>" + data.getType() +"</td>");
                                            out.println("<td>" + data.getRegisterDate() +"</td>");
                                            out.println("<td>" + data.getOverdueCount() +"</td>");
                                            out.println("<td><div class='row'>"  
                                                    + "<form method='get' action='/NewReservationSystem/Account'><input type='hidden' name='udataact' value='editdata' /><input type='hidden' name='ueditID' value='"+ data.getUserID() +"' />"
                                                    + "<button type='submit' class='btn btn-outline-primary rounded-pill modalbtn'>Edit</button></form> "
                                                    + "<form method='get' action='/NewReservationSystem/Account'><input type='hidden' name='udataact' value='deldata'><input type='hidden' name='udelID' value='"+ data.getUserID() +"' />"
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

<!-- Create Modal-->
<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form method="get" action="/NewReservationSystem/Account">
                <input type="hidden" name="action" value="create" />
                <div class="modal-header">
                    <h5 class="modal-title">Create new account</h5>
                </div>
                <div class="modal-body">
                    <div class="row m-1">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="input-c-uid">Student ID</label>
                                <input type="text" class="form-control" id="input-c-uid" name="uid" />
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <label for="input-c-email">E-mail</label>
                                <input type="text" class="form-control" id="input-c-email" name="uemail" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-c-pwd">Password</label>
                                <input type="password" class="form-control" id="input-c-pwd" name="upwd" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-c-type">Type</label>
                                <select id="input-c-type" class="form-control" name="utype">
                                    <option selected="selected">Student</option>
                                    <option>Technician</option>
                                    <option>Senior Technician</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row m-1">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="input-c-name">First Name</label>
                                <input type="text" class="form-control" id="input-c-name" name="ufname" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="input-c-q">Last Name</label>
                                <input type="text" class="form-control" id="input-c-q" name="ulname" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-c-tel">Tel</label>
                                <input type="text" class="form-control" id="input-c-tel" name="utel" />
                            </div>
                        </div>
                    </div>
                    
                    <div class="row m-2">
                        <div class="col-md-12">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="input-c-disa" name="udisa" value="true">
                                <label class="form-check-label" for="input-c-disa">Disable</label>
                            </div>
                        </div>
                    </div>
                </div>
                
                
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <input type="submit" value="Create" class="btn btn-success" />
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit Modal-->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form method="get" action="/NewReservationSystem/Account">
                <input type="hidden" name="action" value="edit" />
                <input type="hidden" name="editID" value="<% if(ub != null){out.print(ub.getUserID());} %>" />
                <div class="modal-header">
                    <h5 class="modal-title">Edit account</h5>
                    <div id="edit-id"><% if(ub != null){out.print("#" + ub.getUserID());} %></div>
                </div>
                
                
                <div class="modal-body">
                    <div class="row m-1">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label for="input-e-uid">Student ID</label>
                                <input type="text" class="form-control" id="input-e-uid" name="uid" value="<% if(ub != null){out.print(ub.getUserID());} %>" />
                            </div>
                        </div>
                        <div class="col-md-8">
                            <div class="form-group">
                                <label for="input-e-email">E-mail</label>
                                <input type="text" class="form-control" id="input-e-email" name="uemail" value="<% if(ub != null){out.print(ub.getEmail());} %>" readonly />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-e-pwd">Password</label>
                                <input type="password" class="form-control" id="input-e-pwd" name="upwd" value="<% if(ub != null){out.print(ub.getPassword());} %>" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-e-type">Type</label>
                                <select id="input-e-type" class="form-control" name="utype">
                                    <option <% if(ub != null){ if(ub.getType().equals("Student")){out.print("selected='selected'");} }%>>Student</option>
                                    <option <% if(ub != null){ if(ub.getType().equals("Technician")){out.print("selected='selected'");} }%>>Technician</option>
                                    <option <% if(ub != null){ if(ub.getType().equals("Senior Technician")){out.print("selected='selected'");} }%>>Senior Technician</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row m-1">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="input-e-fname">First Name</label>
                                <input type="text" class="form-control" id="input-e-fname" name="ufname" value="<% if(ub != null){out.print(ub.getFirstName());} %>" />
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="input-e-lname">Last Name</label>
                                <input type="text" class="form-control" id="input-e-lname" name="ulname" value="<% if(ub != null){out.print(ub.getLastName());} %>" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-e-tel">Tel</label>
                                <input type="text" class="form-control" id="input-e-tel" name="utel" value="<% if(ub != null){out.print(ub.getTel());} %>" />
                            </div>
                        </div>
                    </div>
                    
                    <div class="row m-2">
                        <div class="col-md-12">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="input-e-disa" name="udisa" value="true" <% if(ub != null){ if(ub.getStatus().equals("Disabled")){out.print("checked='checked'");} }%>>
                                <label class="form-check-label" for="input-e-disa">Disable</label>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <input type="submit" value="Confirm" class="btn btn-success" />
                </div>
            </form>
        </div>
    </div>
</div>

                                
<!-- Delete Modal-->
<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Are you sure to del all data of the account?</h5>
            </div>
            <div class="modal-body">Select "Delete" below if you are ready to delete the account.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                <form method="POST" action="/NewReservationSystem/Account">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="delID" value="<% if(ub != null){ out.print(ub.getUserID()); }%>" />
                    <input type="submit" value="Delete" class="btn btn-danger" />
                </form>
            </div>
        </div>
    </div>
</div>

</html>