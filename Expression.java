import java.util.*;

public class Expression {

	private static final List<Character> operators = Arrays.asList('&', '|', '=');

	private char root;
	private Expression left;
	private Expression right;

	public Expression (String s) throws IllegalLineException {

		int nest = 0;

		//Handle empty expression.
		if (s.length() == 0) {
			throw new IllegalLineException("Empty strings are invalid.");
		}

		//Handle single character expression.
		if (s.length() == 1) {
			if (Character.isLowerCase(s.charAt(0))) {
			root = s.charAt(0);
			return;
			}
			else {
				throw new IllegalLineException("Not a valid expression.");
			}
		}

		//Handle NOT expression.
		if (s.charAt(0) == '~') {

			root = '~';
			right = new Expression(s.substring(1));
			return;

		}

		//Check for proper parentheses.
		if (s.charAt(0) != '(' || s.charAt(s.length() - 1) != ')') {
			System.out.println(s);
			throw new IllegalLineException("Not properly parenthesized.");
		}

		//Trim parentheses from string.
		s = s.substring(1, s.length() - 1);

		//Search for operators.
		for (int i = 0; i < s.length(); i++) {

			if (s.charAt(i) == '(') {
				nest++;
			}
			if (s.charAt(i) == ')') {
				nest--;
			}

			if (nest == 0 && operators.contains(s.charAt(i))) {

				if (i == 0 || i == s.length() - 1) {
					throw new IllegalLineException("Not enough operands.");
				}

				switch (s.charAt(i)) {

					case '=':
						if (s.charAt(i+1) != '>') {
							throw new IllegalLineException("Operator is not valid.");
						}

						root = '=';
						left = new Expression(s.substring(0, i));
						right = new Expression(s.substring(i+2, s.length()));
						break;

					case '&': case '|':
						if (i == 0 || i == s.length()){
							throw new IllegalLineException("Improper operator location.");

						}
						root = s.charAt(i);
						left = new Expression(s.substring(0, i));
						right = new Expression(s.substring(i + 1, s.length()));
						break;
					}	
				}


			

		}

	}

	public char getRoot() {

		return root;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
}
