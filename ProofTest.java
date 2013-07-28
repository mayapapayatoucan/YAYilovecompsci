import static org.junit.Assert.*;

import org.junit.Test;


public class ProofTest {
	
	//all expressions are assumed to be valid expressions since the expression class should catch those errors
	
	//The tests in onlineExampleProofs are taken from the example proofs on the project 2 specs.
	@Test
	public void workingProofs( ) {
		try{
		/*p1 is the following proof:
		 * 1 show (p=>p)
		 * 2 assume p
		 * 3 ic 2 (p=>p)
		 * this simply tests ic, line numbering in a simple proof, and the correct completion of a simple proof.
		 */
		Proof p1 = new Proof(null);
		assertEquals(1, p1.getLine());
		p1.extendProof("show (p=>p)");
		assertEquals(2, p1.getLine());
		p1.extendProof("assume p");
		assertEquals(3, p1.getLine());
		p1.extendProof("ic 2 (p=>p)");
		assertTrue(p1.isComplete());
		System.out.println("P1 passed! ONWARDS TO p2!");
		
		
		/* p2 is the following proof:
		 * 1  show (p=>(~p=>q))
		 * 2  assume p
		 * 3  show (~p=>q)
		 * 3.1  assume ~p
		 * 3.2  co 2 3.1 (~p=>q)
		 * 4    ic 3 (p=>(~p=>q))
		 * This tests the correctness of our line numbering with subproofs.
		 * This also test that our subproofs end correctly
		 */
		Proof p2 = new Proof(null);
		assertEquals(1, p2.getLine());
		p2.extendProof("show (p=>(~p=>q))");
		assertEquals(2, p2.getLine());
		p2.extendProof("assume p");
		assertEquals(3, p2.getLine());
		p2.extendProof("show (~p=>q)");
		assertTrue(p2.getSubProof().globalLine().toString().equals("3.1"));
		p2.extendProof("assume ~p");
		assertTrue(p2.getSubProof().globalLine().toString().equals("3.2"));
		p2.extendProof("co 2 3.1 (~p=>q)");
		// below tests whether the subproof correctly eliminates itself at this point.
		assertTrue(p2.getSubProof() == null);
		assertEquals(4, p2.getLine());
		p2.extendProof("ic 3 (p=>(~p=>q))");
		assertTrue(p2.isComplete());
		System.out.println("p3 passed! ONWARDS to p4!!!");
		
		
		/* p3 is the following proof:
		 * 1  show (((p=>q)=>q)=>((q=>p)=>p))
		 * 2  assume ((p=>q)=>q)
		 * 3  show ((q=>p)=>p)
		 * 3.1  assume (q=>p)
		 * 3.2  show p
		 * 3.2.1  assume ~p
		 * 3.2.2  mt 3.2.1 3.1 ~q
		 * 3.2.3  mt 2 3.2.2 ~(p=>q)
		 * 3.2.4  show (p=>q)
		 * 3.2.4.1  assume p
		 * 3.2.4.2  co 3.2.4.1 3.2.1 (p=>q)
		 * 3.2.5  co 3.2.4 3.2.3 p
		 * 3.3  ic 3.2 ((q=>p)=>p)
		 * 4  ic 3 (((p=>q)=>q)=>((q=>p)=>p))
		 */
		Proof p3 = new Proof(null);
		p3.extendProof("show (((p=>q)=>q)=>((q=>p)=>p))");
		p3.extendProof("assume ((p=>q)=>q)");
		p3.extendProof("show ((q=>p)=>p)");
		assertTrue(p3.getSubProof() != null);
		assertTrue(p3.getSubProof().globalLine().toString().equals("3.1"));
		p3.extendProof("assume (q=>p)");
		p3.extendProof("show p");
		assertTrue(p3.getSubProof().getSubProof() != null);
		assertTrue(p3.getSubProof().getSubProof().globalLine().toString().equals("3.2.1"));
		p3.extendProof("assume ~p");
		p3.extendProof("mt 3.2.1 3.1 ~q");
		p3.extendProof("mt 2 3.2.2 ~(p=>q)");
		p3.extendProof("show (p=>q)");
		assertTrue(p3.getSubProof().getSubProof().getSubProof() != null);
		p3.extendProof("assume p");
		p3.extendProof("co 3.2.4.1 3.2.1 (p=>q)");
		assertTrue(p3.getSubProof().getSubProof().getSubProof() == null);
		p3.extendProof("co 3.2.4 3.2.3 p");
		assertTrue(p3.getSubProof().getSubProof() == null);
		p3.extendProof("ic 3.2 ((q=>p)=>p)");
		assertTrue(p3.getSubProof() == null);
		p3.extendProof("ic 3 (((p=>q)=>q)=>((q=>p)=>p))");
		assertTrue(p3.isComplete());
		System.out.println("p3 passed! ONWARDS to p4!!");
		/* p4 is the following proof:
		*1 show (p=>((p=>q)=>q))
		*2 assume p
		*3 show ((p=>q)=>q)
		*3.1 assume (p=>q)
		*3.2 show q
		*3.2.1 mp 2 3.1 q
		*3.3 ic 3.2 ((p=>q)=>q)
		*4 ic 3 (p=>((p=>q)=>q)) */
		Proof p4 = new Proof(null);
		p4.extendProof("show (p=>((p=>q)=>q))");
		p4.extendProof("assume p");
		p4.extendProof("show ((p=>q)=>q)");
		p4.extendProof("assume (p=>q)");
		p4.extendProof("show q");
		p4.extendProof("mp 2 3.1 q");
		assertTrue(p4.getSubProof().getSubProof() == null);
		p4.extendProof("ic 3.2 ((p=>q)=>q)");
		assertTrue(p4.getSubProof() == null);
		p4.extendProof("ic 3 (p=>((p=>q)=>q))");
		assertTrue(p4.isComplete());		
		} catch (IllegalLineException e){
			System.out.println("bad line on p4!");
		} catch (IllegalInferenceException e2) {
			System.out.println("bad inference on p4!");
		}
	}
	
