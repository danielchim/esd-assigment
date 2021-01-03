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
import res.db.DBFactory;
import res.db.EquipDB;

/**
 *
 * @author erd25
 */
@WebServlet(name = "InventoryController", urlPatterns = {"/Inventory"})
public class InventoryController extends HttpServlet {
    
    private EquipDB equipDB;
    
    public void init(){
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
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
            out.println("<title>Servlet InventoryController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InventoryController at " + request.getContextPath() + "</h1>");
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
        ArrayList<EquipBean> ebs;
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
            ebs = equipDB.queryEquip();
        }else{
            if((keyword != null || !keyword.equals("")) && (status == null || status.equals(""))){
                // case: only keyword
                ebs = equipDB.searchEquip(keyword, null);
            }else if((keyword == null || keyword.equals("")) && (status != null || !status.equals(""))){
                // case: only status
                ebs = equipDB.searchEquip(status, null);
            }else{
                // case: both keyword and status
                ebs = equipDB.searchEquip(keyword, status);
            }
        }
        request.setAttribute("equipList", ebs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/pages/inventory_management.jsp");
        rd.forward(request, response);
    }
    
    public void doCreate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int equipID = equipDB.getPossibleID();
        equipDB.insertEquip(equipID, request.getParameter("ename"), request.getParameter("edesc"));
        equipDB.insertInventory(equipID, Integer.parseInt(request.getParameter("eq")));
        if(request.getParameter("edisa") != null){
            equipDB.insertDisaEquip(equipID);
        }
        response.sendRedirect("/NewReservationSystem/Inventory");
    }
    
    public void doEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int equipID = Integer.parseInt(request.getParameter("editID"));
        String equipName = request.getParameter("ename");
        int quantity = Integer.parseInt(request.getParameter("eq"));
        String description = request.getParameter("edesc");
        boolean disabled = request.getParameter("edisa") != null;
        EquipBean eb = new EquipBean();
        eb.setEquipID(equipID);
        eb.setEquipName(equipName);
        eb.setQuantity(quantity);
        eb.setDescription(description);
        if(disabled){
            eb.setStatus("Disabled");
        }
        if(equipDB.updateEquip(eb)){
            response.sendRedirect("/NewReservationSystem/Inventory");
        }
    }
    
    public void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int equipID = Integer.parseInt(request.getParameter("delID"));
        if(equipDB.delEquip(equipID)){
            response.sendRedirect("/NewReservationSystem/Inventory");
        }
    }

}
