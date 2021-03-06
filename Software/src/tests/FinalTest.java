/**
 * Lior Wunsch - 206238263
 */
package tests;

import java.util.ArrayList;

import junit.framework.TestCase;
import methods.DFA;
import methods.NFA;
import methods.Regex;
import methods.RunAssignment;
import methods.Thompson;

public class FinalTest extends TestCase {

	private ArrayList<String> regexes;

	public FinalTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		regexes = new ArrayList<String>();
		regexes.add("e");
		regexes.add("a");
		regexes.add("(a)");
		regexes.add("a*");
		regexes.add("(a)*");
		regexes.add("(a*)");
		regexes.add("aa");
		regexes.add("(a)a");
		regexes.add("a(a)");
		regexes.add("(a)(a)");
		regexes.add("ab");
		regexes.add("(a)b");
		regexes.add("a(b)");
		regexes.add("(a)(b)");
		regexes.add("(aa)*");
		regexes.add("((a)a)*");
		regexes.add("(a(a))*");
		regexes.add("((a)(a))*");
		regexes.add("(ab)*");
		regexes.add("((a)b)*");
		regexes.add("(a(b))*");
		regexes.add("((a)(b))*");
		regexes.add("a*a");
		regexes.add("a*(a)");
		regexes.add("(a)*a");
		regexes.add("(a*)a");
		regexes.add("(a)*(a)");
		regexes.add("(a*)(a)");
		regexes.add("a*b");
		regexes.add("a*(b)");
		regexes.add("(a)*b");
		regexes.add("(a*)b");
		regexes.add("(a)*(b)");
		regexes.add("(a*)(b)");
		regexes.add("(a*a)*");
		regexes.add("(a*(a))*");
		regexes.add("((a)*a)*");
		regexes.add("((a*)a)*");
		regexes.add("((a)*(a))*");
		regexes.add("((a*)(a))*");
		regexes.add("(a*b)*");
		regexes.add("(a*(b))*");
		regexes.add("((a)*b)*");
		regexes.add("((a*)b)*");
		regexes.add("((a)*(b))*");
		regexes.add("((a*)(b))*");
		regexes.add("aa*");
		regexes.add("(a)a*");
		regexes.add("a(a)*");
		regexes.add("a(a*)");
		regexes.add("(a)(a)*");
		regexes.add("(a)(a*)");
		regexes.add("ba*");
		regexes.add("(b)a*");
		regexes.add("b(a)*");
		regexes.add("b(a*)");
		regexes.add("(b)(a)*");
		regexes.add("(b)(a*)");
		regexes.add("(aa*)*");
		regexes.add("((a)a*)*");
		regexes.add("(a(a)*)*");
		regexes.add("(a(a*))*");
		regexes.add("((a)(a)*)*");
		regexes.add("((a)(a*))*");
		regexes.add("(ba*)*");
		regexes.add("((b)a*)*");
		regexes.add("(b(a)*)*");
		regexes.add("(b(a*))*");
		regexes.add("((b)(a)*)*");
		regexes.add("((b)(a*))*");
		regexes.add("a*a*");
		regexes.add("(a)*a*");
		regexes.add("(a*)a*");
		regexes.add("a*(a)*");
		regexes.add("a*(a*)");
		regexes.add("(a)*(a)*");
		regexes.add("(a)*(a*)");
		regexes.add("(a*)(a)*");
		regexes.add("(a*)(a*)");
		regexes.add("b*a*");
		regexes.add("(b)*a*");
		regexes.add("(b*)a*");
		regexes.add("b*(a)*");
		regexes.add("b*(a*)");
		regexes.add("(b)*(a)*");
		regexes.add("(b)*(a*)");
		regexes.add("(b*)(a)*");
		regexes.add("(b*)(a*)");
		regexes.add("(a*a*)*");
		regexes.add("((a)*a*)*");
		regexes.add("((a*)a*)*");
		regexes.add("(a*(a)*)*");
		regexes.add("(a*(a*))*");
		regexes.add("((a)*(a)*)*");
		regexes.add("((a)*(a*))*");
		regexes.add("((a*)(a)*)*");
		regexes.add("((a*)(a*))*");
		regexes.add("(b*a*)*");
		regexes.add("((b)*a*)*");
		regexes.add("((b*)a*)*");
		regexes.add("(b*(a)*)*");
		regexes.add("(b*(a*))*");
		regexes.add("((b)*(a)*)*");
		regexes.add("((b)*(a*))*");
		regexes.add("((b*)(a)*)*");
		regexes.add("((b*)(a*))*");
		regexes.add("a|a");
		regexes.add("(a)|a");
		regexes.add("a|(a)");
		regexes.add("(a)|(a)");
		regexes.add("a|b");
		regexes.add("(a)|b");
		regexes.add("a|(b)");
		regexes.add("(a)|(b)");
		regexes.add("(a|a)*");
		regexes.add("((a)|a)*");
		regexes.add("(a|(a))*");
		regexes.add("((a)|(a))*");
		regexes.add("(a|b)*");
		regexes.add("((a)|b)*");
		regexes.add("(a|(b))*");
		regexes.add("((a)|(b))*");
		regexes.add("a*|a");
		regexes.add("a*|(a)");
		regexes.add("(a)*|a");
		regexes.add("(a*)|a");
		regexes.add("(a)*|(a)");
		regexes.add("(a*)|(a)");
		regexes.add("a*|b");
		regexes.add("a*|(b)");
		regexes.add("(a)*|b");
		regexes.add("(a*)|b");
		regexes.add("(a)*|(b)");
		regexes.add("(a*)|(b)");
		regexes.add("(a*|a)*");
		regexes.add("(a*|(a))*");
		regexes.add("((a)*|a)*");
		regexes.add("((a*)|a)*");
		regexes.add("((a)*|(a))*");
		regexes.add("((a*)|(a))*");
		regexes.add("(a*|b)*");
		regexes.add("(a*|(b))*");
		regexes.add("((a)*|b)*");
		regexes.add("((a*)|b)*");
		regexes.add("((a)*|(b))*");
		regexes.add("((a*)|(b))*");
		regexes.add("a|a*");
		regexes.add("(a)|a*");
		regexes.add("a|(a)*");
		regexes.add("a|(a*)");
		regexes.add("(a)|(a)*");
		regexes.add("(a)|(a*)");
		regexes.add("b|a*");
		regexes.add("(b)|a*");
		regexes.add("b|(a)*");
		regexes.add("b|(a*)");
		regexes.add("(b)|(a)*");
		regexes.add("(b)|(a*)");
		regexes.add("(a|a*)*");
		regexes.add("((a)|a*)*");
		regexes.add("(a|(a)*)*");
		regexes.add("(a|(a*))*");
		regexes.add("((a)|(a)*)*");
		regexes.add("((a)|(a*))*");
		regexes.add("(b|a*)*");
		regexes.add("((b)|a*)*");
		regexes.add("(b|(a)*)*");
		regexes.add("(b|(a*))*");
		regexes.add("((b)|(a)*)*");
		regexes.add("((b)|(a*))*");
		regexes.add("a*|a*");
		regexes.add("(a)*|a*");
		regexes.add("(a*)|a*");
		regexes.add("a*|(a)*");
		regexes.add("a*|(a*)");
		regexes.add("(a)*|(a)*");
		regexes.add("(a)*|(a*)");
		regexes.add("(a*)|(a)*");
		regexes.add("(a*)|(a*)");
		regexes.add("b*|a*");
		regexes.add("(b)*|a*");
		regexes.add("(b*)|a*");
		regexes.add("b*|(a)*");
		regexes.add("b*|(a*)");
		regexes.add("(b)*|(a)*");
		regexes.add("(b)*|(a*)");
		regexes.add("(b*)|(a)*");
		regexes.add("(b*)|(a*)");
		regexes.add("(a*|a*)*");
		regexes.add("((a)*|a*)*");
		regexes.add("((a*)|a*)*");
		regexes.add("(a*|(a)*)*");
		regexes.add("(a*|(a*))*");
		regexes.add("((a)*|(a)*)*");
		regexes.add("((a)*|(a*))*");
		regexes.add("((a*)|(a)*)*");
		regexes.add("((a*)|(a*))*");
		regexes.add("(b*|a*)*");
		regexes.add("((b)*|a*)*");
		regexes.add("((b*)|a*)*");
		regexes.add("(b*|(a)*)*");
		regexes.add("(b*|(a*))*");
		regexes.add("((b)*|(a)*)*");
		regexes.add("((b)*|(a*))*");
		regexes.add("((b*)|(a)*)*");
		regexes.add("((b*)|(a*))*");
		regexes.add("aaa");
		regexes.add("a(a)a");
		regexes.add("aa(a)");
		regexes.add("a(a)(a)");
		regexes.add("aab");
		regexes.add("a(a)b");
		regexes.add("aa(b)");
		regexes.add("a(a)(b)");
		regexes.add("aa*a");
		regexes.add("aa*(a)");
		regexes.add("a(a)*a");
		regexes.add("a(a*)a");
		regexes.add("a(a)*(a)");
		regexes.add("a(a*)(a)");
		regexes.add("aa*b");
		regexes.add("aa*(b)");
		regexes.add("a(a)*b");
		regexes.add("a(a*)b");
		regexes.add("a(a)*(b)");
		regexes.add("a(a*)(b)");
		regexes.add("aaa*");
		regexes.add("a(a)a*");
		regexes.add("aa(a)*");
		regexes.add("aa(a*)");
		regexes.add("a(a)(a)*");
		regexes.add("a(a)(a*)");
		regexes.add("aba*");
		regexes.add("a(b)a*");
		regexes.add("ab(a)*");
		regexes.add("ab(a*)");
		regexes.add("a(b)(a)*");
		regexes.add("a(b)(a*)");
		regexes.add("aa*a*");
		regexes.add("a(a)*a*");
		regexes.add("a(a*)a*");
		regexes.add("aa*(a)*");
		regexes.add("aa*(a*)");
		regexes.add("a(a)*(a)*");
		regexes.add("a(a)*(a*)");
		regexes.add("a(a*)(a)*");
		regexes.add("a(a*)(a*)");
		regexes.add("ab*a*");
		regexes.add("a(b)*a*");
		regexes.add("a(b*)a*");
		regexes.add("ab*(a)*");
		regexes.add("ab*(a*)");
		regexes.add("a(b)*(a)*");
		regexes.add("a(b)*(a*)");
		regexes.add("a(b*)(a)*");
		regexes.add("a(b*)(a*)");
		regexes.add("aa|a");
		regexes.add("a(a)|a");
		regexes.add("aa|(a)");
		regexes.add("a(a)|(a)");
		regexes.add("aa|b");
		regexes.add("a(a)|b");
		regexes.add("aa|(b)");
		regexes.add("a(a)|(b)");
		regexes.add("aa*|a");
		regexes.add("aa*|(a)");
		regexes.add("a(a)*|a");
		regexes.add("a(a*)|a");
		regexes.add("a(a)*|(a)");
		regexes.add("a(a*)|(a)");
		regexes.add("aa*|b");
		regexes.add("aa*|(b)");
		regexes.add("a(a)*|b");
		regexes.add("a(a*)|b");
		regexes.add("a(a)*|(b)");
		regexes.add("a(a*)|(b)");
		regexes.add("a|aa");
		regexes.add("a|(a)a");
		regexes.add("a|a(a)");
		regexes.add("a|(a)(a)");
		regexes.add("a|ab");
		regexes.add("a|(a)b");
		regexes.add("a|a(b)");
		regexes.add("a|(a)(b)");
		regexes.add("a|a*a");
		regexes.add("a|a*(a)");
		regexes.add("a|(a)*a");
		regexes.add("a|(a*)a");
		regexes.add("a|(a)*(a)");
		regexes.add("a|(a*)(a)");
		regexes.add("a|a*b");
		regexes.add("a|a*(b)");
		regexes.add("a|(a)*b");
		regexes.add("a|(a*)b");
		regexes.add("a|(a)*(b)");
		regexes.add("a|(a*)(b)");
		regexes.add("a|aa*");
		regexes.add("a|(a)a*");
		regexes.add("a|a(a)*");
		regexes.add("a|a(a*)");
		regexes.add("a|(a)(a)*");
		regexes.add("a|(a)(a*)");
		regexes.add("a|ba*");
		regexes.add("a|(b)a*");
		regexes.add("a|b(a)*");
		regexes.add("a|b(a*)");
		regexes.add("a|(b)(a)*");
		regexes.add("a|(b)(a*)");
		regexes.add("a|a*a*");
		regexes.add("a|(a)*a*");
		regexes.add("a|(a*)a*");
		regexes.add("a|a*(a)*");
		regexes.add("a|a*(a*)");
		regexes.add("a|(a)*(a)*");
		regexes.add("a|(a)*(a*)");
		regexes.add("a|(a*)(a)*");
		regexes.add("a|(a*)(a*)");
		regexes.add("a|b*a*");
		regexes.add("a|(b)*a*");
		regexes.add("a|(b*)a*");
		regexes.add("a|b*(a)*");
		regexes.add("a|b*(a*)");
		regexes.add("a|(b)*(a)*");
		regexes.add("a|(b)*(a*)");
		regexes.add("a|(b*)(a)*");
		regexes.add("a|(b*)(a*)");
		regexes.add("a|a|a");
		regexes.add("a|(a)|a");
		regexes.add("a|a|(a)");
		regexes.add("a|(a)|(a)");
		regexes.add("a|a|b");
		regexes.add("a|(a)|b");
		regexes.add("a|a|(b)");
		regexes.add("a|(a)|(b)");
		regexes.add("a|a*|a");
		regexes.add("a|a*|(a)");
		regexes.add("a|(a)*|a");
		regexes.add("a|(a*)|a");
		regexes.add("a|(a)*|(a)");
		regexes.add("a|(a*)|(a)");
		regexes.add("a|a*|b");
		regexes.add("a|a*|(b)");
		regexes.add("a|(a)*|b");
		regexes.add("a|(a*)|b");
		regexes.add("a|(a)*|(b)");
		regexes.add("a|(a*)|(b)");
		regexes.add("a|a|a*");
		regexes.add("a|(a)|a*");
		regexes.add("a|a|(a)*");
		regexes.add("a|a|(a*)");
		regexes.add("a|(a)|(a)*");
		regexes.add("a|(a)|(a*)");
		regexes.add("a|b|a*");
		regexes.add("a|(b)|a*");
		regexes.add("a|b|(a)*");
		regexes.add("a|b|(a*)");
		regexes.add("a|(b)|(a)*");
		regexes.add("a|(b)|(a*)");
		regexes.add("a|a*|a*");
		regexes.add("a|(a)*|a*");
		regexes.add("a|(a*)|a*");
		regexes.add("a|a*|(a)*");
		regexes.add("a|a*|(a*)");
		regexes.add("a|(a)*|(a)*");
		regexes.add("a|(a)*|(a*)");
		regexes.add("a|(a*)|(a)*");
		regexes.add("a|(a*)|(a*)");
		regexes.add("a|b*|a*");
		regexes.add("a|(b)*|a*");
		regexes.add("a|(b*)|a*");
		regexes.add("a|b*|(a)*");
		regexes.add("a|b*|(a*)");
		regexes.add("a|(b)*|(a)*");
		regexes.add("a|(b)*|(a*)");
		regexes.add("a|(b*)|(a)*");
		regexes.add("a|(b*)|(a*)");
	}

	public void testTransformations() throws Exception {
		for (int i = 0; i < regexes.size(); i++) {
			System.out.println("Input Regex: " + regexes.get(i));

			NFA nfa_of_input = Thompson.compile(regexes.get(i));
			NFA nfa = NFA.eNFAtoNFA(nfa_of_input);
			DFA dfa_of_input = DFA.NFAtoDFA(nfa);

			System.out.print("Output DFA:\n" + dfa_of_input.toString());
			boolean equals0 = RunAssignment.DFAequalsRegex(dfa_of_input, regexes.get(i));
			assertTrue(equals0);
			System.out.println("Output DFA equals Input Regex ? " + equals0);

			String regex_of_dfa = Regex.DFAtoRegex(dfa_of_input);
			System.out.println("Reduced Output Regex: " + regex_of_dfa);
			boolean equals1 = RunAssignment.DFAequalsRegex(dfa_of_input, regex_of_dfa);
			assertTrue(equals1);
			System.out.println("Reduced Output Regex equals Input DFA ? " + equals1);

			String regex_of_dfa2 = Regex.rawDFAtoRegex(dfa_of_input);
			System.out.println("Unreduced Output Regex: " + regex_of_dfa2);
//			boolean equals2 = RunAssignment.DFAequalsRegex(dfa_of_input, regex_of_dfa2);
//			assertTrue(equals2);
			System.out.println("Unreduced Output Regex equals Input DFA ? " + equals1 + "\n");
		}
	}

}
