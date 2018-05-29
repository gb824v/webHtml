package examples.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CalcServlet", urlPatterns = "/calc")
public class CalcServlet extends HttpServlet {

	private String param1Name = "x1";
	
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
        String x1ParamStr = request.getParameter(param1Name);
        String x2ParamStr = request.getParameter("x2");
        String result = null;
        String error = null;
        if (x1ParamStr != null && x2ParamStr != null && !x1ParamStr.isEmpty() && !x2ParamStr.isEmpty()) {
            try {
                int x1 = Integer.parseInt(x1ParamStr);
                int x2 = Integer.parseInt(x2ParamStr);
                result = Integer.toString(x1 + x2);
            } catch (NumberFormatException e) {
                error = "Must enter numbers";
            }

        }
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CalcServlet</title>");
            out.println("</head>");
            out.println("<body>");
            if (result != null) {
                String operatorSign = "+";
                out.println("<h2>" + x1ParamStr + operatorSign + x2ParamStr + " = " + result + "</h2>");
            }

            out.println("<form action='calc'>");
            out.println("    <input type='text' name='"+param1Name+"' value='" +x1ParamStr+ "'/>");
            out.println("     +");
            out.println("    <input type='text' name='x2' value='" + x2ParamStr + "'/>");
            out.println("    <input type='submit' value='Sum' />");
            out.println("</form>");
            
            if (error != null) {
                out.println("<h2>Error: " + error + "</h2>");
            }
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
