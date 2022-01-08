/**
 * Lior Wunsch - 206238263
 */
package methods;

import java.util.ArrayList;

/* a tree representing a regex */
public class TreeNode {

//        '|'
//        / \
//       / | \
//	    /  |  \
//	   /   |   \
//	  /    |    \
//	'e'   '*'    ' '
//	     /      / | \
//	    /      /  |  \
//	   ' '   'a' 'b' '*'
//	  /  \             \
//  'a'  'b'           'a'
//
// = "e|(ab)*|aba*"

	public char value;
	public ArrayList<TreeNode> subnodes = new ArrayList<TreeNode>();

	public TreeNode() {
	}

	public TreeNode(char value) {
//		if (value == 'e') {
//			this.value = '*';
//			TreeNode e = new TreeNode();
//			e.value = 'e';
//			this.subnodes.add(e);
//		}
		this.value = value;
	}

	/* check if the other regex accepts all this regex words */
	/* and vice versa, -1 if no match */
	/* 1 if this matches other, 2 if other matches this */
	public int matches(TreeNode obj) {
		if (this == obj)
			return 1;

		String it = this.toString();
		String other = ((TreeNode) obj).toString();
		if (it.equals(other))
			return 1;

		if (it.equals("e") && !other.contains("e")) {
			String blank = "";
			if (blank.matches(other))
				return 1;
		}
		if (it.matches(other))
			return 1;

		if (other.equals("e") && !it.contains("e")) {
			String blank = "";
			if (blank.matches(it))
				return 2;
		}
		if (other.matches(it))
			return 2;

		return -1;
	}

	/* traverses the tree with 'this' TreeNode as head */
	/* and return a viable regex */
	/* according to how we created R(i,j,k) */
	@Override
	public String toString() {

		// 'e' | 'a' | 'b'
		if (this.subnodes.size() == 0)
			return "" + this.value;

		StringBuilder str = new StringBuilder("");

		// '*' has 1 subnode and the result is (r)*
		if (this.value == '*') {
			String sub = this.subnodes.get(0).toString();
			if (sub.length() > 1)
				str.append("(" + sub + ")*");
			else
				str.append(sub + "*");
		}

		// '|' has multiple subnodes and the result is r1|r2|...|rn
		if (this.value == '|') {
			str.append("(");
			for (TreeNode subnode : this.subnodes) {
				String sub = subnode.toString();
				str.append(sub + "|");
			}
			str.append(")");
			if (str.length() == 2) // str = "()"
				str.delete(0, 1);
			else if (str.charAt(str.length() - 2) == '|')
				str.deleteCharAt(str.length() - 2);
		}

		// ' ' has multiple subnodes and the result is r1r2...rn
		// from first (left) subnode to last (right) subnode
		if (this.value == ' ') {
			String sub;
			for (TreeNode subnode : this.subnodes) {
				sub = subnode.toString();
				str.append(sub);
			}
		}

		return str.toString();
	}

}
