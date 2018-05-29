package examples.readresource;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReadXMLServlet", urlPatterns = {"/readxml"})
public class ReadXMLServlet extends HttpServlet {

    private final static String XML_PATH = "/resources/world.xml";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String filename = getAbsolutePathOfResource(XML_PATH);
            String filecontent = getResouceContent(XML_PATH);
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReadXMLServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Showing the content of the file: " + filename);
            out.println("</h1>");
            out.println("<xmp>");
            out.println(filecontent);
            out.println("</xmp>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String getAbsolutePathOfResource(String resouce) {
        URL url = this.getClass().getResource(resouce);
        return url != null ? url.getPath() : "?";
    }

    private String getResouceContent(String resource) {
        StringBuilder result = new StringBuilder();
        try (InputStream stream = this.getClass().getResourceAsStream(resource)) {
            Scanner scanner = new Scanner(stream, "UTF-8");
            while (scanner.hasNextLine()){
                result.append(scanner.nextLine()).append("\n\r");
            }
        } catch (Exception exception){
            return "Error: Failed to read file!";
        }
        return result.toString();
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
    }// </editor-fold>

}
