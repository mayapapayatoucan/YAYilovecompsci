import java.util.*;

public class Expression {

	private static final List<Character> operators = Arrays.asList('&', '|', '=');

	private Character root;
	private Expression left;
	private Expression right;

	public Expression (String s) throws IllegalLineException {

		int nest = 0;
		//Handle empty expression.
		if (s.length() == 0) {
			throw new IllegalLineException("Not enough characters to form a valid expression.");
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
						return;

					case '&': case '|':
						if (i == 0 || i == s.length()){
							throw new IllegalLineException("Improper operator location.");

						}
						root = s.charAt(i);
						left = new Expression(s.substring(0, i));
						right = new Expression(s.substring(i + 1, s.length()));
						return;
					}	
				}

		}
		throw new IllegalLineException("No valid operators.");

	}
	
	public Expression (char myRoot, Expression myLeft, Expression myRight) {
		root = myRoot;
		left = myLeft;
		right = myRight;
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
	
	public Expression negate ( ) {
/*	if (root == '~') {
			return right;
} */
		Expression temp = new Expression(root, left, right);
		return new Expression('~', null, temp);
	}
	
	public boolean equals (Expression exp) {
		return equalsHelper(this, exp);
	}
	
	public static boolean equalsHelper(Expression exp1, Expression exp2) {

		if (exp1 == null && exp2 == null) {
			return true;
		}
		
		if (exp1 == null || exp2 == null) {
			return false;
		}

		if (exp1.getRoot() != exp2.getRoot()) {  // char equals
			return false;
		}
		if (!equalsHelper(exp1.getLeft(), exp2.getLeft()) || !equalsHelper(exp1.getRight(), exp2.getRight())) {
			return false;
		}
		return true;
	}

	
	public boolean isLeaf ( ) {
		if ((left == null) && (right == null)) {
			return true;
		}
		return false;
	}
	
	public static void print (Expression e, String description) {
		System.out.println(description + ":");
		printHelper(e, 0);
	}
	
	private static final String indent1 = "    ";
	
	private static void printHelper (Expression e, int indent) {
		if (e != null) {
			printHelper(e.right, indent+1);
			println (e.root, indent);
			printHelper(e.left, indent+1);
		}
	}
			
	private static void println (Object obj, int indent) {
	    for (int k=0; k<indent; k++) {
	        System.out.print (indent1);
	    }
	    System.out.println (obj);
	}
	
	public int size () {
		if (root == null) {
			return 0;
		} else
			if ((left == null ) && (right == null)) {
				return 1;
			} else if (left == null) {
				return right.size() + 1;
			} else if (right == null) {
				return left.size() + 1;
			} else {
				return left.size() + right.size() + 1;
			}
	}
}
