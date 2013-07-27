import static org.junit.Assert.*;

import org.junit.Test;


public class ProofTest {
	
	//all expressions are assumed to be valid expressions since the expression class should catch those errors
	
	//The tests in onlineExampleProofs are taken from the example proofs on the project 2 specs.
	@Test
	public void onlineExampleProofs( ) {
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
			System.out.println("bad line on p1!");
		} catch (IllegalInferenceException e2) {
			System.out.println("bad inference on p1!");
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
			System.out.println("bad line on p1!");
		} catch (IllegalInferenceException e2) {
			System.out.println("bad inference on p1!");
		}
	}
	
/*	  @Test
		public void testIsModusPonens() {
			try {
				assertTrue(Proof.isMP(new Expression("(p=>q)"), new Expression("p"), new Expression("q")));
				assertTrue(Proof.isMP(new Expression("((p=>q)=>(t=>r))"), new Expression("(p=>q)"), new Expression("(t=>r)")));
				assertTrue(Proof.isMP(new Expression("((p&d)=>q)"), new Expression("(p&d)"), new Expression("q")));
				
				//the test below is logically true, but should not pass this test as IsModusPonies is written right now.
				assertFalse(Proof.isMP(new Expression("(~p|q)"), new Expression("p"), new Expression("q")));
				assertFalse(Proof.isMP(new Expression("((p|q)=>z)"), new Expression("p"), new Expression("z")));
				
				assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("~q"), new Expression("~q")));
				assertFalse(Proof.isMP(new Expression("((p=>q)=>(t=>r))"), new Expression("(t=>r)"), new Expression("(p=>q)")));
				assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("q"), new Expression("q")));
				
			} catch (IllegalLineException e){
			}
		}
	@Test
		public void testIsModusTollens() {
			try {
				assertTrue(Proof.isMT(new Expression("(p=>q)"), new Expression("~q"), new Expression("~p")));
				assertTrue(Proof.isMT(new Expression("((r&s)=>(t|u))"), new Expression("~(t|u)"), new Expression("~(r&s)")));
				assertTrue(Proof.isMT(new Expression("(~(z&x)=>~(h|g))"), new Expression("~~(h|g)"), new Expression("~~(z&x)")));
				assertTrue(Proof.isMT(new Expression("(~(z=>x)=>~(h=>g))"), new Expression("~~(h=>g)"), new Expression("~~(z=>x)")));
				
				
				assertFalse(Proof.isMT(new Expression("(~(z&x)=>~(h|g))"), new Expression("(h|g)"), new Expression("(z&x)")));
				// while the above line is logically valid, for the purposes of our program, it should not equate double negation to no negation
				assertFalse(Proof.isMT(new Expression("(p=>q)"), new Expression("q"), new Expression("~p")));
			} catch (IllegalLineException e){
			}
	
		}
	
	@Test
		public void testisImplicationConstruction() {
			try {			
				assertTrue(Proof.isIC(new Expression("q"), new Expression("(p=>q)")));
				assertTrue(Proof.isIC(new Expression("(p&q)"), new Expression("((q|p)=>(p&q))")));
				assertTrue(Proof.isIC(new Expression("~(q&r)"), new Expression("(p=>~(q&r))")));
				
				assertFalse(Proof.isIC(new Expression("~~(p&q)"), new Expression ("(p&q")));
				// Again, the above case is logically valid but for the purposes of our program, should return false
				assertFalse(Proof.isIC(new Expression("(p&r)"), new Expression("(p=>(p&q)")));
				
			} catch (IllegalLineException e){
			}
	}
	
	@Test
	public void testisContradiction() {
		try {			
			assertTrue(Proof.isCO(new Expression("~p"), new Expression("p")));
			assertTrue(Proof.isCO(new Expression("~(p&q)"), new Expression("(p&q)")));
			assertTrue(Proof.isCO(new Expression("~(p&(q&r))"), new Expression("~~(p&(q&r))")));
			
			assertFalse(Proof.isCO(new Expression("~~p"), new Expression("p")));
			
			assertFalse(Proof.isCO(new Expression("~~~~~p"), new Expression("p")));
			// Again, the above case is logically valid but for the purposes of our program, should return false
			
	
		} catch (IllegalLineException e){
		}
	} */
	
	
	
	}
