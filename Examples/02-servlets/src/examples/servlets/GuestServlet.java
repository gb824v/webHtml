package examples.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GuestServlet", urlPatterns = "/guestslist")
public class GuestServlet extends HttpServlet {

    private static final String GUEST_NAME_PARAMETER = "guest";
    
    private List<String> guestsList;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String guestName = request.getParameter(GUEST_NAME_PARAMETER);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Guest Servlet Example</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<form action='guestslist' method='post'>");
            out.println("<input type='text' name='"+GUEST_NAME_PARAMETER+"'/>");
            out.println("<input type='submit' value='Add'/>");
            out.println("</form>");
            
//            if (true)
//           throw new RuntimeException ("Screw This !");
            if (shouldAddGuest(guestName)) {
                getGuestList().add(guestName);
            }

            out.println("<ol>");
            for (String name : getGuestList()) {
                out.print("<li>");
                out.print(name);
                out.println("</li>");
            }
            out.println("</ol>");

            out.println("</body>");
            out.println("</html>"); 
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    private boolean shouldAddGuest (String guestName){
        return guestName != null && !guestName.isEmpty();
    }

    private List<String> getGuestList() {
      
        //option #1 - save the guest list as a data member of the servlet
        if (guestsList == null) {
            guestsList = new ArrayList<>();
        }

        //option #2 - save the guest list in the servlet context
        //The Servlet Context is shared between all servlets in the web application
//        List<String> guestsList = (List<String>) getServletContext().getAttribute("guestsList");
//        if (guestsList == null) {
//            guestsList = new ArrayList<>();
//            getServletContext().setAttribute("guestsList", guestsList);
//        }
        //option #3 - save the guest list in the session context
        //The Session Context is unique for each browser that is connected to the server
//        HttpSession session = request.getSession();
//        List<String> guestsList = (List<String>) session.getAttribute("guestsList");
//        if (guestsList == null) {
//            guestsList = new ArrayList<>();
//            session.setAttribute("guestsList", guestsList);
//        }
        return guestsList;
    }
}
