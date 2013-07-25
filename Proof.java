import java.util.*;

public class Proof {
	private HashMap<LineNumber, Expression> lineMap;
	private LineNumber currentLine;
	private Expression toBeProved;
	private Proof subProof;
	private TheoremSet theorems;
	private ArrayList<Expression> provedThings;

  public Proof (TheoremSet theorems) {
	  lineMap = new HashMap<LineNumber, Expression>();
	  currentLine = new LineNumber();
	  this.theorems = theorems;
	}

	public LineNumber nextLineNumber ( ) {
		if (subProof == null) {
			return new LineNumber(currentLine.getHead() + 1);
		}
		return new LineNumber(currentLine.getHead(), subProof.nextLineNumber());
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		
		if (subProof != null) {
			subProof.extendProof(x);
			return;
		}
		
		String[] line = x.split(" ");
		
		switch (line[0]) {
			
			case "show":
				if (toBeProved != null) {
					subProof = new Proof(theorems);
					subProof.extendProof(x);
					return;
					
				}
				toBeProved = new Expression(line[1]);
				break;
				
			case "assume":
				if (toBeProved == null) {
					throw new IllegalLineException("Made assumption before statement to prove.");
				}
				if (toBeProved.getLeft().equals(new Expression(line[1]))) {
					provedThings.add(new Expression(line[1]));
				}
				break;
			
			case "print":
				System.out.println("These have been proven: ");
				for (int i = 0; i < provedThings.size(); i++) {
					Expression.print(provedThings.get(i), "");		// write as string
				}	
			
		}
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
	
	public static boolean isCO (Expression given1, Expression given2) {
		
		return (given1.negate().equals(given2) || given2.negate().equals(given1));
	}
}
