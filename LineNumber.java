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

	//Concatenates two LineNumbers.
	public static LineNumber concat(LineNumber l1, LineNumber l2) {
		
		if (l1 == null) {
			return l2;
		}

		while (l1.rest != null) {
			l1 = l1.rest;
		}
		l1.rest = l2;
		return l1;
	}

	public LineNumber (String s) throws IllegalLineException {

		try {
				head = Integer.parseInt(s);
				return;
			}
		catch (NumberFormatException e1) {

			String[] split = s.split("\\.", 2);
			int base;
			System.out.println("head: " + split[0]);
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


