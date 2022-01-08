/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.HashSet;

/* NFA - serves as a graph representing a Non-Deterministic Finite Automata */
public class NFA extends DFA {

	/* =============== basic constructors of NFA and printing =============== */

	/* generally built with Thompson's Alg with one final state in [F] */
	/* that all accepting states have E transitions to */

	/* although a state is a set of numbers, NFA states will only use 1 number */
	/* it is built this way for traversing through both NFAs and DFAs equally */

	/* premade NFA constructor with [P(Q)], [Delta], [F] */
	public NFA(HashSet<State> states, State start_state, HashSet<State> final_states, HashSet<Transition> transitions) {
		super(states, start_state, final_states, transitions);
	}

	/* NFA for one char, has 2 states and 1 final - supports Thompson's Alg */
	public NFA(char c) {
		super();
		State q0 = new State();
		State q1 = new State();
		q0.addSubState(0);
		q1.addSubState(1);
		this.start_state = q0;
		this.states.add(q0);
		this.states.add(q1);
		this.final_states.add(q1);
		this.transitions.add(new Transition(q0, q1, c));
	}

	/*
	 * ============= transformation of NFA from eNFA , eDelta, eClosure
	 * =============
	 */

	/* get delta(q,s) in NFA n -> level 1+ transitions */
	/* good for automatas with E transitions */
	public static HashSet<State> getEDelta(DFA n, State q, char s) {
		HashSet<State> d = new HashSet<State>();

		// first, get all p states of eClosure on q
		HashSet<State> pList = NFA.getEClosure(n, q);

		// then, for each p, get all r states of delta(p,s)
		for (State p : pList) {
			HashSet<State> rList = getDelta(n, p, s);

			// then, for each r, add all states of eClosure on r to delta list
			for (State r : rList)
				d.addAll(NFA.getEClosure(n, r));
		}

		// return a list of all states reachable with symbol s from state q in NFA n
		return d;
	}

	/* calculate eClosure of state q in NFA n using BFS() and getEAdj() */
	public static HashSet<State> getEClosure(DFA n, State q) {
		return getSClosure(n, q, 'e');
	}

	/* build NFA without E transitions from an NFA with E transitions */
	public static NFA eNFAtoNFA(NFA en) {

		// final_states may be changed regarding E acceptance
		HashSet<State> final_states = new HashSet<State>();
		final_states.addAll(en.final_states);

		// check if E is accepted in NFA en
		for (State f : en.final_states) {
			State s = en.start_state;
			if (getEClosure(en, s).contains(f)) {
				final_states.add(s);
				break;
			}
		}

		// add all transitions according to delta of each state
		HashSet<Transition> transitions = new HashSet<Transition>();
		for (State q : en.states) {
			HashSet<State> da = getEDelta(en, q, 'a');
			for (State p : da)
				transitions.add(new Transition(q, p, 'a'));

			HashSet<State> db = getEDelta(en, q, 'b');
			for (State p : db)
				transitions.add(new Transition(q, p, 'b'));
		}

		// return new NFA without E transitions and multiple final_states
		// and the same states and start_state
		return new NFA(en.states, en.start_state, final_states, transitions);
	}

}
