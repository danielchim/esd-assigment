package res.servlet;

import javafx.util.converter.LocalDateStringConverter;
import res.bean.EquipBean;
import res.bean.RecordBean;
import res.bean.UserBean;
import res.db.EquipDB;
import res.db.ReservationDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebServlet(name = "BorrowLookupManagementLookupController",urlPatterns = {"/management/borrowManagement"})
public class BorrowLookupManagementLookupController extends HttpServlet {
    EquipDB equipDB;
    ReservationDB reservationDB;
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
        reservationDB = new ReservationDB(dbUrl, dbUser, dbPassword);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "search";
        }
        if("search".equals(action)){
            doFetchData(request, response);
        }else if("insert".equals(action)){
            doInsertReservation(request, response);
        }else if("update".equals(action)){
            try {
                doUpdateReservation(request, response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if("delete".equals(action)){
            doDeleteReservation(request, response);
        }else if("logout".equals(action)){

        }else{
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    public void doFetchData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<EquipBean> ebs = equipDB.queryEquip();
        request.setAttribute("equipList", ebs);
        ArrayList<RecordBean> rb = reservationDB.fetchReservation(0);
        request.setAttribute("reservationList", rb);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/management/borrow_management.jsp");
        rd.forward(request, response);
    }

    public void doInsertReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        // String userID = session.getAttribute("userInfo");
        String[] names = request.getParameterValues("equipment");
        String[] quantity = request.getParameterValues("quantity");
        UserBean targetUserBean = (UserBean) request.getSession().getAttribute("userInfo");
        int columnLength = reservationDB.fetchIdLength("recordID","record")+1;
        for(int i = 0; i< names.length;i++){
            // converting names into ids
            int equipmentID = equipDB.fetchEquipmentID(names[i]);
            reservationDB.insertReservation(columnLength ,equipmentID, quantity[i],targetUserBean.getUserID());
            columnLength++;
        }
        doFetchData(request, response);
    }
    public void doUpdateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        String name = request.getParameter("equipment");
        String quantity = request.getParameter("quantity");
        String status = request.getParameter("status");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/dd/yyyy");
        LocalDate startDate =  LocalDate.parse(request.getParameter("startDate"));
        LocalDate dueDate =  LocalDate.parse(request.getParameter("dueDate"),formatter);
        int userID = Integer.parseInt(request.getParameter("userID"));
        int recordID = Integer.parseInt(request.getParameter("recordID"));
        int equipmentID = equipDB.fetchEquipmentID(name);
        reservationDB.updateReservation(recordID,equipmentID,status,quantity,startDate,dueDate,userID);
        doFetchData(request, response);
    }
    public void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("equipment");
        String status = request.getParameter("status");
        int userID = Integer.parseInt(request.getParameter("userID"));
        reservationDB.searchReservation(userID, status, name);
        doFetchData(request, response);
    }
    public void doDeleteReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int recordID = Integer.parseInt(request.getParameter("recordID"));
        reservationDB.deleteReservation(recordID);
        doFetchData(request, response);
    }
}
