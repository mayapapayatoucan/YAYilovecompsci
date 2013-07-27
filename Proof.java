import java.util.*;

public class Proof {
	private HashMap<LineNumber, Expression> lines;
	private LineNumber base;
	private int localLine;
	private Expression toBeProved;
	private Proof subProof;
	private TheoremSet theorems;
	private boolean proved;

  	public Proof (TheoremSet theorems) {
	  lines = new HashMap<LineNumber, Expression>();
	  localLine = 1;
	  this.theorems = theorems;
	  proved = false;
	}

	public Proof (TheoremSet theorems, LineNumber base) {
		this(theorems);
		this.base = base;
	}

	//Returns current line number in context of global proof.
	public LineNumber globalLine() {
		return LineNumber.concat(base, localLine);
	}

	//Searches for deepest subproof, and requests global line number.
	public LineNumber nextLineNumber () {
		
		Proof p = this;
		while (p.subProof != null) {
			p = p.subProof;
		}
		return p.globalLine();
	}

	//Checks to see if proof is over. Returns true if it is, and deals with end-of-proof.
	private boolean check (Expression e) {

		if (e.equals(toBeProved)) {
			proved = true;
			return true;
		}
		else {
			return false;
		}
	}

	private void show (Expression e) {
		
		if (toBeProved != null) {
			subProof = new Proof(theorems, globalLine());
			subProof.toBeProved = e;
			subProof.lines = new HashMap<LineNumber, Expression> (lines);

		}
		else {
			toBeProved = e;
			increment();
		}

	}

	private void assume (Expression e) throws IllegalLineException {

		if (toBeProved == null) {
			throw new IllegalLineException("Made assumption before statement to prove.");
		}

		if (e.equals(toBeProved.getLeft()) || e.equals(toBeProved.negate())) {
			lines.put(globalLine(), e);
		}
		increment();
	}


	private void repeat (LineNumber l1, Expression prove) throws IllegalLineException, IllegalInferenceException {

		Expression given1 = lookupLine(l1);

		if (given1.equals(prove)) {
			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}
		else {
			throw new IllegalInferenceException("Not a valid use of repeat.");
		}
	}

	private void ic (LineNumber l1, Expression prove) throws IllegalLineException, IllegalInferenceException {

		//line reference
		Expression given1 = lookupLine(l1);

		//Check if expression to prove is an implication, and that our given expression is on the right.
		if (prove.getRoot() == '=' && given1.equals(prove.getRight())) {
			
			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}

		else {
			throw new IllegalInferenceException("Not a valid use of implication.");
		}

	}

	private void co (LineNumber l1, LineNumber l2, Expression prove) throws IllegalLineException, IllegalInferenceException {


		Expression given1 = lookupLine(l1);
		Expression given2 = lookupLine(l2);
			

		if (given1.equals(given2.negate()) || given2.equals(given1.negate())) {

			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}

		else {
			throw new IllegalInferenceException("Not a valid use of contradiction.");
		}

	}

	private void mp (LineNumber l1, LineNumber l2, Expression prove) throws IllegalLineException, IllegalInferenceException {


		Expression given1 = lookupLine(l1);
		Expression given2 = lookupLine(l2);

		if (given1.getRoot() != '=' && given2.getRoot() != '=') {
			throw new IllegalInferenceException("Not a valid use of modus ponens.");
		}

		if (given2.equals(given1.getLeft()) && prove.equals(given1.getRight()) ||
			given1.equals(given2.getLeft()) && prove.equals(given2.getRight())) {


			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}

		else {
			throw new IllegalInferenceException("Not a valid use of modus ponens.");
		}
	}

	private void mt (LineNumber l1, LineNumber l2, Expression prove) throws IllegalLineException, IllegalInferenceException {

		Expression given1 = lookupLine(l1);
		Expression given2 = lookupLine(l2);

		if (given1.getRoot() != '=' && given2.getRoot() != '=') {
			throw new IllegalInferenceException("Not a valid use of modus tollens.");
		}

		if (given2.equals(given1.getRight().negate()) && prove.equals(given1.getLeft().negate()) ||
			given1.equals(given2.getRight().negate()) && prove.equals(given2.getLeft().negate())) {


			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}

		else {
			throw new IllegalInferenceException("Not a valid use of modus tollens.");
		}
	}

	private void checkTheorem(String theoremName, Expression prove) throws IllegalInferenceException {

		if (theorems.equals(theoremName, prove)) {
			
			if (!check(prove)) {
				addLine(globalLine(), prove);
				increment();
			}
		}

		else {
			throw new IllegalInferenceException("Not a valid use of theorem.");
		}

	}

