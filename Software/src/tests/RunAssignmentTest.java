/**
 * Lior Wunsch - 206238263
 */
package tests;

import java.util.HashSet;

import junit.framework.TestCase;
import methods.DFA;
import methods.RunAssignment;
import methods.State;
import methods.Transition;

public class RunAssignmentTest extends TestCase {

	private DFA dfa;
	private String regex;

	public RunAssignmentTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		buildDFA();
		regex = "b*|b*ab*a(ab*ab*a|b)*b*ab*";
	}

	public void testDFAequalsRegex() throws Exception {
		assertTrue(RunAssignment.DFAequalsRegex(dfa, regex));
	}

	public void buildDFA() {
		HashSet<State> states = new HashSet<State>();
		State q0 = new State(0);
		states.add(q0);
		State q1 = new State(1);
		states.add(q1);
		State q2 = new State(2);
		states.add(q2);

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q0);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'a'));
		transitions.add(new Transition(q0, q0, 'b'));
		transitions.add(new Transition(q1, q2, 'a'));
		transitions.add(new Transition(q1, q1, 'b'));
		transitions.add(new Transition(q2, q0, 'a'));
		transitions.add(new Transition(q2, q2, 'b'));

		dfa = new DFA(states, q0, final_states, transitions);
	}

}
