<!DOCTYPE html>
<html lang="en">

<head>
    <title>SB Admin 2 - Dashboard</title>

    <jsp:include page="/content/headconfigs.jsp" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

    <%@page import="res.bean.EquipBean, java.util.ArrayList" %>
    <%@ page import="res.bean.RecordBean" %>
    <%!
        ArrayList<EquipBean> equipList = null;
        ArrayList<RecordBean> reservationList = null;
    %>
    <%
        if(request.getAttribute("equipList") != null){
            equipList = (ArrayList<EquipBean>)request.getAttribute("equipList");
        }
        if(request.getAttribute("reservationList") != null){
            reservationList = (ArrayList<RecordBean>)request.getAttribute("reservationList");
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
                    <h1 class="h3 mb-0 text-gray-800">Reservation</h1>
                </div>

                <div class="container-fluid card rounded border shadow mt-3 mb-3 p-0">
                    <!-- Content Row -->
                    <div class="row">
                        <div class="toppadding container">
                            <p class="h3 text-center mt-3 mb-3">Search for your Equipment!</p>
                        </div>

                    </div>

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
                                        <label for="inputAva">Availability</label>
                                        <select id="inputAva" class="form-control">
                                            <option selected value="">Choose...</option>
                                            <option>Available</option>
                                            <option>Under Reserve</option>
                                            <option>Occupied</option>
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
                    <!-- Content Row -->
                    <div class="row m-4">
                        <div>
                            <div class="row justify-content-end">
                                <div class="text-right" style="padding-right:50px ; padding-bottom: 20px;">
                                    <a href class="dropdown-item" href="#" data-toggle="modal" data-target="#newReservationModal">
                                        <button type="button" class="btn btn-primary" id="addRecord">
                                            New record
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-search" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd" d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"/>
                                                <path fill-rule="evenodd" d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"/>
                                            </svg>
                                        </button>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <table class="table table-striped">
                            <th>Equip ID</th>
                            <th>Equip Name</th>
                            <th>Availability</th>
                            <th>Available Quantity</th>
                            <th>Available Date</th>
                            <th>Description</th>
                            <th>Actions</th>
                            <%
                                if(reservationList.size() > 0){
                                    for(RecordBean data : reservationList){
                                        out.println("<tr>");
                                        out.println("<td>" + data.getRecordID() + "</td>");
                                        out.println("<td>" + data.getItemName() + "</td>");
                                        out.println("<td>" + "</td>");
                                        out.println("<td>" + "</td>");
                                        out.println("<td>" + "</td>");
                                        out.println("<td>" + "</td>");
                                        out.println("<td>" + data.getStatus() +"</td>");
                                    }
                                }else{
                                    out.println("<tr><td colspan='8'>No data<td></tr>");
                                }
                            %>
                        </table>
                    </div>
                </div>


            </div>
            <!-- /.container-fluid -->
            <!--New reservation modal-->
            <div class="modal fade" id="newReservationModal" tabindex="-1" role="dialog" aria-labelledby="newReservationModal"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">New reservation</h5>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/reservation" method="get">
                                <div class="form-group">
                                    <select class="selectpicker" name="equipment" multiple>
                                        <%
                                            if(equipList.size() > 0){
                                                for(EquipBean data : equipList){
                                                    out.println("<option>" + data.getEquipName() + "</option>");
                                                }
                                            }else{
                                                out.println("<li>No data<td></li>");
                                            }
                                        %>
                                    </select>
                                </div>
                                <input type="hidden" name="action" value="insert" />
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End of Main Content -->

        <jsp:include page="/content/footer.jsp" />

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<jsp:include page="/content/addins.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

</body>

</html>