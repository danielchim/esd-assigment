package res.servlet;

import res.bean.EquipBean;
import res.bean.RecordBean;
import res.bean.UserBean;
import res.db.EquipDB;
import res.db.OverdueDB;
import res.db.RecordDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "OverdueLookupController",urlPatterns = {"/overdue"})
public class OverdueLookupController extends HttpServlet {
    EquipDB equipDB;
    OverdueDB overdueDB;
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
        overdueDB = new OverdueDB(dbUrl, dbUser, dbPassword);
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
            // doInsertReservation(request, response);
            System.out.print("test!");
        }else if("logout".equals(action)){

        }else{
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }
    public void doFetchData(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<EquipBean> ebs = equipDB.queryEquip();
        request.setAttribute("equipList", ebs);
        UserBean targetUserBean = (UserBean) request.getSession().getAttribute("userInfo");
        ArrayList<RecordBean> rb = overdueDB.fetchOverdueRecord(targetUserBean.getUserID());
        request.setAttribute("overdueList", rb);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/overdue.jsp");
        rd.forward(request, response);
    }
}
