/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package res.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import res.bean.EquipBean;
import res.bean.UserBean;
import res.db.EquipDB;
import res.db.UserDB;

/**
 *
 * @author erd25
 */
@WebServlet(name = "AnalyticsController", urlPatterns = {"/Analytics"})
public class AnalyticsController extends HttpServlet {

    private UserDB userDB;
    private EquipDB equipDB;
    
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userDB = new UserDB(dbUrl, dbUser, dbPassword);
        equipDB = new EquipDB(dbUrl, dbUser, dbPassword);
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AnalyticsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AnalyticsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action = "search";
        }
        if("search".equals(action)){
            doSearch(request, response);
        }else{
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    
    public void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<EquipBean> ebs;
        ArrayList<UserBean> ubs;
        String keyword = request.getParameter("s-keyword");
        String status = request.getParameter("s-status");
        String period = request.getParameter("s-period");
        String studentID = request.getParameter("s-sid");
        boolean overdue = request.getParameter("s-od") != null;
        
//        if(request.getParameter("dataact") != null){
//            if(request.getParameter("dataact").equals("editdata")){
//                request.setAttribute("dataact", "editdata");
//                request.setAttribute("editID", request.getParameter("editID"));
//            }else if(request.getParameter("dataact").equals("deldata")){
//                request.setAttribute("dataact", "deldata");
//                request.setAttribute("delID", request.getParameter("delID"));
//            }
//        }
        
//        if((keyword == null || keyword.equals("")) && (status == null || status.equals(""))){
//            ebs = equipDB.queryEquip();
//        }else{
//            if((keyword != null || !keyword.equals("")) && (status == null || status.equals(""))){
//                // case: only keyword
//                ebs = equipDB.searchEquip(keyword, null);
//            }else if((keyword == null || keyword.equals("")) && (status != null || !status.equals(""))){
//                // case: only status
//                ebs = equipDB.searchEquip(status, null);
//            }else{
//                // case: both keyword and status
//                ebs = equipDB.searchEquip(keyword, status);
//            }
//        }

        ubs = userDB.queryUser();
        ebs = equipDB.queryEquip();
        request.setAttribute("aequipList", ebs);
        request.setAttribute("userList", ubs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/analytics.jsp");
        rd.forward(request, response);
    }

}