	@Test
	public void testRepeat ( ) {
		/* p1 is the following proof (taken from the online specs):
		 * 1 show (p=>p)
		 * 2 show (p=>p)
		 * 2.1 assume p
		 * 2.2 ic 2.1 (p=>p)
		 * 3 repeat 2 (p=>p)
		 */
		try {
			Proof p1 = new Proof(null);
			p1.extendProof("show (p=>p)");
			p1.extendProof("show (p=>p)");
			p1.extendProof("assume p");
			p1.extendProof("ic 2.1 (p=>p)");
			assertTrue(p1.getSubProof() == null);
			p1.extendProof("repeat 2 (p=>p)");
			assertTrue(p1.isComplete());
		} catch (IllegalLineException e){
			System.out.println("bad line!");
		} catch (IllegalInferenceException e2) {
			System.out.println("bad inference!");
		}
	}
	
	@Test
	public void testPrintAndToString ( ) {
		// tests the print and toString methods. Tests are given print examples from the online specs.
		try{
			/* p1 is the following proof:
			 * 1 show (p=>p)
			 * 2 assume p
			 * 3 show ~p
			 * 3.1 print
			*/
		Proof p1 = new Proof(null);
		p1.extendProof("show (p=>p)");
		p1.extendProof("assume p");
		p1.extendProof("show ~p");
		p1.extendProof("print");
		assertTrue(p1.printIsOK());
		assertTrue(p1.toString().equals("1 show (p=>p) / 2 assume p / 3 show ~p / "));
		//assertEquals();
		} catch (IllegalLineException e) {
			System.out.println("bad line!");
		} catch (IllegalInferenceException e2) {
			System.out.println("bad inference!");
		}
	}
	
