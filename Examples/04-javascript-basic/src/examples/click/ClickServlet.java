package examples.click;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author blecherl
 */
@WebServlet(name = "ClickServlet", urlPatterns = {"/click"})
public class ClickServlet extends HttpServlet {

    private static final int BOARD_SIZE = 10;
    private static final String ROW_PARAMETER = "row";
    private static final String COL_PARAMETER = "col";

    TableCellLocation leftClickLocation;
    TableCellLocation rightClickLocation;

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

        String rowStr = request.getParameter(ROW_PARAMETER);
        String colStr = request.getParameter(COL_PARAMETER);
        String button = request.getParameter("button");

        updateClickLocations(rowStr, colStr, button);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClickServlet</title>");
            out.println("<link rel='stylesheet' type='text/css' href='click/click.css' />");
            out.println("<script src='click/click.js' type='text/javascript'></script>");
            out.println("</head>");
            out.println("<body>");

            //oncontextmenu=\"return false\" - causes the right click menu to not open
            out.println("<table oncontextmenu=\"return false\">");
            for (int col = 1; col <= BOARD_SIZE; col++) {
                out.println("<tr>");
                for (int row = 1; row <= BOARD_SIZE; row++) {
                    String className = "";
                    if (isLeftClickedCell(row, col)) {
                        className = "redborder";
                    } else if (isRightClickedCell(row, col)) {
                        className = "blueborder";
                    }
                    //each td element is set to call 'myclick' on mouse up passing
                    //three parameters - the event parameter comes from the browser
                    //and contains details on the mouse click event;
                    //row and col are generated when the page is generated
                    //and are fixed for each td element
                    out.println("<td class= '" + className + "' row='" + row + "' col='" + col + "'>");
                    out.println("<span>" + col * row + "</span>");
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");

            //type='hidden' means the field is not visible
            //also - notice there is no type='submit' input since this form
            //will be submitted using JavaScript
            out.println("<form id='clickform' method='post' action='click'>");
            out.println("<input id='form_col' type='hidden' name='" + COL_PARAMETER + "'/>");
            out.println("<input id='form_row' type='hidden' name='" + ROW_PARAMETER + "'/>");
            out.println("<input id='button' type='hidden' name='button'/>");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    private void updateClickLocations(String rowStr, String colStr, String button) {
        if (rowStr != null && colStr != null && button != null) {
            //0 or 1 - means left button
            switch (button) {
                case "0":
                case "1":
                    leftClickLocation = new TableCellLocation(rowStr, colStr);
                    break;
                case "2":
                    rightClickLocation = new TableCellLocation(rowStr, colStr);
                    break;
                default:
                    leftClickLocation = null;
                    rightClickLocation = null;
                    break;
            }
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

    private boolean isLeftClickedCell(int row, int col) {
        TableCellLocation currentTableCellLocation = new TableCellLocation(row, col);
        return currentTableCellLocation.equals(leftClickLocation);
    }

    private boolean isRightClickedCell(int row, int col) {
        TableCellLocation currentTableCellLocation = new TableCellLocation(row, col);
        return currentTableCellLocation.equals(rightClickLocation);
    }

    static class TableCellLocation {
        public int row;
        public int col;

        public TableCellLocation(String row, String col) {
            this.row = Integer.parseInt(row);
            this.col = Integer.parseInt(col);
        }

        public TableCellLocation(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.row;
            hash = 29 * hash + this.col;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final TableCellLocation other = (TableCellLocation) obj;
            return this.row == other.row && this.col == other.col;
        }
    }

}
