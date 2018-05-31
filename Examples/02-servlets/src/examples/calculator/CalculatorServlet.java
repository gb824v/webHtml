package examples.calculator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.calculator.engine.ACTIONS;
import examples.calculator.engine.Calculator;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

	private static final String NUMBER_PARAM = "number";
	private static final String ACTION_PARAM = "action";
	private static final String COMMAND_PARAM = "command";

	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_CHANGE_SIGN = "sign";
	private static final String COMMAND_CALCULATE = "calculate";

	private Calculator calculator = new Calculator();
	private String calculationResult = "0";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		processParameters(request.getParameterMap());
		try (PrintWriter out = response.getWriter()) {
			printForm(out);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void processParameters(Map<String, String[]> parameterMap) {
		if (parameterMap.containsKey(NUMBER_PARAM)) {
			Double number = Double.parseDouble(parameterMap.get(NUMBER_PARAM)[0]);
			calculationResult = calculator.pushNumber(number);
		}
		if (parameterMap.containsKey(ACTION_PARAM)) {
			ACTIONS action = ACTIONS.valueOf(parameterMap.get(ACTION_PARAM)[0]);
			calculationResult = calculator.pushAction(action);
		}
		if (parameterMap.containsKey(COMMAND_PARAM)) {
			switch (parameterMap.get(COMMAND_PARAM)[0]) {
			case COMMAND_CLEAR:
				calculationResult = calculator.clear();
				break;
			case COMMAND_CHANGE_SIGN:
				calculationResult = calculator.changeSign();
				break;
			case COMMAND_CALCULATE:
				calculationResult = calculator.calculate();
				break;
			}
		}
	}

	private void printForm(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Calculator</title>");
		out.println("<link href='calculator.css' rel='stylesheet'/>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Calculator</h1>");
		out.println("<form method='get' action='calculator'>");
		printResult(out);
		out.println(htmlButton(COMMAND_PARAM, COMMAND_CLEAR, "C"));
		out.println(htmlButton(COMMAND_PARAM, COMMAND_CHANGE_SIGN, "±"));
		printNumbers(out);
		printActions(out);
		out.println(htmlButton(COMMAND_PARAM, COMMAND_CALCULATE, "="));
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	void printResult(PrintWriter out) {
		out.println("<h2 class='result'>" + calculationResult + "</h2>");
	}

	void printNumbers(PrintWriter out) {
		for (int number = 0; number < 10; number++) {
			out.println(renderNumber(number));
		}
	}

	void printActions(PrintWriter out) {
		for (ACTIONS action : ACTIONS.values()) {
			out.println(renderAction(action));
		}
	}

	String renderNumber(int number) {
		return htmlButton(NUMBER_PARAM, number + "", number + "");
	}

	String renderAction(ACTIONS action) {
		return htmlButton(ACTION_PARAM, action.toString(), action.sign);
	}

	String htmlButton(String name, String value, String label) {
		return "<button formaction='calculator' name='" + name + "' value='" + value + "'>" + label + "</button>";
	}
}
