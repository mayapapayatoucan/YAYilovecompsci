import java.util.*;

public class Expression {

	private static final List<Character> operators = Arrays.asList('&', '|', '=');

	private Character root;
	private Expression left;
	private Expression right;

	public Expression (String s) throws IllegalLineException {

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

		int nest = 0;
		//Search for operators.
		for (int i = 0; i < s.length(); i++) {

			// Keep track of inner parentheses in case there is a missing/extra parenthesis
			if (s.charAt(i) == '(') {
				nest++;
			}
			if (s.charAt(i) == ')') {
				nest--;
			}

			// Found an operator
			if (nest == 0 && operators.contains(s.charAt(i))) {

				// Check its operands
				if (i == 0 || i == s.length() - 1) {
					throw new IllegalLineException("Not enough operands.");
				}

				// Check what the operator is
				switch (s.charAt(i)) {

					case '=':
						// Must be =>
						if (s.charAt(i+1) != '>') {
							throw new IllegalLineException("Operator is not valid.");
						}
						
						// Make into an Expression and exit loop
						root = '=';
						left = new Expression(s.substring(0, i));
						right = new Expression(s.substring(i+2, s.length()));
						return;

					case '&': case '|':
						// Must be between operands
						if (i == 0 || i == s.length()){
							throw new IllegalLineException("Improper operator location.");

						}
						
						// Make into an Expression and exit loop
						root = s.charAt(i);
						left = new Expression(s.substring(0, i));
						right = new Expression(s.substring(i + 1, s.length()));
						return;
					}	
				}
		}
		// If gone through the entire loop without finding an operator, throw an exception
		throw new IllegalLineException("No valid operators.");

	}
	
	// Separate constructor used internally and for testing purposes
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
	
	// Given an expression, this returns the negation of that expression (for example, p becomes ~p)
	public Expression negate ( ) {
		Expression temp = new Expression(root, left, right);
		return new Expression('~', null, temp);
	}
	
	// Returns true if every node is equal to the corresponding node in the Expression exp
	public boolean equals (Expression exp) {
		return equalsHelper(this, exp);
	}
	
	public static boolean equalsHelper(Expression exp1, Expression exp2) {
		// base cases
		if (exp1 == null && exp2 == null) {
			return true;
		}
		
		if (exp1 == null || exp2 == null) {
			return false;
		}

		// Check if the chars at both roots are equal
		if (exp1.getRoot() != exp2.getRoot()) {
			return false;
		}
		// Recursive call on left and right
		return equalsHelper(exp1.getLeft(), exp2.getLeft()) && equalsHelper(exp1.getRight(), exp2.getRight());
	}

	
	public boolean isLeaf ( ) {
		if ((left == null) && (right == null)) {
			return true;
		}
		return false;
	}

	// Returns the number of nodes in the tree
	public int size () {
		// base case
		if (root == null) {
			return 0;
		} else
			// base case
			if ((left == null ) && (right == null)) {
				return 1;
			// if no left, look only at right
			} else if (left == null) {
				return right.size() + 1;
			// if no right, look only at left
			} else if (right == null) {
				return left.size() + 1;
			// recursive call on both left and right
			} else {
				return left.size() + right.size() + 1;
			}
	}
	
	// Prints a visual representation of the Expression object as a tree
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
}
