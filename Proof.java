import java.util.*;

public class Proof {

  public Proof (TheoremSet theorems) {
	}

	public LineNumber nextLineNumber ( ) {
		return null;
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
	}

	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) {
		return true;
	}
	
	public static boolean isMP (Expression imp, Expression given, Expression proven) {
		return imp.getLeft().equals(given) && imp.getRight().equals(proven);
	}
	
	public static boolean isMT (Expression imp, Expression given, Expression proven) {
		
		return imp.getLeft().negate().equals(proven) && imp.getRight().negate().equals(given);
	}
	
	public static boolean isIC (Expression given, Expression proven) {
		
		if (proven.getRoot() != '=') {
			return false;
		}
		return proven.getRight().equals(given);
	}
	
	public static boolean isC (Expression given1, Expression given2) {
		
		return (given1.negate().equals(given2) || given2.negate().equals(given1));
	}
}
