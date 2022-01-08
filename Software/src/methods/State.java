/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.Collection;
import java.util.HashSet;

/* State - a state of an automata */
public class State {

	// all substates / numbers in the state
	public HashSet<Integer> substates = new HashSet<Integer>();

	/* Empty Set state constructor */
	public State() {
	}

	/* 1 substate state constructor */
	public State(Integer substate) {
		this.addSubState(substate);
	}

	/* premade state */
	public State(State other) {
		this.substates.addAll(other.substates);
	}

	/* premade state constructor */
	public State(Collection<Integer> substates) {
		this.substates.addAll(substates);
	}

	/* premade set of nfa states constructor */
	public State(HashSet<State> nfastates) {
		for (State q : nfastates)
			this.substates.addAll(q.substates);
	}

	/* add substate to state */
	public void addSubState(Integer substate) {
		this.substates.add(substate);
	}

	/* equality of 2 states with the same substates */
	/* equals for hashset */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		State s = (State) obj;
		return (this.substates).equals(s.substates);
	}

	/* hashcode for hashset */
	@Override
	public int hashCode() {
		if (this.substates.size() == 0)
			return -1;
		int hc = this.substates.hashCode();
		for (Integer q : this.substates)
			hc += Math.pow(q, 2);
		return hc;
	}

	/* print state */
	@Override
	public String toString() {
		return "" + this.hashCode();
	}

}
