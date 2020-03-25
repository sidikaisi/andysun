package utils;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/15
 *  @Version 1.0.0
 **/
public class D {

	private int v;

	private String n;

	public D(int v, String n) {
		this.v = v;
		this.n = n;
	}

	public int getV() {
		return v;
	}

	public String getN() {
		return n;
	}

	public String cacl() {
		return v == 1 ? "NO" : "YES";
	}
}
