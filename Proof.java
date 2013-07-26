import java.util.*;

public class Proof {
	private HashMap<LineNumber, Expression> lines;
	private LineNumber base;
	private LineNumber currentLine;
	private Expression toBeProved;
	private Proof subProof;
	private TheoremSet theorems;

  	public Proof (TheoremSet theorems) {
	  lines = new HashMap<LineNumber, Expression>();
	  currentLine = new LineNumber();
	  this.theorems = theorems;
	}
	public Proof (TheoremSet theorems, LineNumber base) {
		this(theorems);
		this.base = base;
	}


	public LineNumber nextLineNumber ( ) {
		if (subProof == null) {
			return new LineNumber(currentLine.getHead() + 1);
		}
		return new LineNumber(currentLine.getHead(), subProof.nextLineNumber());
	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		currentLine = nextLineNumber();
		//Feed lines to subproof if currently working on subproof.
		if (subProof != null) {
			subProof.extendProof(x);
		}
		
		//Otherwise...
		else {

			//Separate line by whitespace	
			String[] currline = x.split(" ");
			
			//SHOW
			if (currline[0].equals("show")) {
			
				if (toBeProved != null) {
					subProof = new Proof(theorems, currentLine);
					subProof.toBeProved = new Expression(currline[1]);

					//If subproof is completed...
					if (subProof.isComplete()) {
						currentLine.setRest(null);
						lines.put(currentLine, subProof.toBeProved());
						subProof = null;
					}
				}
				else {
					toBeProved = new Expression(currline[1]);
				}
			}

			else if (currline[0].equals("ic")) {

				
				LineNumber ref = new LineNumber(currline[1]);


				Expression expAtRef = null;

				for (LineNumber l : lines.keySet()) {
					if (ref.equals(l)) {
						expAtRef = lines.get(l);
						break;
					}
				}

				if (expAtRef == null) {
				throw new IllegalLineException("Line number is not valid.");
				}

				Expression toProve = new Expression(currline[2]);
				if (!isIC(expAtRef, toProve)) {
					throw new IllegalInferenceException("Not a valid use of implication construction.");
				}

				lines.put(ref, toProve);
				//for debugging
				System.out.println("Lines size: " + lines.size());

			}
					
			else if (currline[0].equals("assume")) {
				if (toBeProved == null) {
					throw new IllegalLineException("Made assumption before statement to prove.");
				}

			
				if ((new Expression(currline[1])).equals(toBeProved.getLeft())) {
						//for debugging
					System.out.println("put " + currentLine.getHead());

					lines.put(LineNumber.concat(base, currentLine), new Expression(currline[1]));
				}
			} 
			
/*			else if (currline[0].equals("print")) {
				System.out.println("These have been proven: ");
				for (int i = 0; i < lines.size(); i++) {
					Expression.print(lines.getHead(i), "");		// write as string
				}	
			}
*/
		}
		
	}

	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) {
		for (Expression e : lines.values()) {
			if (e.equals(toBeProved)) {
				return true;
			}
		}
		return false;
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

	public Expression toBeProved() {
		return toBeProved;
	}
}
