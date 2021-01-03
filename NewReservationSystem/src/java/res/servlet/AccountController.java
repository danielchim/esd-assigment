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
import res.bean.UserBean;
import res.db.UserDB;

/**
 *
 * @author erd25
 */
@WebServlet(name = "AccountController", urlPatterns = {"/Account"})
public class AccountController extends HttpServlet {
    
    private UserDB userDB;
    
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userDB = new UserDB(dbUrl, dbUser, dbPassword);
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
            out.println("<title>Servlet AccountController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AccountController at " + request.getContextPath() + "</h1>");
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
        }else if("create".equals(action)){
            doCreate(request, response);
        }else if("edit".equals(action)){
            doEdit(request, response);
        }else if("delete".equals(action)){
            doDel(request, response);
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
        ArrayList<UserBean> ubs;
        String keyword = request.getParameter("s-keyword");
        String status = request.getParameter("s-status");
        if(request.getParameter("dataact") != null){
            if(request.getParameter("dataact").equals("editdata")){
                request.setAttribute("dataact", "editdata");
                request.setAttribute("editID", request.getParameter("editID"));
            }else if(request.getParameter("dataact").equals("deldata")){
                request.setAttribute("dataact", "deldata");
                request.setAttribute("delID", request.getParameter("delID"));
            }
        }
        
        if((keyword == null || keyword.equals("")) && (status == null || status.equals(""))){
            ubs = userDB.queryUser();
        }else{
            if((keyword != null || !keyword.equals("")) && (status == null || status.equals(""))){
                // case: only keyword
                ubs = userDB.searchUser(keyword, null);
            }else if((keyword == null || keyword.equals("")) && (status != null || !status.equals(""))){
                // case: only status
                ubs = userDB.searchUser(status, null);
            }else{
                // case: both keyword and status
                ubs = userDB.searchUser(keyword, status);
            }
        }
        request.setAttribute("userList", ubs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/account_management.jsp");
        rd.forward(request, response);
    }
    
    public void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userID = userDB.getPossibleID();
        userDB.insertUser(userID, request.getParameter("uname"), request.getParameter("edesc"));
        if(request.getParameter("edisa") != null){
            userDB.insertDisaUser(userID);
        }
        response.sendRedirect("/NewReservationSystem/Account");
    }
    
    public void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int equipID = Integer.parseInt(request.getParameter("editID"));
        String equipName = request.getParameter("ename");
        int quantity = Integer.parseInt(request.getParameter("eq"));
        String description = request.getParameter("edesc");
        boolean disabled = request.getParameter("edisa") != null;
        UserBean ub = new UserBean();
        ub.setEquipID(equipID);
        ub.setEquipName(equipName);
        ub.setQuantity(quantity);
        ub.setDescription(description);
        if(disabled){
            ub.setStatus("Disabled");
        }
        if(userDB.updateEquip(eb)){
            response.sendRedirect("/NewReservationSystem/Inventory");
        }
    }
    
    public void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int equipID = Integer.parseInt(request.getParameter("delID"));
        if(userDB.delEquip(equipID)){
            response.sendRedirect("/NewReservationSystem/Inventory");
        }
    }
}
