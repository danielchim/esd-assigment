//package res.tag;
//
//import res.bean.EquipBean;
//import res.bean.RecordBean;
//
//import javax.servlet.jsp.JspWriter;
//import javax.servlet.jsp.PageContext;
//import javax.servlet.jsp.tagext.SimpleTagSupport;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class Reservation extends SimpleTagSupport {
//    // define a variable customers as ArrayList type
//    ArrayList<RecordBean> reservationList = null;
//    // define the tagType as String
//    String tagType;
//    // define the setter method for customers and tagType
//    public void setReservationList(ArrayList reservationList) {
//        this.reservationList = reservationList;
//    }
//    public void setTagType(String tagType) {
//        this.tagType = tagType;
//    }
//    @Override
//    public void doTag() {
//        try {
//            PageContext pageContext = (PageContext) getJspContext();
//            JspWriter out = pageContext.getOut();
//            for (int i = 0; i < customers.size(); i++) {
//                RecordBean cb = (RecordBean) customers.get(i);
//                if(reservationList.size() > 0){
//                    for(RecordBean data : reservationList){
//                        out.println("<tr>");
//                        out.println("<td>" + data.getRecordID() + "</td>");
//                        out.println("<td>" + data.getItemName() + "</td>");
//                        out.println("<td>" + data.getStatus() + "</td>");
//                        out.println("<td>" + +data.getQuantity() +"</td>");
//                        out.println("<td> <button type=\"button\" class=\"btn btn-primary\" data-toggle=\"modal\" data-target=\"#editReservationModal\" equipName=\""+ data.getItemName() + "\" quantity=" + data.getQuantity() + " userID=" + data.getUserID() + " recordID=" + data.getRecordID() + ">Edit</button><button type=\"button\" class=\"btn btn-danger\" style=\"margin-left: 5%\" data-toggle=\"modal\" data-target=\"#deleteReservationModal\" recordID=" + data.getRecordID() + ">Delete</button>\n</td></tr>");
//                    }
//                }else{
//                    out.println("<tr><td colspan='5'>No data<td></tr>");
//                }
//            }
//        } catch (IOException ioe) {
//            System.out.println("Error generating prime: " + ioe);
//        }
//    }
//}
