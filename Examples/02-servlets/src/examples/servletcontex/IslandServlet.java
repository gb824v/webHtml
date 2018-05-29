package examples.servletcontex;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author blecherl
 */
@WebServlet(name = "IslandServlet", urlPatterns = {"/IslandServlet"})
public class IslandServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Object namesListsObj = getServletContext().getAttribute(InitServlet.VISITOR_NAMES_LIST);

        List<String> namesList = null;
        if (namesListsObj != null && namesListsObj instanceof List) {
            namesList = (List<String>) namesListsObj;
        }

        PrintWriter out = response.getWriter();
//        try {
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet IslandServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
            if (namesList == null || namesList.isEmpty()) {
                out.println("<h1 style='color: red'>No one is on the Island</h1>");
            } else {
                out.println("<ul>");
                for (String name : namesList) {
                    out.println("<li style='color: red'>" + name + " is on the Island</li>");
                }
                out.println("</ul>");
            }

//            out.println("<p/>");
            out.println("<a href='InitServlet'>Go Back!!!</a>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
            //if you intende to include a servlet respone in your code
            //DO NOT CLOSE THE RESPONSE WRITER IN THE INCLUDED SERVLER !!!
//            out.close();
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
    }// </editor-fold>
}
