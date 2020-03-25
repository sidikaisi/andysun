package arithmetic.algorithm;

import java.util.Objects;

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

	public int lengthOfLongestSubstring(String s) {
		int l = s.length();
		int res = 0;
		for (int i = 0; i < l; i++) {
			for (int j = i + 1; j <= l; j++) {

			}
		}
	}

	private boolean existDuplicate() {

	}
}
