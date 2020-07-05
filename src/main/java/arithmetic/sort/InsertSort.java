package arithmetic.sort;

import java.util.Arrays;

/**
 *  @Description 插入排序算法
 *  通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入。
 * 插入排序非常类似于整扑克牌。在开始摸牌时，左手是空的，牌面朝下放在桌上。接着，一次从
 * 桌上摸起一张牌，并将它插入到左手一把牌中的正确位置上。为了找到这张牌的正确位置，要将
 * 它与手中已有的牌从右到左地进行比较。无论什么时候，左手中的牌都是排好序的。
 *  @Author zhangxiaojun
 *  @Date 2020/7/5
 *  @Version 1.0.0
 **/
public class InsertSort {

	public static void sort(int[] arrs) {
		for (int i = 1; i < arrs.length; i++) {
			int insert = arrs[i];
			int index = i - 1;
			while (index >= 0 && insert < arrs[index]) {
				arrs[index + 1] = arrs[index];
				index--;
			}
			arrs[index+1] = insert;
		}
	}


	public static void main(String[] args) {
		int[] arr = new int[]{3,5,9,1,2,4,6,2,9,11,3,5,7};
		sort(arr);
		Arrays.stream(arr).forEach(a->{
			System.out.println(a);
		});
	}
}
