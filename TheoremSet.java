import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TheoremSet {
	
	private HashMap<String, Expression> theorems = new HashMap<String, Expression> ();
	private HashMap<Expression, Expression> expCompare = new HashMap<Expression, Expression> ();
	private static final List<Character> operators = Arrays.asList('&', '|', '=', '~');
	
	public TheoremSet ( ) {
	} 
	
	public Expression put (String theoremName, Expression theorem) {
		return theorems.put(theoremName, theorem);
	}
	
	public Expression get (String theoremName) {
		return theorems.get(theoremName);
	}
	
	public boolean equals (String theoremName, Expression e) {
		/*We compare the sizes of the expressions in order to quickly tell whether the given expression is
		 * more general than the theorem. For example, if the given expression is (p=>p) and the theorem is
		 * ((a&b)=>(a&b)), that would not be a valid use of the theorem. */
		if (this.get(theoremName).size() > e.size()) {
			return false;
		}
		
		//call upon helper!!
		boolean output = equalsHelper(this.get(theoremName), e);
		
		// reset the mapping
		expCompare = new HashMap<Expression, Expression> ();
		
		return output;
	}

	public boolean equalsHelper (Expression theorem, Expression e) {
		// base case
		if (theorem == null || e == null) {
			return true;
		} 
		
		/*  The given expression must contain at least all of the operators in the theorem. This if statement checks that all of
		 * the theorem's operators are present in the given expression.
		*/
		if (operators.contains(theorem.getRoot()) && operators.contains(e.getRoot()) && (theorem.getRoot() != (e.getRoot()))) {
			return false;
		}
		
		// Checks if a mapping exists from the variable in the theorem to variable or larger expression in the given expression
		for (Expression exp : expCompare.keySet()) {
			if (exp.equals(theorem)) {
				
				if (e.equals(expCompare.get(exp))) {
					
					return true;
				} else {
					
					//if this mapping fails, then it is not a valid use of the theorem
					return false;
				}
			}
		}
		
		// If the mapping didn't exist, create one
		expCompare.put(theorem, e);

		// Call recursively on the left and right nodes of the expressions
		return equalsHelper(theorem.getLeft(), e.getLeft()) && equalsHelper(theorem.getRight(), e.getRight()); 
	}
	
	public HashMap<String, Expression> getTheorems() {
		return theorems;
	}
}
