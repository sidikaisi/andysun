package arithmetic.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 *  @Description
 *  给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *  输入: "abcabcbb"
 *  输出: 3
 *  解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 *
 *  输入: "bbbbb"
 *  输出: 1
 *  解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 *  @Author zhangxiaojun
 *  @Date 2020/2/26
 *  @Version 1.0.0
 **/
public class Solution {

	public static void main(String[] args) {
		System.out.println("方法1："+lengthOfLongestSubstring("adasjkhjkhdsa"));
		System.out.println("方法2："+lengthOfLongestSubstring2("adasjkhjkhdsa"));
	}

	public static int lengthOfLongestSubstring2(String s){
		int l = s.length();
		Set<Character> set = new HashSet<>(l);
		int res = 0,i = 0,j = 0;
		while (i < l && j < l){
			if(!set.contains(s.charAt(j))){
				set.add(s.charAt(j++));
				res = Math.max(res,j-i);
			}else {
				set.remove(s.charAt(i++));
			}
		}
		return res;
	}

	public static int lengthOfLongestSubstring(String s) {
		int l = s.length();
		int res = 0;
		for (int i = 0; i < l; i++) {
			for (int j = i + 1; j <= l; j++) {
				if(allUnique(s,i,j)){
					res = Math.max(res,j-i);
				}
			}
		}

		return res;
	}

	private static boolean allUnique(String s,int start,int end) {
		Set<Character> set = new HashSet<>();
		for (int i=start;i<end ;i++){
			Character c = s.charAt(i);
			if(set.contains(c)){
				return false;
			}
			set.add(c);
		}
		return true;
	}
}
