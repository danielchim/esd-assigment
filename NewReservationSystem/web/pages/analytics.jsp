<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="/content/headconfigs.jsp" />
    <%@page import="res.bean.EquipBean, res.bean.UserBean, res.bean.RecordBean, java.util.ArrayList, java.util.LinkedHashSet, res.db.UserDB, res.db.EquipDB, res.db.RecordDB" %>
    <title>SB Admin 2 - Dashboard</title>
    
    
    <%!
        ArrayList<EquipBean> aequipList = new ArrayList<EquipBean>();
        ArrayList<UserBean> userList = new ArrayList<UserBean>();
        ArrayList<RecordBean> arecordList = new ArrayList<RecordBean>();
        String dbUser = "root";
        String dbPassword = "";
        String dbUrl = "jdbc:mysql://localhost:3306/itp4511_db";
        EquipDB equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
        UserDB userDB = new UserDB(dbUrl, dbUser, dbPassword);
        RecordDB recordDB = new RecordDB(dbUrl, dbUser, dbPassword);
        String selectID = "";
        boolean equipSearch = true;
    %>
    <%
        equipSearch = request.getAttribute("searchact") != null;
        if(request.getAttribute("aequipList") != null && request.getAttribute("userList") != null){
            aequipList = (ArrayList<EquipBean>)request.getAttribute("aequipList");
            userList = (ArrayList<UserBean>)request.getAttribute("userList");
        }else{
            response.sendRedirect(request.getContextPath() + "/Analytics" );
        }
        if(request.getParameter("dataact") != null){
            if(request.getParameter("dataact").equals("search")){
                String id = request.getParameter("ms-sid");
                String email = request.getParameter("ms-email");
                ArrayList<UserBean> newUList = new ArrayList<UserBean>();
                boolean hvID = !id.equals("");
                boolean hvEmail = !email.equals("");
                if(hvID){
                    for(UserBean data : userList){
                        if((data.getUserID()+"").contains(id)){
                            newUList.add(data);
                        }
                    }
                }
                if(hvEmail){
                    for(UserBean data : userList){
                        if(data.getEmail().contains(email)){
                            newUList.add(data);
                        }
                    }
                }
                
                LinkedHashSet<UserBean> hashSet = new LinkedHashSet<UserBean>(newUList);
                userList = new ArrayList<UserBean>(hashSet);
                
                if(!hvID && !hvEmail){
                    userList = (ArrayList<UserBean>)request.getAttribute("userList");
                }
                
                out.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>"
                        + "<script type='text/javascript'>"
                        + "$(document).ready(function(){"
                        + "$('#searchModal').modal('show');"
                        + "});"
                        + "</script>");
            }
        }
        if(request.getParameter("selectID") != null){
            selectID = request.getParameter("selectID");
            if(!selectID.equals("")){
                arecordList = recordDB.fetchRecord(Integer.parseInt(selectID));
            }
        }
        if(request.getAttribute("arecordList") != null){
            arecordList = (ArrayList<RecordBean>)request.getAttribute("arecordList");
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
                        <h1 class="h3 mb-0 text-gray-800">Analytics</h1>
                    </div>

                    <div class="container-fluid card rounded border shadow mt-3 mb-3 p-0">
                        <!-- Content Row -->
                        <form method="get" action="/NewReservationSystem/Analytics">
                            <input type="hidden" name="action" value="search" />
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row container-fluid">
                                        <div class="col-md-5 p-4">
                                            <div class="form-group">
                                                <label for="input-s-key">Equipment Key Word</label>
                                                <input type="text" class="form-control" id="inputKey" name="s-keyword" />
                                            </div>
                                        </div>
                                        <div class="col-md-1 p-4">
                                            <div class="form-group mt-4 pt-3">
                                                <input type="checkbox" name="s-od" id="s-od" class="form-check-input" />
                                                <label for="s-od" class="form-check-label">Overdue</label>
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="inputAva">Status</label>
                                                <select id="inputAva" class="form-control" name="s-status">
                                                    <option selected value="">Choose...</option>
                                                    <option>Available</option>
                                                    <option>Unavailable</option>
                                                    <option>Disabled</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="input-s-period">Period</label>
                                                <select id="input-s-period" class="form-control" name="s-period">
                                                    <option selected value="">Choose...</option>
                                                    <option>Monthly</option>
                                                    <option>Yearly</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-md-2 p-4">
                                            <div class="form-group">
                                                <label for="input-s-stu">Student</label>
                                                <input type="hidden" name="s-sid" value="<%=selectID%>" />
                                                <button type="button" class="btn btn-primary rounded-pill form-control" id="search" data-toggle="modal" data-target="#searchModal" id="input-s-stu">
                                                    <% if(!selectID.equals("")){ out.print("#"+selectID); }else{ out.print("Not Selected"); } %>
                                                </button>
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
                        </form>
                        
                        <hr class="m-4"/>
                        
                        <div class="row ml-4">
                            Period:
                        </div>
                        
                        <!-- Content Row -->
                        <div class="row m-4">
                            <table class="table table-striped">
                                <%
                                    if(selectID.equals("") && equipSearch){
                                        out.println("<th>Equip ID</th>" +
                                        "<th>Equip Name</th>" +
                                        "<th>Quantity (Stock / Total)</th>" +
                                        "<th>Status</th>" +
                                        "<th>Utilization Rate</th>" + 
                                        "<th>Description</th>");
                                        
                                        if(aequipList.size() > 0){
                                            for(EquipBean data : aequipList){
                                                out.println("<tr>");
                                                out.println("<td>" + data.getEquipID() + "</td>");
                                                out.println("<td>" + data.getEquipName() + "</td>");
                                                out.println("<td>" + data.getAvaQuantity() + " / " + data.getQuantity() +"</td>");
                                                out.println("<td>" + data.getStatus() + "</td>");
                                                out.println("<td>" + String.format("%,.2f", recordDB.getUtilizationRate(data.getEquipID(), "")*100) + "%</td>");
                                                out.println("<td>" + data.getDescription() +"</td>");
                                                out.println("</tr>");
                                            }
                                        }else{
                                            out.println("<tr><td colspan='5'>No data<td></tr>");
                                        }
                                    }else{
                                        out.println("<th>Record ID</th>" +
                                                    "<th>User ID</th>" +
                                                    "<th>Equipment Name</th>" +
                                                    "<th>Status</th>" +
                                                    "<th>Quantity</th>" +
                                                    "<th>Start Date</th>" +
                                                    "<th>Due Date</th>");
                                        
                                        if(arecordList.size() > 0){
                                            for(RecordBean data : arecordList){
                                                out.println("<tr>");
                                                out.println("<td>" + data.getRecordID() + "</td>");
                                                out.println("<td>" + data.getUserID() + "</td>");
                                                out.println("<td>" + data.getItemName() + "</td>");
                                                out.println("<td>" + data.getStatus() +"</td>");
                                                out.println("<td>" + data.getQuantity() +"</td>");
                                                out.println("<td>" + data.getStartDate()+ "</td>");
                                                out.println("<td>" + data.getDueDate()+"</td></tr>");
                                            }
                                        }else{
                                            out.println("<tr><td colspan='6'>No data<td></tr>");
                                        }
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

<!-- Search Modal-->
<div class="modal fade bd-example-modal-lg" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
    aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            
                <div class="modal-header">
                    <h5 class="modal-title">Search for a student</h5>
                </div>
                <div class="modal-body">
                    <form method="get" action="/NewReservationSystem/Analytics">
                        <input type="hidden" name="dataact" value="search" />
                        <div class="row m-1">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label for="input-c-uid">Student ID</label>
                                    <input type="text" class="form-control" id="input-c-uid" name="ms-sid" />
                                </div>
                            </div>
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label for="input-c-email">E-mail</label>
                                    <input type="text" class="form-control" id="input-c-email" name="ms-email" />
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
                    </form>
                    <hr>
                    <div class="row m-4">
                        <table class="table table-striped">
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Tel</th>
                            <th>E-mail</th>
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
                                        out.println("<td>" + data.getOverdueCount() +"</td>");
                                        out.println("<td><div class='row'>"  
                                                + "<form method='get' action='/NewReservationSystem/Analytics'><input type='hidden' name='adataact' value='selectdata' /><input type='hidden' name='selectID' value='"+ data.getUserID() +"' />"
                                                + "<button type='submit' class='btn btn-outline-primary rounded-pill modalbtn'>Select</button></form> "
                                                + "</div></td></tr>");
                                    }
                                }else{
                                    out.println("<tr><td colspan='7'>No data<td></tr>");
                                }
                            %>
                        </table>
                    </div>
                </div>
                
                
                <div class="modal-footer">
                    <form method="get" action="/NewReservationSystem/Analytics">
                        <input type='hidden' name='dataact' value='selectdata' />
                        <input type='hidden' name='selectID' value="" />
                        <button class="btn btn-secondary" type="submit">Cancel</button>
                    </form>
                </div>
            
        </div>
    </div>
</div>


</html>