<!DOCTYPE html>
<html lang="en">

<head>
    <title>Equipment system</title>

    <jsp:include page="/content/headconfigs.jsp"/>
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
        if(request.getAttribute("record") != null){
            reservationList = (ArrayList<RecordBean>)request.getAttribute("record");
        }
    %>
</head>

<body id="page-top">
<jsp:useBean id="userBean" class="res.bean.UserBean" scope="page"/>

<!-- Page Wrapper -->
<div id="wrapper">

    <jsp:include page="/content/sidebar.jsp"/>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <jsp:include page="/content/topbar.jsp"/>

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
                </div>

                <!-- Content Row -->
                <div class="row">

                    <!-- Earnings (Monthly) Card Example -->
                    <a href="reservation" class="col-xl-6 col-md-6 mb-4 btn">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="font-weight-bold text-primary text-uppercase">
                                            Equipment Reservation
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>


                    <!-- Earnings (Monthly) Card Example -->
                    <a href="record" class="col-xl-6 col-md-6 mb-4 btn">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="font-weight-bold text-info text-uppercase mb-1">
                                            Records
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </a>
                    <!-- Content Row -->

                </div>
                <div class="row">
                    <div class="col-sm">
                        <div class="card shadow">
                            <!-- Card Header - Dropdown -->
                            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">Recent reservations</h6>
                            </div>
                            <!-- Card Body -->
                            <div class="card-body">
                                <div class="row m-4">
                                    <table class="table table-striped" id="reservationTable">
                                        <th>Record ID</th>
                                        <th>Reserved Equipment Name</th>
                                        <th>Availability</th>
                                        <th>Reserved Quantity</th>
                                        <%
                                            int size = 0;
                                            if(reservationList.size() > 0){
                                                for(RecordBean data : reservationList){
                                                    if(size < 5){
                                                        out.println("<tr>");
                                                        out.println("<td>" + data.getRecordID() + "</td>");
                                                        out.println("<td>" + data.getItemName() + "</td>");
                                                        out.println("<td>" + data.getStatus() + "</td>");
                                                        out.println("<td>" + +data.getQuantity() +"</td>");
                                                    }
                                                    size++;
                                                }
                                            }else{
                                                out.println("<tr><td colspan='3'>No data<td></tr>");
                                            }
                                        %>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <jsp:include page="/content/footer.jsp"/>

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Logout Modal-->
    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                    <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">×</span>
                    </button>
                </div>
                <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                    <a class="btn btn-primary" href="login.html">Logout</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/content/addins.jsp"/>

</body>

</html>