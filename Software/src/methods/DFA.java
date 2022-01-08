/**
 * Lior Wunsch - 206238263
 */

package methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/* DFA - serves as a graph representing a Deterministic Finite Automata */
public class DFA {

	/* =============== basic constructors of DFA and printing =============== */

	/* alphabet [Sigma] = { a,b } */

	// states list [Q]+[P(Q)]
	public HashSet<State> states;

	// starting state [q0]
	public State start_state;

	// list of final accepting states [F]
	public HashSet<State> final_states;

	// transitions list [Delta]
	public HashSet<Transition> transitions;

	/* empty DFA constructor */
	public DFA() {
		this.states = new HashSet<State>();
		this.start_state = null;
		this.final_states = new HashSet<State>();
		this.transitions = new HashSet<Transition>();
	}

	/* premade DFA constructor with [P(Q)], [Delta], [F] */
	public DFA(HashSet<State> states, State start_state, HashSet<State> final_states, HashSet<Transition> transitions) {
		this.states = new HashSet<State>(states);
		for (State q : this.states) {
			if (q.equals(start_state)) {
				this.start_state = q;
				break;
			}
		}
		this.final_states = new HashSet<State>();
		for (State q : this.states) {
			if (final_states.contains(q))
				this.final_states.add(q);
		}
		this.transitions = new HashSet<Transition>();
		for (Transition t : transitions) {
			State state_from = null;
			for (State q : this.states) {
				if (q.equals(t.state_from)) {
					state_from = q;
					break;
				}
			}
			State state_to = null;
			for (State q : this.states) {
				if (q.equals(t.state_to)) {
					state_to = q;
					break;
				}
			}
			this.transitions.add(new Transition(state_from, state_to, t.trans_symbol));
		}
	}

	/* print DFA */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		ArrayList<State> statesList = new ArrayList<State>();
		statesList.add(this.start_state);
		for (State q : this.states) {
			if (!q.equals(this.start_state))
				statesList.add(q);
		}

		// append all states
		str.append("#states\n");
		for (State q : this.states) {
			str.append(statesList.indexOf(q) + "\n");
		}

		// append start state
		str.append("#initial\n");
		str.append(statesList.indexOf(this.start_state) + "\n");

		// append all final states
		str.append("#accepting\n");
		for (State q : this.final_states) {
			str.append(statesList.indexOf(q) + "\n");
		}

		str.append("#alphabet\ne\na\nb\n");

		// append all transitions' strings
		str.append("#transitions\n");
		for (Transition t : this.transitions) {
			str.append(statesList.indexOf(t.state_from) + ":" + t.trans_symbol + ">" + statesList.indexOf(t.state_to)
					+ "\n");
		}

