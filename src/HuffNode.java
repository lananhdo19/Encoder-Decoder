
public class HuffNode implements Comparable<HuffNode>{
	public char c;
	public int freq;
	private HuffNode left;
	private HuffNode right;	
	
	public HuffNode(char c, int freq) {
		left = null;
		right = null;
		this.c = c;
		this.freq = freq;
	}
	
	public HuffNode() {
		c = '@';
		freq = 99999;
		left = null;
		right = null;
	}
	
	public void setC(char c) {
		this.c = c;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public void setLeft(HuffNode left) {
		this.left = left;
	}

	public void setRight(HuffNode right) {
		this.right = right;
	}

	public HuffNode getLeft() {
		return left;
	}

	public char getC() {
		return c;
	}

	public int getFreq() {
		return freq;
	}

	public HuffNode getRight() {
		return right;
	}

	public String toString() {
		return c + "(" + freq + ")";
	}

	@Override
	public int compareTo(HuffNode h) {
		return freq - h.freq;
	}
	
	public String inOrder(){
		String root = this.toString();
		String l = "";
		String r = "";

		if(this.left!=null) l = this.left.inOrder();
		if(this.right!=null) r = this.right.inOrder();
		return l + root + r;
	}
	
}
