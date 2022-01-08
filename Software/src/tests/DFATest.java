/**
 * Lior Wunsch - 206238263
 */
package tests;

import java.util.HashSet;

import junit.framework.TestCase;
import methods.DFA;
import methods.NFA;
import methods.State;
import methods.Transition;

public class DFATest extends TestCase {

	private DFA dfa;
	private NFA nfa;

	public DFATest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		buildDFA();
		buildNFA();
	}

	public void testNFAtoDFA() {
		DFA expected = dfa;
		DFA result = DFA.NFAtoDFA(nfa);

		Boolean bool = expected.states.equals(result.states);
		bool = bool && expected.start_state.equals(result.start_state);
		bool = bool && expected.final_states.equals(result.final_states);
		bool = bool && expected.transitions.equals(result.transitions);

		assertTrue(bool);
	}

	public void buildDFA() {
		HashSet<State> states = new HashSet<State>();
		State p0 = new State();
		states.add(p0);
		State p1 = new State();
		p1.addSubState(0);
		states.add(p1);
		State p2 = new State();
		p2.addSubState(1);
		states.add(p2);
		State p3 = new State();
		p3.addSubState(1);
		p3.addSubState(2);
		states.add(p3);
		State p4 = new State();
		p4.addSubState(0);
		p4.addSubState(1);
		p4.addSubState(2);
		states.add(p4);

		State start_state = p1;

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(p2);
		final_states.add(p3);
		final_states.add(p4);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(p0, p0, 'a'));
		transitions.add(new Transition(p0, p0, 'b'));
		transitions.add(new Transition(p1, p2, 'a'));
		transitions.add(new Transition(p1, p0, 'b'));
		transitions.add(new Transition(p2, p3, 'a'));
		transitions.add(new Transition(p2, p0, 'b'));
		transitions.add(new Transition(p3, p4, 'a'));
		transitions.add(new Transition(p3, p2, 'b'));
		transitions.add(new Transition(p4, p4, 'a'));
		transitions.add(new Transition(p4, p2, 'b'));

		dfa = new DFA(states, start_state, final_states, transitions);
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

		HashSet<State> final_states = new HashSet<State>();
		final_states.add(q1);

		HashSet<Transition> transitions = new HashSet<Transition>();
		transitions.add(new Transition(q0, q1, 'a'));
		transitions.add(new Transition(q1, q1, 'a'));
		transitions.add(new Transition(q1, q2, 'a'));
		transitions.add(new Transition(q2, q0, 'a'));
		transitions.add(new Transition(q2, q1, 'b'));
		transitions.add(new Transition(q2, q2, 'a'));

		nfa = new NFA(states, q0, final_states, transitions);
	}

}
