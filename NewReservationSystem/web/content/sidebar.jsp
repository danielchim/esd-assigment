<%@page import="res.bean.UserBean" %>
<%
    UserBean userinfo = (UserBean)session.getAttribute("userInfo");
    String userType = userinfo.getType();
%>
<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/main" methods="get">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Equipment System </div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/main">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">

    <!-- Heading -->
    <div class="sidebar-heading">
        Interface
    </div>

    <!-- Nav Item - Pages Collapse Menu -->
    <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
            aria-expanded="true" aria-controls="collapseTwo">
            <i class="fas fa-fw fa-cog"></i>
            <span>Start</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
            <div class="bg-white py-2 collapse-inner rounded">
                <h6 class="collapse-header">Borrowing:</h6>
                <a class="collapse-item" href="${pageContext.request.contextPath}/reservation">Equipment reservation</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/record">Records</a>
                <a class="collapse-item" href="${pageContext.request.contextPath}/overdue">Lookup overdues</a>
            </div>
        </div>
    </li>

    <!-- Nav Item - Utilities Collapse Menu -->
    <%
        if(!userType.equals("Student")){
            out.print("<li class='nav-item'>" +
            "<a class='nav-link collapsed' href='#' data-toggle='collapse' data-target='#collapseUtilities'" +
            "aria-expanded='true' aria-controls='collapseUtilities'>" +
            "<i class='fas fa-fw fa-wrench'></i>" +
            "<span>Management</span>" +
            "</a>" +
            "<div id='collapseUtilities' class='collapse' aria-labelledby='headingUtilities'" +
            "data-parent='#accordionSidebar'>" +
            "<div class='bg-white py-2 collapse-inner rounded'>" +
            "<h6 class='collapse-header'> Management tools:</h6>" +
            "<a class='collapse-item' href='/NewReservationSystem/management/borrowManagement'>Check in / out</a>" +
            "<a class='collapse-item' href='/NewReservationSystem/Inventory'>Inventory Management</a>");
            if(userType.equals("Senior Technician")){
                out.print("<a class='collapse-item' href='/NewReservationSystem/Account'>Account Management</a>" +
                            "<a class='collapse-item' href='/NewReservationSystem/Analytics'>Analytics</a>");
            }

            out.print("</div>" +
            "</div>" +
            "</li>");

        }
    %>

    



    <!-- Divider -->
    <hr class="sidebar-divider d-none d-md-block">

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>
<!-- End of Sidebar -->