	@Test 
	public void testBadLines ( ) {
		// the following are some example proofs from the online specs with bad lines that ultimately give a valid proof.
		try {
			/* p1 is the following proof:
			
			inst.eecs.berkeley.edu/~cs61bl/su13/projects/proj2/SampleProofs/proof01.txt
			
			*/
		Proof p1 = new Proof(null);
		p1.extendProof("show ((a=>b)=>((b=>c)=>(a=>c)))");
		p1.extendProof("assume (a=>b)");
		p1.extendProof("show ((b=>c)=>(a=>c))");
		p1.extendProof("assume (b=>c)");
		p1.extendProof("show (a=>c)");
		p1.extendProof("assume a");
		p1.extendProof("show c");
		p1.extendProof("assume ~c");
		p1.extendProof("mt 3.2.2.1 3.1 ~b");
		p1.extendProof("mt 2 3.2.2.2 ~a");
		p1.extendProof("co 3.2.2.3 3.2.1");
		//Again, I'm not sure how to test the output for this but if you follow the link above you'll se what it is that we are supposed to test.
		p1.extendProof("co 3.2.2.3 3.2.1 c");
		p1.extendProof("ic 3.2.2.4 (a=>c)");
		// There should be another line output here saying "Unable to refer to line 3.2.2.4".
		p1.extendProof("ic 3.2.2 (a=>c)");
		p1.extendProof("ic 3.2 ((b=>c)=>(a=>c))");
		p1.extendProof("ic 3 ((a=>b)=>((b=>c)=>(a=>c)))");
		assertTrue(p1.isComplete());
		} catch (IllegalLineException e) {
	
		} catch (IllegalInferenceException e2) {
			
		}
	}
	
	@Test
	public void theoremProofs ( ) {
		/* The following proof is taken from the online specs and tests the use of theorems in a proof.
			inst.eecs.berkeley.edu/~cs61bl/su13/projects/proj2/SampleProofs/proof04.txt
		*/
		try {
			TheoremSet proof04 = new TheoremSet();
			proof04.put("buildAnd", new Expression("(a=>(b=>(a&b))"));
			proof04.put("demorgan2", new Expression("((~a&~b)=>~(a|b))"));
			Proof p1 = new Proof(proof04);
			p1.extendProof("show ((p|q)=>(~p=>q))");
			p1.extendProof("assume (p|q)");
			p1.extendProof("show (~p=>q)");
			p1.extendProof("assume ~p");
			p1.extendProof("show q");
			p1.extendProof("assume ~q");
			p1.extendProof("buildAnd (~p=>(~p=>(~p&~q)))");
			p1.extendProof("mp 3.1 3.2.2 (~p=>(~p&~q))");
			p1.extendProof("3.2.3 3.2.1 (~p=>~q)");
			p1.extendProof("demorgan2 ((~p&~q)=>~(p|q))");
			p1.extendProof("mp 3.2.4 3.2.5 ~(p|q)");
			p1.extendProof("co 3.2.6 2 q");
			p1.extendProof("ic 3.2 (~p=>q)");
			p1.extendProof("ic 3 ((p|q)=>(~p=>q))");
			assertTrue(p1.isComplete());
			
		/* p2 is the following proof taken from the online specs:
		
		inst.eecs.berkeley.edu/~cs61bl/su13/projects/proj2/SampleProofs/proof05.txt
		
		*/
			TheoremSet proof05 = new TheoremSet();
			proof05.put("dn", new Expression("(~~a=>a)"));
			Proof p2 = new Proof(null);
			p1.extendProof("show (a=>~~a)");
			p1.extendProof("assume a");
			p1.extendProof("show ~~a");
			p1.extendProof("assume ~~~a");
			p1.extendProof("dn (~~~a=>~a)");
			p1.extendProof("mp 3.2 3.1 ~a");
			p1.extendProof("co 2 3.3 ~~a");
			p1.extendProof("ic 3 (a=>~~a)");
			assertTrue(p2.isComplete());
			
		} catch (IllegalLineException e){
			
		} catch (IllegalInferenceException e2){
			
		}
	}
	
	
	}
