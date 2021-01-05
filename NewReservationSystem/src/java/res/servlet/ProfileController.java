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
 * @author erd25
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
    private UserDB userDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userDB = new UserDB(dbUrl, dbUser, dbPassword);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "search";
        }
        if ("search".equals(action)) {
            doSearch(request, response);
        } else if ("update".equals(action)) {
            doEdit(request, response);
        } else if ("filter".equals(action)) {
            doSearch(request, response);
        } else if ("logout".equals(action)) {

        } else {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
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

    public void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserBean ubs;
        UserBean targetUserID = (UserBean) request.getSession().getAttribute("userInfo");
        ubs = userDB.profileSearchUser(targetUserID.getUserID());
        request.setAttribute("userInfo", ubs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/profile.jsp");
        rd.forward(request, response);
    }

    public void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("userPasswd");
        String email = request.getParameter("userMail");
        String tel = request.getParameter("userPhone");
        String status = "";
        boolean isDisabled = request.getParameter("udisa") != null;
        if (isDisabled) {
            status = "Disabled";
        } else {
            status = "Active";
        }

        UserBean ub = new UserBean();
        ub.setUserID(userID);
        ub.setPassword(password);
        ub.setType("student");
        ub.setFirstName(firstName);
        ub.setLastName(lastName);
        ub.setTel(tel);
        ub.setEmail(email);
        ub.setStatus(status);

        if (userDB.updateUser(ub)) {
            response.sendRedirect("/NewReservationSystem/profile");
        }
    }

}
