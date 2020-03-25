package arithmetic.trieTree;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2020/3/25
 *  @Version 1.0.0
 **/
public class TrieNode {

	private TrieNode[] links;

	private final int R = 26;

	private boolean isEnd;

	public TrieNode() {
		links = new TrieNode[R];
	}

	public boolean containsKey(char ch) {
		return links[ch -'a'] != null;
	}
	public TrieNode get(char ch) {
		return links[ch -'a'];
	}
	public void put(char ch, TrieNode node) {
		links[ch -'a'] = node;
	}
	public void setEnd() {
		isEnd = true;
	}
	public boolean isEnd() {
		return isEnd;
	}
}
