/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.HashSet;
import java.util.Stack;

/* Thompson - an implementation of Thompson's Alg */
/* for transforming a Regular Expression into an NFA */
public class Thompson {

	/* ========================= operators on NFAs ========================== */

	/* kleene() - Thompson's Alg for kleene star */
	public static NFA kleene(NFA n) {

		// the result nfa will have the same states with 2 new states
		HashSet<State> states = new HashSet<State>(n.states);

		// create new state for start_state with a new nonexisting number
		State start_state = new State(n.states.size());
		states.add(start_state);

		// create new state for final_state with a new nonexisting number
		HashSet<State> final_states = new HashSet<State>();
		State final_state = new State(n.states.size() + 1);
		states.add(final_state);
		final_states.add(final_state);

		// transitions are the same with 2 more
		HashSet<Transition> transitions = new HashSet<Transition>(n.transitions);

		// connect new start_state to n's start_state
		transitions.add(new Transition(start_state, n.start_state, 'e'));

		// connect n's final_state(s) to new final_state(s)
		for (State nfs : n.final_states)
			transitions.add(new Transition(nfs, final_state, 'e'));

		// connect n's final_state(s) to n's start_state for looping kleene star
		for (State nfs : n.final_states)
			transitions.add(new Transition(nfs, n.start_state, 'e'));

		// connect new start_state to new final_state for E acceptance
		transitions.add(new Transition(start_state, final_state, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

	/* concat() - Thompson's Alg for concatenation */
	public static NFA concat(NFA n, NFA m) {

		// the result nfa will have n's states
		HashSet<State> states = new HashSet<State>(n.states);

		// the result nfa will have n's transitions
		HashSet<Transition> transitions = new HashSet<Transition>(n.transitions);

		// the result nfa will also have m's states and m's transitions
		// change m's states so they don't duplicate n's states
		// m's transitions change also according to m's new states automatically
		int i = 0;
		for (State ms : m.states) {
			ms.substates.clear();
			ms.addSubState(n.states.size() + i);
			i++;
		}
		states.addAll(m.states);
		transitions.addAll(m.transitions);

		// create new state for start_state with a new nonexisting number
		State start_state = new State(n.states.size() + m.states.size());
		states.add(start_state);

		// create new state for final_state with a new nonexisting number
		HashSet<State> final_states = new HashSet<State>();
		State final_state = new State(n.states.size() + m.states.size() + 1);
		states.add(final_state);
		final_states.add(final_state);

		// connect new start_state to n's start_state
		transitions.add(new Transition(start_state, n.start_state, 'e'));

		// connect n's final_state(s) to m's start_state
		for (State nfs : n.final_states)
			transitions.add(new Transition(nfs, m.start_state, 'e'));

		// connect m's final_state(s) to new final_state(s)
		for (State mfs : m.final_states)
			transitions.add(new Transition(mfs, final_state, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

	/* union() - Thompson's Alg for union (or) */
	public static NFA union(NFA n, NFA m) {

		// the result nfa will have n's states
		HashSet<State> states = new HashSet<State>(n.states);

		// the result nfa will have n's transitions
		HashSet<Transition> transitions = new HashSet<Transition>(n.transitions);

		// the result nfa will also have m's states and m's transitions
		// change m's states so they don't duplicate n's states
		// m's transitions change also according to m's new states automatically
		int i = 0;
		for (State ms : m.states) {
			ms.substates.clear();
			ms.addSubState(n.states.size() + i);
			i++;
		}
		states.addAll(m.states);
		transitions.addAll(m.transitions);

		// create new state for start_state with a new nonexisting number
		State start_state = new State(n.states.size() + m.states.size());
		states.add(start_state);

		// create new state for final_state with a new nonexisting number
		HashSet<State> final_states = new HashSet<State>();
		State final_state = new State(n.states.size() + m.states.size() + 1);
		states.add(final_state);
		final_states.add(final_state);

		// connect new start_state to n's start_state and to m's start_state
		transitions.add(new Transition(start_state, n.start_state, 'e'));
		transitions.add(new Transition(start_state, m.start_state, 'e'));

		// connect n's final_state(s) and m's final_state(s) to new final_state(s)
		for (State mfs : m.final_states)
			transitions.add(new Transition(mfs, final_state, 'e'));
		for (State nfs : n.final_states)
			transitions.add(new Transition(nfs, final_state, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

	/*
	 * compile() - compile given regular expression into a NFA using Thompson
	 * Construction Algorithm. Will implement typical compiler stack model to
	 * simplify processing the string. This gives descending precedence to
	 * characters on the right. ** fixed **
	 */
	public static NFA compile(String regex) throws Exception {
		if (!Regex.validRegEx(regex))
			throw new Exception("Invalid Regular Expression Input.");

		Stack<Character> operators = new Stack<Character>();
		Stack<NFA> operands = new Stack<NFA>();
		Stack<NFA> concat_stack = new Stack<NFA>();
		boolean ccflag = false; // concat flag
		char op, c; // current character of string
		int para_count = 0;
		NFA nfa1, nfa2;

		for (int i = 0; i < regex.length(); i++) {
			c = regex.charAt(i);
			if (Regex.alphabet(c)) {
				operands.push(new NFA(c));
				if (ccflag == true) { // concat this with previous
					operators.push('.'); // '.' used to represent concat
				} else
					ccflag = true;
			} else {
				if (c == ')') {
					ccflag = false;
					if (para_count == 0)
						throw new Exception("Error: More end paranthesis " + "than beginning paranthesis");
					else
						para_count--;
					// process stuff on stack till '('
					while (!operators.empty() && operators.peek() != '(') {
						op = operators.pop();
						if (op == '.') {
							nfa2 = operands.pop();
							nfa1 = operands.pop();
							operands.push(concat(nfa1, nfa2));
						} else if (op == '|') {
							nfa2 = operands.pop();
							if (!operators.empty() && operators.peek() == '.') {
								concat_stack.push(operands.pop());
								while (!operators.empty() && operators.peek() == '.') {
									concat_stack.push(operands.pop());
									operators.pop();
								}
								nfa1 = concat(concat_stack.pop(), concat_stack.pop());
								while (concat_stack.size() > 0) {
									nfa1 = concat(nfa1, concat_stack.pop());
								}
							} else {
								nfa1 = operands.pop();
							}
							operands.push(union(nfa1, nfa2));
						}
					}
					if (operators.peek() == '(')
						operators.pop();
					ccflag = true;
				} else if (c == '*') {
					operands.push(kleene(operands.pop()));
					ccflag = true;
				} else if (c == '(') { // if any other operator: push
					if (ccflag == true) {
						operators.push('.');
						ccflag = false;
					}
					operators.push(c);
					para_count++;
				} else if (c == '|') {
					operators.push(c);
					ccflag = false;
				}
			}
		}
		while (operators.size() > 0) {
			if (operands.empty())
				throw new Exception("Error: imbalance in operands and operators");
			op = operators.pop();
			if (op == '.') {
				nfa2 = operands.pop();
				nfa1 = operands.pop();
				operands.push(concat(nfa1, nfa2));
			} else if (op == '|') {
				nfa2 = operands.pop();
				if (!operators.empty() && operators.peek() == '.') {
					concat_stack.push(operands.pop());
					while (!operators.empty() && operators.peek() == '.') {
						concat_stack.push(operands.pop());
						operators.pop();
					}
					nfa1 = concat(concat_stack.pop(), concat_stack.pop());
					while (concat_stack.size() > 0) {
						nfa1 = concat(nfa1, concat_stack.pop());
					}
				} else {
					nfa1 = operands.pop();
				}
				operands.push(union(nfa1, nfa2));
			}
		}
		return operands.pop();
	}
}