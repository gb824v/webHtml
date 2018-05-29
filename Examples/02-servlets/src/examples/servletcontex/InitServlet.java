package examples.servletcontex;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
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
@WebServlet(name = "InitServlet", urlPatterns = {"/InitServlet"})
public class InitServlet extends HttpServlet {

    private static final String VISITOR_NAME_PARAMETER = "name";
    static final String VISITOR_NAMES_LIST = "namesList";
    private static final String USER_ACTION_PARAMETER = "action";

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

        String userAction = request.getParameter(USER_ACTION_PARAMETER);
        if (userAction == null) {
            userAction = "";
        }
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InitServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Visitor Name Input Servlet</h1>");

            switch (userAction) {
                case "enterAction": {
                    //get the name from the HTTP request (the form the user filled and submitted)
                    //on the first call this parameter will be null since the user didn't have the form
                    String visitorNameFromParameter = request.getParameter(VISITOR_NAME_PARAMETER);
                    //String hiddenNameField = request.getParameter("hiddenNameField");

                    addVisitorToVisitorNamesList(out, visitorNameFromParameter);
                    break;
                }
                case "includeAction": {
                    //DO NOT CLOSE THE RESPONSE WRITER IN THE INCLUDED SERVLER !!!
                    //SEE IslandServlet.JAVA for detailes
                    writeVisitorNameInputHTMLCode(out, "");
                    out.println("<div style='border: solid 3px'>");
                    getServletContext().getRequestDispatcher("/IslandServlet").include(request, response);
                    out.println("</div>");
                    break;
                }
                case "forwardAction": {
                    getServletContext().getRequestDispatcher("/IslandServlet").forward(request, response);
                    break;
                }
                default: {
                    writeVisitorNameInputHTMLCode(out, "");
                    break;
                }
            }
            out.println("<a href='IslandServlet'>Go to the Island</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void addVisitorToVisitorNamesList(final PrintWriter out, String visitorName) {
        //get a list of names from the Servlet Context
        List<String> visitorsNames = getVisitorsNamesListFromContext();

        //if so - do not add is again and print an error message
        //(print the form as well in order to allow the user to enter another name)
        if (visitorsNames.contains(visitorName)) {
            //print the form that will be returned to the user
            writeVisitorNameInputHTMLCode(out, visitorName);
            printErrorLine(out, visitorName + " is already on the Island");
        } else {
            //add the name to the list - modifying the list changes it
            visitorsNames.add(visitorName);
            //print the form that will be returned to the user
            writeVisitorNameInputHTMLCode(out, "");
            printSuccessLine(out, visitorName + " has entered the Island");
        }
    }

    private void printErrorLine(final PrintWriter out, String message) {
        out.println("<h2 style='color: red'>" + message + "</h2>");
    }

    private void printSuccessLine(final PrintWriter out, String message) {
        out.println("<h2 style='color: green'>" + message + "</h2>");
    }

    private List<String> getVisitorsNamesListFromContext() {
        //on the first call the value will be null
        Object nameListFromContextObj = getServletContext().getAttribute(VISITOR_NAMES_LIST);

        //if there is no value for 'nameList' - create a new list
        //and put it in the Servlet Context (this means to put a reference to the list
        //in the Servlet Context)
        if (nameListFromContextObj == null) {
            getServletContext().setAttribute(VISITOR_NAMES_LIST, new LinkedList<>());
            nameListFromContextObj = getServletContext().getAttribute(VISITOR_NAMES_LIST);
        }

        //now that we are sure that there is a list in the Servlet Context, get it
        //and cast it from Object ot a List<String>
        return (List<String>) nameListFromContextObj;
    }

    private void writeVisitorNameInputHTMLCode(PrintWriter out, String nameValue) {
        out.println("<form action='InitServlet' method='post'>");
        out.println("Enter Name: <input type='text' name='" + VISITOR_NAME_PARAMETER + "' value='" + nameValue + "'/>");
        out.println("<p>");
        out.println("<input type='radio' name='" + USER_ACTION_PARAMETER + "' value='enterAction' checked/>Enter The Island<br/>");
        out.println("<input type='radio' name='" + USER_ACTION_PARAMETER + "' value='includeAction'/>Include The Island List<br/>");
        out.println("<input type='radio' name='" + USER_ACTION_PARAMETER + "' value='forwardAction'/>Forward To The Island<br/>");
        out.println("</p>");

        out.println("<input type='submit' value='Submit Name'/>");
        out.println("</form>");
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
