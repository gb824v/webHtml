package examples.jquery;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author blecherl
 */
@WebServlet(name = "ColorAjaxResponse", urlPatterns = {"/ColorAjaxResponse"})
public class ColorAjaxResponse extends HttpServlet {

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
//	response.setContentType("text/html;charset=UTF-8");
	response.setContentType("application/json;charset=UTF-8");

	try (PrintWriter out = response.getWriter()) {
	    ColorResponse[] boxes = new ColorResponse[new Random().nextInt(10)];
	    for (int i = 0; i < boxes.length; i++) {
		ColorResponse newBox = null;
		int num = new Random().nextInt(3);
		switch (num) {
		    case 0: {
			newBox = new ColorResponse(100, "green");
			break;
		    }
		    case 1: {
			newBox = new ColorResponse(200, "blue");
			break;
		    }
		    case 2: {
			newBox = new ColorResponse(300, "red");
			break;
		    }
		}
		boxes[i] = newBox;
	    }
	    Gson gson = new Gson();
	    String json = gson.toJson(boxes);
	    System.out.println(json);
	    out.print(json);
	}
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

    class ColorResponse {

	int height;
	String color;

	public ColorResponse(int height, String color) {
	    this.height = height;
	    this.color = color;
	}
    }
}