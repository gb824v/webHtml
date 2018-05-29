package chat.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import chat.Constants;
import chat.logic.ChatManager;
import chat.utils.ServletUtils;
import chat.utils.SessionUtils;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        ChatManager chatManager = ServletUtils.getChatManager(getServletContext());
        String username = SessionUtils.getUsername(request);
        if (username == null) {
            response.sendRedirect("index.html");
        }
        
        int chatVersion = ServletUtils.getIntParameter(request, Constants.CHAT_VERSION_PARAMETER);
        logServerMessage("Server Chat version: " + chatManager.getVersion() + ", User '" + username + "' Chat version: " + chatVersion);

        if (chatVersion > Constants.INT_PARAMETER_ERROR) {
            try (PrintWriter out = response.getWriter()) {
                List<ChatManager.ChatEntry> chatEntries = chatManager.getChatEntries(chatVersion);
                ChatAndVersion cav = new ChatAndVersion(chatEntries, chatManager.getVersion());
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(cav);
                logServerMessage(jsonResponse);
                out.print(jsonResponse);
                out.flush();
            }
        }
    }

    private void logServerMessage(String message){
        System.out.println(message);
    }
    
    class ChatAndVersion {

        final private List<ChatManager.ChatEntry> entries;
        final private int version;

        public ChatAndVersion(List<ChatManager.ChatEntry> entries, int version) {
            this.entries = entries;
            this.version = version;
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