	private void increment() {
		localLine++;
	}

	private void addLine(LineNumber l, Expression e) {

		lines.put(l, e);
	}

	private void feed (String x) throws IllegalLineException, IllegalInferenceException {

		subProof.extendProof(x);
		if (subProof.isComplete()) {
			//Expression.print(subProof.toBeProved(), "has been proven.");
			//System.out.println("local line: " + localLine);
			//Expression.print(subProof.toBeProved, "subProof.toBeProved");
			lines.put(LineNumber.concat(base, localLine), subProof.toBeProved);
			subProof = null;
			increment();
		}
	}

	//Searches for l in our store of valid lines. Returns expression at that line if found. Throws exception if nothing is found.
	private Expression lookupLine (LineNumber toFind) throws IllegalLineException {

		for (LineNumber l : lines.keySet()) {
			if (l.equals(toFind)) {
				return lines.get(l);
			}
		}
		throw new IllegalLineException("Line number does not correspond to a valid line.");

	}

	public void extendProof (String x) throws IllegalLineException, IllegalInferenceException {
		
		//Separate line by whitespace
		String[] currline = x.split(" ");
		if (currline.length == 0) {
			throw new IllegalLineException("Enter an input");
		}
		String directive = currline[0];
		
		//Feed lines to subproof if currently working on subproof.
		if (subProof != null) {
			feed(x);
		}

		//SHOW
		else if (directive.equals("show")) {

			if (currline.length != 2) {
				throw new IllegalLineException("Invalid number of arguments in show statement.");
			}

			show(new Expression(currline[1]));
		}

		//ASSUME
		else if (directive.equals("assume")) {

			if (currline.length != 2) {
				throw new IllegalLineException("Invalid number of arguments in assume statement.");
			}

			assume(new Expression(currline[1]));
		}

		//IMPLICATION CONSTRUCTION
		else if (directive.equals("ic")) {

			if (currline.length != 3) {
				throw new IllegalLineException("Invalid number of arguments in ic statement.");
			}

			LineNumber given = new LineNumber(currline[1]);
			Expression prove = new Expression(currline[2]);

			ic(given, prove);
		}

		//CONTRADICTION
		else if (directive.equals("co")) {

			if (currline.length != 4) {
				throw new IllegalLineException("Invalid number of arguments in co statement.");
			}

			LineNumber l1 = new LineNumber(currline[1]);
			LineNumber l2 = new LineNumber(currline[2]);
			Expression prove = new Expression(currline[3]);

			co(l1, l2, prove);
		}

		//MODUS PONENS
		else if (directive.equals("mp")) {

			if (currline.length != 4) {
				throw new IllegalLineException("Invalid number of arguments in mp statement.");
			}

			LineNumber l1 = new LineNumber(currline[1]);
			LineNumber l2 = new LineNumber(currline[2]);
			Expression prove = new Expression(currline[3]);

			mp(l1, l2, prove);
		}

		//MODUS TOLLENS
		else if (directive.equals("mt")) {

			if (currline.length != 4) {
				throw new IllegalLineException("Invalid number of arguments in mt statement.");
			}

			LineNumber l1 = new LineNumber(currline[1]);
			LineNumber l2 = new LineNumber(currline[2]);
			Expression prove = new Expression(currline[3]);

			mt(l1, l2, prove);
		}


		//PRINT
		else if (directive.equals("print")) {

			if (currline.length != 1) {

				throw new IllegalLineException("Print does not take arguments.");
			}

		}

		//REPEAT
		else if (directive.equals("repeat")) {

			if (currline.length != 3) {

				throw new IllegalLineException("Invalid number of arguments in repeat statement.");
			}

			LineNumber l1 = new LineNumber(currline[1]);
			Expression prove = new Expression(currline[2]);

			repeat(l1, prove);


		}

		//THEOREMS
		else if (theorems.getTheorems().containsKey(directive)) {

			if (currline.length != 2) {
				throw new IllegalLineException("Invalid number of arguments for theorem.");
			}

			checkTheorem(directive, new Expression(currline[1]));

		}

		//Otherwise, the line is not valid.
		else {

			throw new IllegalLineException("Not a valid line.");
		}
	}


	public String toString ( ) {
		return "";
	}

	public boolean isComplete ( ) {
		return proved;
	}

	public Expression toBeProved() {
		return toBeProved;
	}

}
