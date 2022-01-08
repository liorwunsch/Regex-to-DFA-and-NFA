/**
 * Lior Wunsch - 206238263
 */
package tests;

import java.util.HashSet;

import junit.framework.TestCase;
import methods.Thompson;
import methods.NFA;
import methods.State;
import methods.Transition;

public class ThompsonTest extends TestCase {

	private NFA n;
	private NFA m;

	public ThompsonTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		n = new NFA('a');
		m = new NFA('b');
	}

	public void testKleene() {
		NFA expected = kleeneNFA();
		NFA result = Thompson.kleene(n);

		Boolean bool = expected.states.equals(result.states);
		bool = bool && expected.start_state.equals(result.start_state);
		bool = bool && expected.final_states.equals(result.final_states);
		bool = bool && expected.transitions.equals(result.transitions);

		assertTrue(bool);
	}

	public void testConcat() {
		NFA expected = concatNFA();
		NFA result = Thompson.concat(n, m);

		Boolean bool = expected.states.equals(result.states);
		bool = bool && expected.start_state.equals(result.start_state);
		bool = bool && expected.final_states.equals(result.final_states);
		bool = bool && expected.transitions.equals(result.transitions);

		assertTrue(bool);
	}

	public void testUnion() {
		NFA expected = unionNFA();
		NFA result = Thompson.union(n, m);

		Boolean bool = expected.states.equals(result.states);
		bool = bool && expected.start_state.equals(result.start_state);
		bool = bool && expected.final_states.equals(result.final_states);
		bool = bool && expected.transitions.equals(result.transitions);

		assertTrue(bool);
	}

	private NFA kleeneNFA() {
		HashSet<State> states = new HashSet<State>();
		State q0 = new State();
		q0.addSubState(0);
		states.add(q0);
		State q1 = new State();
		q1.addSubState(1);
		states.add(q1);
		State q2 = new State();
		q2.addSubState(2);
		states.add(q2);
		State q3 = new State();
		q3.addSubState(3);
		states.add(q3);

		State start_state = q2;

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q3);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'a'));
		transitions.add(new Transition(q1, q0, 'e'));
		transitions.add(new Transition(q1, q3, 'e'));
		transitions.add(new Transition(q2, q0, 'e'));
		transitions.add(new Transition(q2, q3, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

	private NFA concatNFA() {
		HashSet<State> states = new HashSet<State>();
		State q0 = new State();
		q0.addSubState(0);
		states.add(q0);
		State q1 = new State();
		q1.addSubState(1);
		states.add(q1);
		State q2 = new State();
		q2.addSubState(2);
		states.add(q2);
		State q3 = new State();
		q3.addSubState(3);
		states.add(q3);
		State q4 = new State();
		q4.addSubState(4);
		states.add(q4);
		State q5 = new State();
		q5.addSubState(5);
		states.add(q5);

		State start_state = q4;

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q5);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'a'));
		transitions.add(new Transition(q1, q2, 'e'));
		transitions.add(new Transition(q2, q3, 'b'));
		transitions.add(new Transition(q3, q5, 'e'));
		transitions.add(new Transition(q4, q0, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

	private NFA unionNFA() {
		HashSet<State> states = new HashSet<State>();
		State q0 = new State();
		q0.addSubState(0);
		states.add(q0);
		State q1 = new State();
		q1.addSubState(1);
		states.add(q1);
		State q2 = new State();
		q2.addSubState(2);
		states.add(q2);
		State q3 = new State();
		q3.addSubState(3);
		states.add(q3);
		State q4 = new State();
		q4.addSubState(4);
		states.add(q4);
		State q5 = new State();
		q5.addSubState(5);
		states.add(q5);

		State start_state = q4;

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q5);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'a'));
		transitions.add(new Transition(q1, q5, 'e'));
		transitions.add(new Transition(q2, q3, 'b'));
		transitions.add(new Transition(q3, q5, 'e'));
		transitions.add(new Transition(q4, q0, 'e'));
		transitions.add(new Transition(q4, q2, 'e'));

		return new NFA(states, start_state, final_states, transitions);
	}

}
