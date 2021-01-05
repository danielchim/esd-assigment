<!DOCTYPE html>
<html lang="en">

<head>
    <title>SB Admin 2 - Dashboard</title>

    <jsp:include page="/content/headconfigs.jsp" />

    <%@ page import="res.bean.UserBean" %>
    <%!
        UserBean userInfo = null;
    %>
    <%
        if(request.getAttribute("userInfo") != null){
            userInfo = (UserBean) request.getAttribute("userInfo");
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
                                            <h3><%=userInfo.getLastName() + " " + userInfo.getFirstName()%></h3>
                                            <small>Email:</small>
                                            <h3><%=userInfo.getEmail()%></h3>
                                        </div>
                                        <div class="col-1">
                                            <button type="button" class="btn btn-primary" data-toggle="modal"data-target="#editUserModal" userID="<%=userInfo.getUserID()%>" firstName="<%=userInfo.getFirstName()%>" lastName="<%=userInfo.getLastName()%>" email="<%=userInfo.getEmail()%>" password="<%=userInfo.getPassword()%>" userPhone="<%=userInfo.getTel()%>">Edit</button>
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
                                    <form action="${pageContext.request.contextPath}/profile" method="get">
                                        <div class="form-group">
                                            <label for="lastName">Last Name</label>
                                            <input type="text" class="form-control" id="lastName" name="lastName" aria-describedby="emailHelp">
                                        </div>
                                        <div class="form-group">
                                            <label for="firstName">First Name</label>
                                            <input type="text" class="form-control" id="firstName" name="firstName" aria-describedby="emailHelp">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPhone">Phone Number</label>
                                            <input type="text" class="form-control" id="userPhone" name="userPhone" value="" aria-describedby="phoneNumber" autocomplete="off">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPhone">Email</label>
                                            <input readonly type="text" class="form-control" id="userMail" name="userMail" value="" aria-describedby="email">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPasswd">Password</label>
                                            <input type="password" class="form-control" id="userPasswd" name="userPasswd">
                                        </div>
                                        <div class="form-group">
                                            <label for="userPasswdRepeat">Repeat Password</label>
                                            <input type="password" class="form-control" id="userPasswdRepeat">
                                        </div>
                                        <input hidden name="action" value="update">
                                        <input hidden id="userID" name="userID" value="">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="submit" class="btn btn-primary">Submit</button>
                                    </form>
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
            let userID = $(opener).attr('userID');
            let firstName = $(opener).attr('firstName');
            let lastName = $(opener).attr('lastName');
            let email = $(opener).attr('email');
            let password = $(opener).attr('password');
            let userPhone = $(opener).attr('userPhone');
            console.log(userPhone);
            // set what we got to our form
            $("#lastName").attr('value', lastName);
            $("#userID").attr('value', userID);
            $("#firstName").attr('value', firstName);
            $("#userPhone").attr('value',userPhone);
            $("#userMail").attr('value',email);
            $("#userPasswd").attr('value', password);
        });
    });
</script>

</body>

</html>