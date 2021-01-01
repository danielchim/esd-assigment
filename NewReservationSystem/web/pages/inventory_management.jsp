<!DOCTYPE html>
<html lang="en">

<head>
    <title>SB Admin 2 - Dashboard</title>

    <jsp:include page="/content/headconfigs.jsp" />
    <%@page import="res.bean.EquipBean, java.util.ArrayList, res.db.EquipDB" %>
    <%!
        ArrayList<EquipBean> equipList = null;
        String dbUser = "root";
        String dbPassword = "";
        String dbUrl = "jdbc:mysql://localhost:3306/itp4511_db";
        EquipDB equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
    %>
    <%
        if(request.getAttribute("equipList") != null){
            equipList = (ArrayList<EquipBean>)request.getAttribute("equipList");
        }else{
            response.sendRedirect(request.getContextPath() + "/Inventory" );
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
                        <h1 class="h3 mb-0 text-gray-800">Inventory Management</h1>
                    </div>

                    <div class="container-fluid card rounded border shadow mt-3 mb-3 p-0">
                        <!-- Content Row -->
                        
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row container-fluid">
                                    <div class="col-md-8 p-4">
                                        <div class="form-group">
                                            <label for="inputKey">Key Word</label>
                                            <input type="text" class="form-control" id="inputKey" />
                                        </div>
                                    </div>
                                    <div class="col-md-4 p-4">
                                        <div class="form-group">
                                            <label for="inputAva">Status</label>
                                            <select id="inputAva" class="form-control">
                                                <option selected value="">Choose...</option>
                                                <option>Available</option>
                                                <option>Overdue</option>
                                                <option>Occupied</option>
                                                <option>Reserved</option>
                                                <option>Disabled</option>
                                            </select>
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
                        
                        <div class="row ml-4">
                                <button type="button" class="btn btn-outline-success rounded-pill" id="create" data-toggle="modal" data-target="#createModal">
                                    <i class="fas fa-plus"></i>
                                    Create
                                </button>
                        </div>
                        
                        <!-- Content Row -->
                        <div class="row m-4">
                            <table class="table table-striped">
                                <th>Equip ID</th>
                                <th>Equip Name</th>
                                <th>Quantity</th>
                                <th>Status</th>
                                <th>Start Date</th>
                                <th>Due Date</th>
                                <th>Description</th>
                                <th>Actions</th>
                                <%
                                    if(equipList.size() > 0){
                                        for(EquipBean data : equipList){
                                            out.println("<tr>");
                                            out.println("<td>" + data.getEquipID() + "</td>");
                                            out.println("<td>" + data.getEquipName() + "</td>");
                                            out.println("<td>" + data.getQuantity() +"</td>");
                                            out.println("<td>" + data.getStatus() + "</td>");
                                            out.println("<td>" + "</td>");
                                            out.println("<td>" + "</td>");
                                            out.println("<td>" + data.getDescription() +"</td>");
                                            out.println("<td>" + "</td>");
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
            <form method="get" action="/NewReservationSystem/Inventory">
                <input type="hidden" name="action" value="create" />
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Create new equipment</h5>
                    #<%=equipDB.getPossibleID()%>
                </div>
                
                
                <div class="modal-body">
                    <div class="row m-1">
                        <div class="col-md-9">
                            <div class="form-group">
                                <label for="input-c-name">Equip Name</label>
                                <input type="text" class="form-control" id="input-c-name" name="ename" />
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="input-c-q">Quantity</label>
                                <input type="text" class="form-control" id="input-c-q" value="0" name="eq" />
                            </div>
                        </div>
                    </div>
                    <div class="row m-1">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="input-c-textarea">Description</label>
                                <textarea class="form-control" id="input-c-textarea" rows="3" name="edesc"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row m-2">
                        <div class="col-md-12">
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="input-c-disa" name="edisa" value="true">
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

</html>