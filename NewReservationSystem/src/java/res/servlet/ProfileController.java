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
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
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
        }else if("edit".equals(action)){
            doEdit(request, response);
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
        String type = request.getParameter("s-type");
        String sOdCount0 = request.getParameter("s-odCount0");
        String sOdCount1 = request.getParameter("s-odCount1");
        int odCount0 = 0;
        int odCount1 = -1;
        if(request.getParameter("udataact") != null){
            if(request.getParameter("udataact").equals("editdata")){
                request.setAttribute("udataact", "editdata");
                request.setAttribute("ueditID", request.getParameter("ueditID"));
            }else if(request.getParameter("udataact").equals("deldata")){
                request.setAttribute("udataact", "deldata");
                request.setAttribute("udelID", request.getParameter("udelID"));
            }
        }

        boolean hvKeyword = keyword != null && !keyword.equals("");
        boolean hvStatus = status != null && !status.equals("");
        boolean hvType = type != null && !type.equals("");
        boolean hvOdCount0 = sOdCount0 != null && !sOdCount0.equals("");
        boolean hvOdCount1 = sOdCount1 != null && !sOdCount1.equals("");

        if(hvOdCount0){
            odCount0 = Integer.parseInt(sOdCount0);
        }
        if(hvOdCount1){
            odCount1 = Integer.parseInt(sOdCount1);
        }

        boolean validOdCount = (odCount1 >= odCount0) && odCount1 >= 0 && odCount0 >= 0;

        if(!hvKeyword && !hvStatus && !hvType && !validOdCount){
            // empty search
            ubs = userDB.queryUser();
        }else if(!validOdCount){
            ubs = userDB.searchUser(keyword, status, type, -1, -1);
        }else{
            ubs = userDB.searchUser(keyword, status, type, odCount0, odCount1);
        }
        request.setAttribute("userList", ubs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/profile.jsp");
        rd.forward(request, response);
    }

    public void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userID = Integer.parseInt(request.getParameter("uid"));
        String email = request.getParameter("uemail");
        String pwd = request.getParameter("upwd");
        String type = request.getParameter("utype");
        String fname = request.getParameter("ufname");
        String lname = request.getParameter("ulname");
        String tel = request.getParameter("utel");
        String status = "";
        boolean isDisabled = request.getParameter("udisa") != null;
        if(isDisabled){
            status = "Disabled";
        }else{
            status = "Active";
        }

        UserBean ub = new UserBean();
        ub.setUserID(userID);
        ub.setEmail(email);
        ub.setPassword(pwd);
        ub.setType(type);
        ub.setFirstName(fname);
        ub.setLastName(lname);
        ub.setTel(tel);
        ub.setStatus(status);

        if(userDB.updateUser(ub)){
            response.sendRedirect("/NewReservationSystem/Account");
        }
    }

}
