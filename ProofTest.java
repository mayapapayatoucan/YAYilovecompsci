import static org.junit.Assert.*;

import org.junit.Test;


public class ProofTest {

  @Test
	public void testIsModusPonens() {
		try {
			assertTrue(Proof.isMP(new Expression("(p=>q)"), new Expression("p"), new Expression("q")));
			assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("q"), new Expression("q")));
			assertTrue(Proof.isMT(new Expression("(p=>q)"), new Expression("~q"), new Expression("~p")));
			assertFalse(Proof.isMP(new Expression("(p=>q)"), new Expression("~q"), new Expression("~q")));
			assertTrue(Proof.isIC(new Expression("q"), new Expression("(p=>q)")));
			assertTrue(Proof.isC(new Expression("p"), new Expression("~p")));
			assertTrue(Proof.isC(new Expression("~p"), new Expression("p")));
			
		} catch (IllegalLineException e){
		}
	}
}
