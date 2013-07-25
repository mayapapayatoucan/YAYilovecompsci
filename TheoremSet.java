import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TheoremSet extends HashMap<String, Expression> {
  
	private static final List<Character> operators = Arrays.asList('&', '|', '=', '~');
	private HashMap<Expression, Expression> expCompare = new HashMap<Expression, Expression> ();
	
	public TheoremSet ( ) {
		super();
	}
	
	public TheoremSet (String theoremName, Expression theorem) {
		// need to fix
		super();
	}
	
	public boolean equals (String theoremName, Expression e) {
		return equalsHelper(e, this.get(theoremName));
	}

	public boolean equalsHelper (Expression e, Expression theorem) {
		if (theorem == null || e == null) {
			System.out.println("reached null");
			return true;
		}
		if (operators.contains(theorem.getRoot()) && operators.contains(e.getRoot()) && (theorem.getRoot() != (e.getRoot()))) {
			System.out.println("reached invalid operator: " + theorem.getRoot());
			return false;
		}
		if (expCompare.containsKey(theorem.getRoot())) {
			if (expCompare.get(theorem).equals(e)) {
				System.out.println("reached correct variable");
				return true;
			} else {
				System.out.println("reached incorrect variable");
				return false;
			}
		}
		expCompare.put(theorem, e);
		System.out.println("reached recursive call");
		return equalsHelper(e.getLeft(), theorem.getLeft()) && equalsHelper(e.getRight(), theorem.getRight()); 
	}
}
