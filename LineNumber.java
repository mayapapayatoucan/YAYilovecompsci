import java.util.*;


public class LineNumber {
  private int head;
	private LineNumber rest;
	
	public LineNumber ( ) {
		this.head = 0;
	}
	
	public LineNumber (int head) {
		this.head = head;
	}
	
	public LineNumber (int head, LineNumber rest) {
		this.head = head;
		this.rest = rest;
	}

	//Concatenates a base linenumber and a local linenumber.
	public static LineNumber concat(LineNumber base, int localline) {
	

		if (base == null) {
			return new LineNumber(localline);
		}

		return new LineNumber(base.head, concat(base.rest, localline));

	}

	public LineNumber (String s) throws IllegalLineException {

		try {
				head = Integer.parseInt(s);
				return;
			}
		catch (NumberFormatException e1) {

			String[] split = s.split("\\.", 2);
			int base;
			try {
				base = Integer.parseInt(split[0]);
			}
			catch (NumberFormatException e2) {
				throw new IllegalLineException("Line number is not properly formatted.");
			}

			head = base;
			if (split.length > 1) {
				rest = new LineNumber(split[1]);
			}

		}
		

	}
	
	public int getHead() {
		return head;
	}
	
	public LineNumber getRest() {
		return rest;
	}
	
	public void setRest(LineNumber rest) {
		this.rest = rest;
	}
	
	public void advance () {
		head++;
	}
	
	public String toString() {
		if (rest == null) {
			return Integer.toString(head);
		}
		else {
			return Integer.toString(head) + "." + rest.toString();
		}
	}

	public boolean equals(LineNumber l) {
		if (rest == null) {
			return head == l.getHead();
		}
		return (head == l.getHead() && rest.equals(l.getRest()));
	}
}


