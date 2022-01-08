/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.ArrayList;
import java.util.HashSet;

public class Regex {

	/***************** with regex reduction *****************/

	// regex from dfa constructor
	public static String DFAtoRegex(DFA dfa) throws Exception {
		// the states' number are their indexes in the list - {0, ..., n}
		// the initial state will be represented by index 0
		ArrayList<State> statesList = new ArrayList<State>();
		statesList.add(dfa.start_state);
		for (State q : dfa.states) {
			if (!q.equals(dfa.start_state) && !q.equals(new State()))
				statesList.add(q);
		}

		// get all sets of words that end in a final state
		// hence are accepted by the dfa
		// and connect them with union
		TreeNode regex = new TreeNode('|');
		for (State fs : dfa.final_states) {
			TreeNode tn = R(dfa, statesList, 0, statesList.indexOf(fs), statesList.size() - 1);
			if (tn.value != '0')
				regex.subnodes.add(tn);
		}

		if (regex.subnodes.size() == 0)
			regex.value = '0';
		if (regex.subnodes.size() == 1)
			regex = regex.subnodes.get(0);

		StringBuilder result = new StringBuilder(regex.toString());
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == '0') {
				result.deleteCharAt(i);
				if (result.charAt(i) == '*') {
					result.deleteCharAt(i);
					i--;
				}
				i--;
			}
			if (i > 0 && result.charAt(i) == '|' && result.charAt(i - 1) == '|') {
				result.deleteCharAt(i);
				i--;
			}
		}

		if (result.charAt(result.length() - 1) == '|')
			result.deleteCharAt(result.length() - 1);

		String result2 = result.toString();
		if (!validRegEx(result2))
			throw new Exception(result + "\nNot valid regex");

		return result2;
	}

	// R(i,j,k) = set of words that move from state i to state j
	// without transitioning through state k+1 or higher
	// except for i and j themselves
	// a set of words = a regex = a tree representing a regex
	public static TreeNode R(DFA dfa, ArrayList<State> statesList, int i, int j, int k) {

		TreeNode head = new TreeNode();

		// R(i,j,-1)
		if (k == -1) {

			// check if there is a direct transition from i to j
			char sigma = 'x';
			for (Transition t : dfa.transitions)
				if (t.state_from.equals(statesList.get(i)) && t.state_to.equals(statesList.get(j)))
					sigma = t.trans_symbol;

			if (sigma != 'x') {
				if (i == j) {
					head.subnodes.add(new TreeNode('e'));
					head.value = '|';
					head.subnodes.add(new TreeNode(sigma));
				} else
					head.value = sigma;
			} else {
				if (i == j)
					head.value = 'e';
				else
					head.value = '0';
			}

			return head;
		}

		// R(i,j,k)
		TreeNode left = R(dfa, statesList, i, j, k - 1);

		// build right side of 'or'
		// emptySet 'concat' anything = emptySet
		TreeNode right = new TreeNode(' ');
		TreeNode right1 = R(dfa, statesList, i, k, k - 1);
		if (right1.value == '0') {
			right.value = '0';
		} else {
			TreeNode right2pre = R(dfa, statesList, k, k, k - 1);
			if (right2pre.value == '0') {
				right.value = '0';
			} else {
				TreeNode right3 = R(dfa, statesList, k, j, k - 1);
				if (right3.value == '0') {
					right.value = '0';
				} else {
					boolean flag1 = false, flag2 = false, flag3 = false;
					TreeNode right2 = new TreeNode('*');

					if (right1.value != 'e')
						flag1 = true;
					if (right2pre.value != 'e') {
						right2.subnodes.add(right2pre);
						flag2 = true;
					}
					if (right3.value != 'e')
						flag3 = true;

					if (flag1 == true && right1.value == '|') {
						if (right1.subnodes.size() > 1 && right1.subnodes.get(0).value == 'e')
							right1.subnodes.remove(0);
						if (right1.subnodes.size() == 1)
							right1 = right1.subnodes.get(0);
					}
					if (flag2 == true && right2pre.value == '|') {
						if (right2pre.subnodes.size() > 1 && right2pre.subnodes.get(0).value == 'e')
							right2pre.subnodes.remove(0);
						if (right2pre.subnodes.size() == 1)
							right2pre = right2pre.subnodes.get(0);
					}
					if (flag3 == true && right3.value == '|') {
						if (right3.subnodes.size() > 1 && right3.subnodes.get(0).value == 'e')
							right3.subnodes.remove(0);
						if (right3.subnodes.size() == 1)
							right3 = right3.subnodes.get(0);
					}

					if (flag1 == true)
						right.subnodes.add(right1);
					if (flag2 == true)
						right.subnodes.add(right2);
					if (flag3 == true)
						right.subnodes.add(right3);
					if (right.subnodes.size() == 0)
						right.value = 'e';
					if (right.subnodes.size() == 1)
						right = right.subnodes.get(0);
				}
			}
		}

		// emptySet 'or' something = something
		if (left.value == '0') {
			if (right.value == '0')
				head.value = '0';
			else
				head = right;
		} else {
			if (right.value == '0')
				head = left;
			else {
				int match = left.matches(right);
				if (match == 1)
					head = right;
				else {
					if (match == 2) {
						head = left;
					} else {
						head.value = '|';
						if (left.value == '|') {
							head.subnodes.addAll(left.subnodes);
						} else {
							head.subnodes.add(left);
						}
						head.subnodes.add(right);
					}
				}
			}
		}
		return head;
	}

	/***************** w/o regex reduction *****************/

	public static String rawDFAtoRegex(DFA dfa) throws Exception {

		// the states' number are their indexes in the list - {0, ..., n}
		// the initial state will be represented by index 0
		ArrayList<State> statesList = new ArrayList<State>();
		statesList.add(dfa.start_state);
		for (State q : dfa.states) {
			if (!q.equals(dfa.start_state) && !q.equals(new State()))
				statesList.add(q);
		}

		// get all sets of words that end in a final state
		// hence are accepted by the dfa
		// and connect them with union
		StringBuilder finalResult = new StringBuilder("");
		for (State fs : dfa.final_states) {
			String rI = rawR(dfa, statesList, 0, statesList.indexOf(fs), statesList.size() - 1);
			if (!rI.equals("0"))
				finalResult.append("(" + rI + ")|");
		}
		if (finalResult.charAt(finalResult.length() - 1) == '|')
			finalResult.deleteCharAt(finalResult.length() - 1);

		String finalResult2 = finalResult.toString();
		if (!validRegEx(finalResult2))
			throw new Exception(finalResult + "\nNot valid regex");
		return finalResult2;
	}

	// R(i,j,k) = set of words that move from state i to state j
	// without transitioning through state k+1 or higher
	// except for i and j themselves
	// a set of words = a regex = a tree representing a regex
	public static String rawR(DFA dfa, ArrayList<State> statesList, int i, int j, int k) {
		StringBuilder result = new StringBuilder("");

		// R(i,j,-1)
		if (k == -1) {
			// check if there is a direct transition from i to j
			char sigma = 'x';
			for (Transition t : dfa.transitions)
				if (t.state_from.equals(statesList.get(i)) && t.state_to.equals(statesList.get(j)))
					sigma = t.trans_symbol;

			if (sigma != 'x') {
				if (i == j) {
					result.append("e|" + sigma);
				} else
					result.append(sigma);
			} else {
				if (i == j)
					result.append("e");
				else
					result.append("0");
			}
			return result.toString();
		}

		// R(i,j,k)
		String left = rawR(dfa, statesList, i, j, k - 1);
		String right1 = rawR(dfa, statesList, i, k, k - 1);
		String right2 = rawR(dfa, statesList, k, k, k - 1);
		String right3 = rawR(dfa, statesList, k, j, k - 1);

		if (!right1.equals("0") && !right2.equals("0") && !right3.equals("0")) {
			if (!left.equals("0"))
				result.append(left + "|");
			result.append("(" + right1);
			result.append("(" + right2 + ")*");
			result.append(right3 + ")");
		} else {
			if (!left.equals("0"))
				result.append(left);
			else
				result.append("0");
		}
		return result.toString();
	}

	/********************* regex methods ***************/

	// simplify the repeated boolean condition checks
	public static boolean alpha(char c) {
		return c >= 'a' && c <= 'b';
	}

	public static boolean alphabet(char c) {
		return alpha(c) || c == 'e';
	}

	public static boolean regexOperator(char c) {
		return c == '(' || c == ')' || c == '*' || c == '|';
	}

	public static boolean validRegExChar(char c) {
		return alphabet(c) || regexOperator(c);
	}

	// check if given string is a valid regular expression
	public static boolean validRegEx(String regex) {
		if (regex.isEmpty())
			return false;
		for (char c : regex.toCharArray())
			if (!validRegExChar(c))
				return false;
		return true;
	}

	// check if given string is valid word
	public static boolean validWord(String word) {
		if (word.isEmpty())
			return true;
		for (char c : word.toCharArray())
			if (!alpha(c))
				return false;
		return true;
	}

	// check if string is accepted by automata by configuration
	// if a final state can be reached once the word is read
	// return true, else return false
	// works for any automata without E transitions
	public static boolean isAccepted(DFA dfa, String word) throws Exception {
		if (word.equals("e"))
			return dfa.final_states.contains(dfa.start_state);

		if (word.length() == 0 || word == null || !validWord(word))
			throw new Exception("Invalid Word Input.");

		return configuration(dfa, dfa.start_state, new StringBuilder(word));
	}

	public static boolean configuration(DFA dfa, State q, StringBuilder word) {
		if (word.length() == 0) {
			return dfa.final_states.contains(q);
		}

		HashSet<State> ds = NFA.getDelta(dfa, q, word.charAt(0));
		word.deleteCharAt(0);
		for (State qds : ds)
			if (configuration(dfa, qds, word) == true)
				return true;

		return false;
	}

}
