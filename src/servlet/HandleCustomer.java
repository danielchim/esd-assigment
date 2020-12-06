package servlet;

import bean.CustomerBean;
import db.CustomerDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "HandleCustomer",urlPatterns = {"/handleCustomer"})
public class HandleCustomer extends HttpServlet {
    private CustomerDB db;
    public void init() {
        //1.  obtain the context-param, dbUser, dbPassword and dbUrl which defined in web.xml
        //2.  create a new db object  with the parameter
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new CustomerDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer
            ArrayList<CustomerBean> customers = db.queryCust();
            // set the result into the attribute
            request.setAttribute("customers", customers);
            // redirect the result to the listCustomers.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/listCustomers.jsp");
            rd.forward(request, response);
        }else if ("delete".equalsIgnoreCase(action)) {
            // get parameter, id, from the request
            String id = request.getParameter("id");
            if (id != null) {
                // call delete record method in the database
                db.delRecord(id);
                // redirect the result to list action
                response.sendRedirect("handleCustomer?action=list");
            }
        }else if ("getEditCustomer".equalsIgnoreCase(action)) {
            // obtain the parameter id;
            String id = request.getParameter("id");
            if (id != null) {
                // call  query db to get retrieve for a customer with the given id
                CustomerBean customers = db.getRecord(id);
                // set the customer as attribute in request scope
                request.setAttribute("c", customers);
                // forward the result to the editCustomer.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editCustomer.jsp");
                rd.forward(request, response);
            }
        } else if ("search".equalsIgnoreCase(action)) {
            // obtain the parameter id;
            String name = request.getParameter("name");
            if (name != null) {
                ArrayList<CustomerBean> customers;
                // call queryByName from db to retrieve for customers with the given name
                customers = db.queryCustByName(name);
                // set the result into the attribute in request
                request.setAttribute("customers", customers);
                // forward the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/listCustomers.jsp");
                rd.forward(request, response);
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }

}
