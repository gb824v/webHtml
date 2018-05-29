package examples.calculator.engine;

public enum ACTIONS implements Calculate {
	PLUS		("+", (x1, x2) -> x1 + x2),
	MINUS		("-", (x1, x2) -> x1 - x2),
	MULTIPLY	("x", (x1, x2) -> x1 * x2), 
	DIVISION	("/", (x1, x2) -> x1 / x2);

	public String sign;
	Calculate calculator;

	private ACTIONS(String sign, Calculate calculator) {
		this.sign = sign;
		this.calculator = calculator;
	}

	@Override
	public Double caluclate(Double n1, Double n2) {
		return calculator.caluclate(n1, n2);
	}
}
