<!DOCTYPE html>
<html lang="en">

<head>
    <title>SB Admin 2 - Dashboard</title>

    <jsp:include page="/content/headconfigs.jsp" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
    <%@ taglib uri="/WEB-INF/tlds/reservation.tld"  prefix="ict" %>

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
            <div class="container-fluid" id="main-wrapper">
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
                    <form action="${pageContext.request.contextPath}/reservation" method="get">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row container-fluid">
                                    <div class="col-md-8 p-4">
                                        <div class="form-group">
                                            <label for="inputKey">Key Word</label>
                                            <input type="text" class="form-control" id="inputKey" name="searchVal"/>
                                        </div>
                                    </div>
                                    <div class="col-md-4 p-4">
                                        <div class="form-group">
                                            <label for="inputAva">Availability</label>
                                            <select id="inputAva" class="form-control" name="status">
                                                <option selected value="">Choose...</option>
                                                <option value="ready">Available</option>
                                                <option value="reserved">Under Reserve</option>
                                            </select>
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
                                        <input hidden name="action" value="filter">
                                        <button type="submit" class="btn btn-primary" id="filter">
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
                    <hr class="m-4">
                    <div class="row ml-4">
                        <button type="button" class="btn btn-outline-success rounded-pill" id="create" data-toggle="modal" data-target="#newReservationModal">
                        <i class="fas fa-plus"></i>
                        Create
                        </button>
                    </div>
                    <!-- Content Row -->
                    <div class="row m-4">
                        <table class="table table-striped" id="reservationTable">
                            <th>Record ID</th>
                            <th>Reserved Equipment Name</th>
                            <th>Availability</th>
                            <th>Reserved Quantity</th>
                            <th>Actions</th>
                            <%
                                if(reservationList.size() > 0){
                                    for(RecordBean data : reservationList){
                                        out.println("<tr>");
                                        out.println("<td>" + data.getRecordID() + "</td>");
                                        out.println("<td>" + data.getItemName() + "</td>");
                                        out.println("<td>" + data.getStatus() + "</td>");
                                        out.println("<td>" + +data.getQuantity() +"</td>");
                                        out.println("<td> <button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#editReservationModal\" equipName=\""+ data.getItemName() + "\" quantity=" + data.getQuantity() + " userID=" + data.getUserID() + " recordID=" + data.getRecordID() + ">Edit</button><button type=\"button\" class=\"btn btn-danger\" style=\"margin-left: 5%\" data-toggle=\"modal\" data-target=\"#deleteReservationModal\" recordID=" + data.getRecordID() + ">Delete</button>\n</td></tr>");
                                    }
                                }else{
                                    out.println("<tr><td colspan='5'>No data<td></tr>");
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
                            <h5 class="modal-title" id="newReversatonModal">New reservation</h5>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/reservation" method="get">
                                <div id="itemWrapper">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm">
                                                <label for="exampleFormControlSelect1">New Item</label>
                                            </div>
                                            <div class="col-sm">
                                                <label for="spinner">Quantity</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm">
                                                <select class="form-control" id="exampleFormControlSelect1" name="equipment">
                                                    <%
                                                        if(equipList.size() > 0){
                                                            for(EquipBean data : equipList){
                                                                out.println("<option>" + data.getEquipName() + "</option>");
                                                                out.println();
                                                            }
                                                        }else{
                                                            out.println("<li>No data<td></li>");
                                                        }
                                                    %>
                                                </select>
                                            </div>

                                            <div class="col-sm">
                                                <input id = "spinner" type="number" value="1" min="1" max="10" step="1" name="quantity"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-outline-success rounded-pill" id="insertReservationItem">
                                        <i class="fas fa-plus"></i>
                                        More items
                                    </button>
                                </div>
                                <input type="hidden" name="action" value="insert" />
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--Edit reservation modal-->
            <div class="modal fade" id="editReservationModal" tabindex="-1" role="dialog" aria-labelledby="editReservationModal"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Edit reservation</h5>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/reservation" method="get">
                                <div id="itemWrapper">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-sm">
                                                <label for="exampleFormControlSelect1">New Item</label>
                                            </div>
                                            <div class="col-sm">
                                                <label for="spinner">Quantity</label>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm">
                                                <select class="form-control" id="equipmentDropdown" name="equipmentName">
                                                    <%
                                                        if(equipList.size() > 0){
                                                            for(EquipBean data : equipList){
                                                                out.println("<option>" + data.getEquipName() + "</option>");
                                                                out.println();
                                                            }
                                                        }else{
                                                            out.println("<option>No data</option>");
                                                        }
                                                    %>
                                                </select>
                                            </div>

                                            <div class="col-sm">
                                                <input id = "quantity" type="number" value="" min="1" max="10" step="1" name="quantity"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="action" value="update" />
                                <input type="hidden" id ="userID" name="userID" value="" />
                                <input type="hidden" id ="editTargetRecordID" name="recordID" value="" />
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!--Delete reservation modal-->
            <div class="modal fade" id="deleteReservationModal" tabindex="-1" role="dialog" aria-labelledby="deleteReservationModal"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteReservationMoal">New reservation</h5>
                        </div>
                        <div class="modal-body">
                            <form action="${pageContext.request.contextPath}/reservation" method="get">
                                <div id="itemWrapper">
                                    <p>Are you sure to delete this reservation?</p>
                                </div>
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" id="deleteTargetRecordID" name="recordID" value="" />
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="back" class="btn btn-danger">Cancel</button>
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
<script src="${pageContext.request.contextPath}/js/bootstrap-input-spinner.js"></script>
<script>
    $(document).ready(function() {
        let target = $("#itemWrapper").html();
        let alert = "<div class=\"alert alert-primary\" role=\"alert\">\n" + "  Some of your item(s) is ready."+ "</div>"
        let alertCheck = 0;
        $("#insertReservationItem").click(()=>{
            $("#itemWrapper").append(target);
        });
        $("#reservationTable").each(function() {
            if($(this).find("td").eq(2).text()==='ready' && alertCheck === 0){
                $("#main-wrapper").prepend(alert);
                $(".alert").delay(2000).fadeOut('slow');
                alertCheck++;
            }
        });
        $("#deleteReservationModal").on('show.bs.modal', function (event) {
            // get information to update quickly to modal view as loading begins
            let opener= event.relatedTarget;//this holds the element who called the modal
            // we get details from attributes
            // console.log(opener);
            let recordID = $(opener).attr('recordID');
            // set what we got to our form
            $("#deleteTargetRecordID").attr('value', recordID);
            console.log(recordID);
            // $("#spinner").text(status);
        });
        $("#editReservationModal").on('show.bs.modal', function (event) {
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