package res.servlet;

import res.bean.EquipBean;
import res.bean.RecordBean;
import res.bean.UserBean;
import res.db.EquipDB;
import res.db.RecordDB;
import res.db.ReservationDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RecordController", urlPatterns = {"/record"})
public class RecordController extends HttpServlet {
    EquipDB equipDB;
    RecordDB recordDB;
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
        recordDB = new RecordDB(dbUrl, dbUser, dbPassword);
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
            System.out.print("test!");
        }else if("filter".equals(action)) {
            doSearch(request, response);
        }else if("logout".equals(action)){

        }else{
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    public void doFetchData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<EquipBean> ebs = equipDB.queryEquip();
        request.setAttribute("equipList", ebs);
        UserBean targetUser = (UserBean)request.getSession().getAttribute("userInfo");
        ArrayList<RecordBean> rb = recordDB.fetchRecord(targetUser.getUserID());
        request.setAttribute("recordList", rb);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/record.jsp");
        rd.forward(request, response);
    }

    public void doInsertReservation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        // String userID = session.getAttribute("userInfo");
        String[] names = request.getParameterValues("equipment");
        int columnLength = recordDB.fetchIdLength("recordID","record")+1;
        for(int i = 0; i< names.length;i++){
            // converting names into ids
            int equipmentID = equipDB.fetchEquipmentID(names[i]);
            recordDB.insertRecord(columnLength ,equipmentID, 1);
            if(columnLength != 1){
                columnLength++;
            }
        }
        doFetchData(request, response);
    }
    public void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<EquipBean> ebs = equipDB.queryEquip();
        request.setAttribute("equipList", ebs);
        String status = request.getParameter("status");
        String equipment = request.getParameter("searchVal");
        UserBean targetUserBean = (UserBean) request.getSession().getAttribute("userInfo");
        ArrayList<RecordBean> rb = recordDB.searchRecord(targetUserBean.getUserID(),status,equipment);
        request.setAttribute("recordList", rb);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/record.jsp");
        rd.forward(request, response);
    }
}

