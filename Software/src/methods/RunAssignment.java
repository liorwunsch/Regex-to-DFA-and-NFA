/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.Scanner;

public class RunAssignment {

	private static Scanner sc = new Scanner(System.in);

	/* String.matches(String regex) */
	/* a|b = a 'or' b */
	/* ab = a 'concat' b */
	/* (exp)* = exp 'kleene star' */
	/* Epsilon = 'e' */

	/*
	 * compares DFA (and not NFA with/out e) and regex by the words they accept and
	 * don't accept
	 */
	public static boolean DFAequalsRegex(DFA dfa, String regex) throws Exception {

		// first check if E has the same result for both DFA and regex
		// "e" doesn't match (exp)* unfortunately
		// but we know "" does match for it and can represent "e" for that matter
		boolean left = Regex.isAccepted(dfa, "e");
		if (!regex.contains("e")) {
			String blank = "";
			if (!(left == blank.matches(regex))) {
				System.out.println("e doesn't match");
				System.out.println("dfa accepts = " + left);
				System.out.println("regex accepts = " + blank.matches(regex) + "\n");
				return false;
			}
		} else {
			String epsilon = "e";
			if (!(left == epsilon.matches(regex))) {
				System.out.println("e doesn't match");
				System.out.println("dfa accepts = " + left);
				System.out.println("regex accepts = " + epsilon.matches(regex) + "\n");
				return false;
			}
		}

		// I use binary numbers for the purpose of getting all the words we want
		// starting with a 2 digits binary numbers and increasing
		// check that all words represented by those numbers
		// have the same result for both DFA and regex
		int bWord = 1;
		while (bWord != 1024) { // 2^20 = 1048576
			if (singleDFAequalsRegex(dfa, regex, Integer.toBinaryString(bWord)) == false)
				return false;

			bWord += 1;
		}

		return true;
	}

	/* compares DFA and regex by their acceptance result for a particular word */
	public static boolean singleDFAequalsRegex(DFA dfa, String regex, String bWord) throws Exception {

		// switch all '0' to 'a' and all '1' to 'b'
		char[] chars1 = bWord.toCharArray();
		for (int i = 0; i < chars1.length; i++) {
			if (chars1[i] == '0')
				chars1[i] = 'a';
			else
				chars1[i] = 'b';
		}
		String word1 = new String(chars1);

		// switch all '0' to 'b' and all '1' to 'a'
		char[] chars2 = bWord.toCharArray();
		for (int i = 0; i < chars2.length; i++) {
			if (chars2[i] == '0')
				chars2[i] = 'b';
			else
				chars2[i] = 'a';
		}
		String word2 = new String(chars2);

		if (!(Regex.isAccepted(dfa, word1) == word1.matches(regex))) {
			System.out.println(word1 + " doesn't match");
			System.out.println("dfa accepts = " + Regex.isAccepted(dfa, word1));
			System.out.println("regex accepts = " + word1.matches(regex) + "\n");
			return false;
		}

		if (!(Regex.isAccepted(dfa, word2) == word2.matches(regex))) {
			System.out.println(word2 + " doesn't match");
			System.out.println("dfa accepts = " + Regex.isAccepted(dfa, word2));
			System.out.println("regex accepts = " + word2.matches(regex) + "\n");
			return false;
		}

		return true;
	}

	/* Firstly, waits for a Regex input, transforms it to DFA and prints */
	/* then, checks that the result DFA is a true match to the Regex input */
	/* Secondly, transforms the result DFA to a new result Regex */
	/* then, checks that the result Regex is a true match to the result DFA */
	public static void main(String[] args) {

		while (true) {
			/* input Regex */
			System.out.println("\nEnter a regular expression with the " + "alphabet {a,b}\ne for empty"
					+ " \n* for Kleene Star" + "\nab without operator indicates " + "concatenation"
					+ " \n| for Union\n( ) for controlling precedence of operators\nx to end");
			String regex = sc.nextLine();

			for (int i = 1; i <= 100; i++)
				System.out.println("\n");

			System.out.println("Input:\n" + regex);
			if (regex.equals("x"))
				break;

			NFA nfa_of_input = null;
			try {
				nfa_of_input = Thompson.compile(regex);
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\n");
				continue;
			}

			/* build and print DFA */

			NFA nfa = NFA.eNFAtoNFA(nfa_of_input);
			DFA dfa_of_input = DFA.NFAtoDFA(nfa);
			System.out.println("\nhttp://ivanzuzak.info/noam/webapps/fsm2regex/");
			System.out.println("RegexToDFA:");
			System.out.println(dfa_of_input);

			/* check true match */
			try {
				System.out.println("Match between input Regex and result DFA = " + DFAequalsRegex(dfa_of_input, regex));
			} catch (Exception e) {
			}

			/* build and print Regex */
			String regex_of_dfa = null;
			try {
				regex_of_dfa = Regex.DFAtoRegex(dfa_of_input);
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\n");
				continue;
			}
			System.out.println("\nDFAtoRegex:");
			System.out.println(regex_of_dfa + "\n");

			/* check true match */
			try {
				System.out.println(
						"Match between input DFA and result Regex = " + DFAequalsRegex(dfa_of_input, regex_of_dfa));
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\n");
			}
		}
		myExit();
	}

	public static void myExit() {
		System.out.println("\nEnd");
		System.exit(0);
	}

}
