package examples.poll;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author blecherl
 */
@WebServlet(name = "PollServlet", urlPatterns = {"/Poll"})
public class PollServlet extends HttpServlet {

    private static final String CHOICE_PARAMETER_NAME = "choice";
    private PollLogic poll;

    @Override
    public void init() throws ServletException {
        super.init();
        poll = new PollLogic(new String[]{"John", "George", "Paul", "Ringo"});
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
        String parameterChoice = request.getParameter(CHOICE_PARAMETER_NAME);
        String previouseSavedChoice = getSavedChoice(request);

        try (PrintWriter out = response.getWriter()) {
            out.println("<html>");
            out.println("   <head>");
            out.println("       <title>Beatles Poll</title>");
            out.println("   </head>");

            //prettify servlet using bootstrap
//            out.println("<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\">");
            out.println("\t<body>");
            out.println("\t\t<div class=\"container\">");

            PollAction action = PollAction.calculateAction(parameterChoice, previouseSavedChoice);
            switch (action) {
                case SHOW_POLL:
                    writePollForm(out);
                    break;
                case CAST_VOTE:
                    saveChoice(request, response, parameterChoice);
                    writeUserChoice(out, parameterChoice);
                    addChoiceToPoll(parameterChoice);
                    writePollResults(out);
                    break;
                case SHOW_RESULTS:
                    writeUserChoice(out, previouseSavedChoice);
                    writePollResults(out);
                    break;
                case ERROR:
                    writeErrorMessage(out, previouseSavedChoice);
                    writePollResults(out);
                    break;
            }
            out.println("\t\t</div>");
            out.println("\t</body>");
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

    private String getSavedChoice(HttpServletRequest request) {
        String cookieChoice = null;
	//option #1 - use cookies
//	if (request.getCookies() != null) {
//	    for (Cookie cookie : request.getCookies()) {
//		if (cookie.getName().contains(CHOICE_PARAMETER_NAME)) {
//		    cookieChoice = cookie.getValue();
//		}
//	    }
//	}

        //option #2 - use session
        cookieChoice = (String) 
                request.getSession().
                        getAttribute(CHOICE_PARAMETER_NAME);

        return cookieChoice;
    }

    private void saveChoice(HttpServletRequest request, HttpServletResponse response, String choice) {
	//option #1 - use cookies
//	response.addCookie(new Cookie(CHOICE_PARAMETER_NAME, choice));

        //option #2 - use session
        request.getSession(true).
                setAttribute(CHOICE_PARAMETER_NAME, choice);
    }

    private void writePollForm(PrintWriter out) {
        out.println("<h1>Which is your favorite?</h1>");
        out.println("<form method='GET' action='Poll'>");
        for (String choice : poll.getPollChoices()) {
            out.println("<input type='radio' name='" + CHOICE_PARAMETER_NAME + "' value='" + choice + "'>" + choice + "</radio><br>");
        }
        out.println("<input type='submit'/>");
        out.println("</form>");
    }

    private void writePollResults(PrintWriter out) {
        out.println("<h1>Poll Results</h1>");
        out.println("<p>");
        for (Map.Entry<String, Integer> choiceEntry : poll.getPollData().entrySet()) {
            String choice = choiceEntry.getKey();
            int choiceCount = choiceEntry.getValue();
            out.println("<span>" + choice + "</span><span>: " + formatVotes(choiceCount) + "</span><br>");

            //prettify servlet using bootstrap
//            int precent = poll.getNumberOfVotes() > 0 ? Math.round(choiceCount * 100 / poll.getNumberOfVotes()) : 0;
//            out.println("<span>" + choice + "</span><span>: </span>");
//            out.println("<div class=\"progress progress-striped active\">");
//            out.println("<div class=\"bar\" style=\"width: "+precent+"%;\"></div></div>");
        }
    }

    private String formatVotes(int votesCount) {
        return votesCount == 0 ? "no votes" : votesCount == 1 ? "1 vote" : votesCount + " votes";
    }

    private void writeUserChoice(PrintWriter out, String choice) {
        out.println("<h1>Your choice was " + choice + "</h1>");
    }

    private void writeErrorMessage(PrintWriter out, String choice) {
        out.println("<h1 style=\"color: red\">You cannot fill the poll more than 1 time!</h1>");
        out.println("<h2>Your choice was " + choice + "</h2>");
    }

    private void addChoiceToPoll(String choice) {
        poll.addChoice(choice);
    }
}
