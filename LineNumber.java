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
}


