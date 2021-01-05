<!DOCTYPE html>
<html lang="en">

<head>
    <title>SB Admin 2 - Dashboard</title>

    <jsp:include page="/content/headconfigs.jsp" />
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
        if(request.getAttribute("recordList") != null){
            reservationList = (ArrayList<RecordBean>)request.getAttribute("recordList");
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
                    <h1 class="h3 mb-0 text-gray-800">User inormation</h1>
                </div>

                <div class="container-fluid card rounded border shadow mt-3 mb-3 p-0">
                    <!-- Content Row -->
                    <div class="row" style="margin: 1%">
                        <div class="container-fluid toppadding">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-10">
                                            <small>Username</small>
                                            <h3>User1</h3>
                                            <small>Email:</small>
                                            <h3>User1@gmail.com</h3>
                                        </div>
                                        <div class="col-1">
                                            <button type="button" class="btn btn-primary" data-toggle="modal"data-target="#editUserModal" userID="">Edit</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Edit user modal -->
                    <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModal" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editUserModalTitle">Edit User</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <div class="form-group">
                                            <label for="username">Username</label>
                                            <input type="text" class="form-control" id="username" aria-describedby="emailHelp">
                                        </div>
                                        <div class="form-group">
                                            <label for="userMail">Email address</label>
                                            <input type="email" class="form-control" id="userMail" aria-describedby="emailHelp">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPhone">Phone Number</label>
                                            <input type="text" class="form-control" id="userPhone" aria-describedby="emailHelp">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPasswd">Password</label>
                                            <input type="password" class="form-control" id="userPasswd">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPasswdRepeat">Repeat Password</label>
                                            <input type="password" class="form-control" id="userPasswdRepeat">
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary" data-dismiss="modal">Save</button>
                                </div>
                            </div>
                        </div>
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
<script>
    $(document).ready(function() {
        let target = $("#itemWrapper").html();
        let alert = "<div class=\"alert alert-primary\" role=\"alert\">\n" + "  Your item " + $(this).find("td").eq(1).text()+ " is ready."+ "</div>"
        $("#insertReservationItem").click(()=>{
            $("#itemWrapper").append(target);
        });
        $("#editUserModal").on('show.bs.modal', function (event) {
            // get information to update quickly to modal view as loading begins
            let opener= event.relatedTarget;//this holds the element who called the modal
            // we get details from attributes
            let quantity = $(opener).attr('quantity');
            let userID = $(opener).attr('userID');
            let recordID = $(opener).attr('recordID');
            // set what we got to our form
            $("#quantity").attr('value', quantity);
            $("#editTargetRecordID").attr('value', recordID);
            $("#userID").attr('value', userID);
            console.log(recordID);
            // $("#spinner").text(status);
        });
    });
</script>

</body>

</html>