		return str.toString();
	}

	/* ======= transformation of DFA from NFA , Delta , sClosure ======= */

	/* get delta(q,s) in NFA n -> level 1 transitions */
	public static HashSet<State> getDelta(DFA dfa, State q, char s) {
		HashSet<State> adjSet = new HashSet<State>();
		for (Transition t : dfa.transitions) {
			if ((t.state_from).equals(q) && t.trans_symbol == s)
				adjSet.add(t.state_to);
		}
		return adjSet;
	}

	/* get delta(q,s) for all symbols s - used for general BFS */
	/* good for minimizing a power group of states in a DFA */
	public static HashSet<State> getAdj(DFA dfa, State q) {
		HashSet<State> adjSet = new HashSet<State>();
		for (Transition t : dfa.transitions) {
			if (t.state_from.equals(q))
				adjSet.add(t.state_to);
		}
		return adjSet;
	}

	/* build altered BFS 'c' traversal from node/state q and extract sClosure */
	/* 'c' can be 'e' for eClosure or '*' for calculating all reachable states */
	public static HashSet<State> getSClosure(DFA dfa, State s, char c) {
		// a list for all visited nodes
		ArrayList<State> visited = new ArrayList<>();

		// a queue for BFS
		ArrayList<State> queue = new ArrayList<>();

		// ds = distance from s to node
		HashMap<State, Integer> ds = new HashMap<State, Integer>();

		// initialize parameters for u in Q\{s}
		for (State u : dfa.states) {
			if (!u.equals(s))
				ds.put(u, -1);
		}

		// mark source node as visited and add it to the queue
		// distance to itself is 0 and it has no predecessor
		visited.add(s);
		ds.put(s, 0);
		queue.add(s);

		while (queue.size() != 0) {

			// starting from u = s
			State u = queue.remove(0);
			int dsu = ds.get(u);

			HashSet<State> adj = null;
			if (c == '*') {
				// get all states with any transitions from u
				adj = getAdj(dfa, u);
			} else {
				// get all states with 'e' transitions from u
				adj = getDelta(dfa, u, c);
			}

			// for each adjacent node v of u, if not visited, mark as visited
			// set distance and predecessor in path from s and add to queue
			for (State v : adj) {
				if (!visited.contains(v)) {
					visited.add(v);
					ds.put(v, dsu + 1);
					queue.add(v);
				}
			}
		}

		// finalilze sClosure of state q with symbol s from traversal
		HashSet<State> sClosureList = new HashSet<State>();
		for (State u : dfa.states) {
			if (ds.get(u) != -1)
				sClosureList.add(u);
		}
		return sClosureList;
	}

	/* build DFA from NFA without E transitions */
	public static DFA NFAtoDFA(NFA nfa) {

		// dfa's states are the power group or all subsets of nfa's states
		int n = nfa.states.size();
		HashSet<State> states = new HashSet<State>();
		for (int i = 0; i < (1 << n); i++) {
			HashSet<Integer> p = new HashSet<Integer>();
			for (int j = 0; j < n; j++)

				// (1 << j) is a number with j'th bit 1
				// so when we 'and' them with the
				// subset number we get which numbers
				// are present in the subset and which
				// are not
				if ((i & (1 << j)) > 0)
					p.add(j);
			states.add(new State(p));
		}

		// calculate all new transitions and add them to dfa
		HashSet<Transition> transitions = new HashSet<Transition>();
		for (State B : states) {

			// for each state p of nfa in B, add group of delta(p,a) in nfa to deltaDa
			HashSet<State> deltaDa = new HashSet<State>();
			for (int p : B.substates) {
				State s = new State();
				s.addSubState(p);
				deltaDa.addAll(getDelta(nfa, s, 'a'));
			}
			// connect B state of dfa with deltaDa state of dfa
			for (State ourDeltaDa : states)
				if (ourDeltaDa.equals(new State(deltaDa)))
					transitions.add(new Transition(B, ourDeltaDa, 'a'));

			// for each state p of nfa in B, add group of delta(p,b) in nfa to deltaDb
			HashSet<State> deltaDb = new HashSet<State>();
			for (int p : B.substates) {
				State s = new State();
				s.addSubState(p);
				deltaDb.addAll(getDelta(nfa, s, 'b'));
			}
			// connect B state of dfa with deltaDb state of dfa
			for (State ourDeltaDb : states)
				if (ourDeltaDb.equals(new State(deltaDb)))
					transitions.add(new Transition(B, ourDeltaDb, 'b'));
		}

		// dfa's final states are all states which contain final states of nfa
		HashSet<State> final_states = new HashSet<State>();
		for (State fNFA : nfa.final_states) {
			for (State B : states) {
				if (B.substates.containsAll(fNFA.substates))
					final_states.add(B);
			}
		}

		// build dfa
		DFA dfa = new DFA(states, nfa.start_state, final_states, transitions);

		// leave only reachable states
		HashSet<State> reachables = getSClosure(dfa, dfa.start_state, '*');
		dfa.states.clear();
		dfa.states.addAll(reachables);

		// remove all unreachable final states
		for (State fs : final_states) {
			if (!reachables.contains(fs))
				dfa.final_states.remove(fs);
		}

		// remove excess transitions
		for (Transition trans : transitions) {
			if (!reachables.contains(trans.state_from) || !reachables.contains(trans.state_to))
				dfa.transitions.remove(trans);
		}

		return dfa;
	}

}
