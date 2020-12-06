package servlet;

import bean.CustomerBean;
import db.CustomerDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HandleEdit",urlPatterns = {"/handleEdit"})
public class HandleEdit extends HttpServlet {
    //1.  define db object
    //2.  define init method
    private CustomerDB db;
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new CustomerDB(dbUrl, dbUser, dbPassword);
    }
    //2.1 doGet method, in which call processRequest
    //2.2 doPost method, in which call processRequest
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 3.  get the parameter, action, from users
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String tel = request.getParameter("tel");
        String age = request.getParameter("age");
        String action = request.getParameter("action");
        // 4. if action equals to “add”
        if(action.equals("add")) {
            // 4.1 call  the database operations  db.addRecord(id, name, tel, age);
            db.addRecord(id, name, tel, Integer.parseInt(age));
            // redirect the result
            response.sendRedirect("handleCustomer?action=list");
        }else if(action.equals("edit")) {
            CustomerBean cb = new CustomerBean(id,name,tel,Integer.parseInt(age));
            db.editRecord(cb);
            // redirect the result
            response.sendRedirect("handleCustomer?action=list");
        } else {
        PrintWriter out = response.getWriter();
        out.println("No such action!!!");
    }
}

}
