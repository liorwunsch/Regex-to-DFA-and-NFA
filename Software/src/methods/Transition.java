/**
 * Lior Wunsch - 206238263
 */
package methods;

/* Transition - a transition of an automata */
public class Transition {

	// 2 states from and to
	public State state_from, state_to;

	// the symbol the transition consumes
	public char trans_symbol;

	/* new transition constructor */
	public Transition(State state_from, State state_to, char trans_symbol) {
		this.state_from = state_from;
		this.state_to = state_to;
		this.trans_symbol = trans_symbol;
	}

	/* equality of 2 transitions with the same attributes */
	/* equals for hashset */
	@Override
	public boolean equals(Object obj) {
		Transition t = (Transition) obj;

		if (!this.state_from.equals(t.state_from))
			return false;

		if (!this.state_to.equals(t.state_to))
			return false;

		if (!(this.trans_symbol == t.trans_symbol))
			return false;

		return true;
	}

	/* hashcode for hashset */
	@Override
	public int hashCode() {
		String str = "" + this.trans_symbol;
		return this.state_from.hashCode() + (this.state_to.hashCode() * str.hashCode());
	}

	/* print transition */
	@Override
	public String toString() {
		return "(" + this.state_from.toString() + "," + this.trans_symbol + "," + this.state_to.toString() + ")";
	}

}
