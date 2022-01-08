/**
 * Lior Wunsch - 206238263
 */
package tests;

import java.util.HashSet;

import junit.framework.TestCase;
import methods.NFA;
import methods.State;
import methods.Transition;

public class NFATest extends TestCase {

	private NFA nfa;
	private NFA enfa;

	public NFATest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		buildNFA();
		buildENFA();
	}

	public void testENFAtoNFA() {
		NFA expected = nfa;
		NFA result = NFA.eNFAtoNFA(enfa);

		Boolean bool = expected.states.equals(result.states);
		bool = bool && expected.start_state.equals(result.start_state);
		bool = bool && expected.final_states.equals(result.final_states);
		bool = bool && expected.transitions.equals(result.transitions);

		assertTrue(bool);
	}

	public void buildNFA() {
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

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q0);
		final_states.add(q1);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q2, 'a'));
		transitions.add(new Transition(q0, q3, 'a'));
		transitions.add(new Transition(q0, q3, 'b'));
		transitions.add(new Transition(q1, q3, 'b'));
		transitions.add(new Transition(q2, q0, 'a'));
		transitions.add(new Transition(q2, q1, 'a'));
		transitions.add(new Transition(q2, q4, 'a'));
		transitions.add(new Transition(q4, q2, 'a'));
		transitions.add(new Transition(q4, q3, 'a'));
		transitions.add(new Transition(q4, q3, 'b'));

		nfa = new NFA(states, q0, final_states, transitions);
	}

	public void buildENFA() {
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

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'e'));
		transitions.add(new Transition(q0, q2, 'a'));
		transitions.add(new Transition(q1, q3, 'b'));
		transitions.add(new Transition(q2, q3, 'e'));
		transitions.add(new Transition(q2, q4, 'a'));
		transitions.add(new Transition(q4, q0, 'e'));

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q1);

		enfa = new NFA(states, q0, final_states, transitions);
	}

}
