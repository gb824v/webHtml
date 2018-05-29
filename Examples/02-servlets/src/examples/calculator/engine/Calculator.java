package examples.calculator.engine;

import java.text.DecimalFormat;

public class Calculator {

	private static final String PATTERN = "###,###,###.#######";
	private static final DecimalFormat decimalFormat = new DecimalFormat(PATTERN);

	private Double firstNumber = 0.0;
	private Double secondNumber = 0.0;
	private ACTIONS action;

	public String pushNumber(Double number) {
		if (action == null) {
			firstNumber = firstNumber * 10 + number;
			return formatDouble(firstNumber);
		} else {
			secondNumber = secondNumber * 10 + number;
			return formatCalculation();
		}
	}

	public String pushAction(ACTIONS action) {
		this.action = action;
		return String.join(" ", formatDouble(firstNumber), action.sign);
	}

	public String calculate() {
		if (action == null) {
			return "0";
		}
		Double result = action.caluclate(firstNumber, secondNumber);
		String resultStr = String.join(" = ", formatCalculation(), formatDouble(result));
		clear();
		return resultStr;
	}

	public String clear() {
		firstNumber = 0.0;
		secondNumber = 0.0;
		action = null;
		return "0";
	}

	public String changeSign() {
		if (action == null) {
			firstNumber = -firstNumber;
			return firstNumber.toString();
		} else {
			secondNumber = -secondNumber;
			return formatCalculation();
		}
	}

	private String formatDouble(Double number) {
		return decimalFormat.format(number);
	}

	private String formatCalculation() {
		return String.join(" ", formatDouble(firstNumber), action.sign, formatDouble(secondNumber));
	}
}