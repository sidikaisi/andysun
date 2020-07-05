package arithmetic.sort;

import java.util.Arrays;

/**
 *  @Description
 *  冒泡排序算法
 * （1）比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * （2）这样对数组的第 0 个数据到 N-1 个数据进行一次遍历后，最大的一个数据就“沉”到数组第
 * N-1 个位置。
 * （3）N=N-1，如果 N 不为 0 就重复前面二步，否则排序完成。
 *  @Author zhangxiaojun
 *  @Date 2020/7/5
 *  @Version 1.0.0
 **/
public class BubbleSort {

	public static void sort(int[] arrs){
		int n = arrs.length;
		for (int a=0;a<n;a++){ // n次排序
			for (int j=1;j<n;j++){
				if(arrs[j-1]>arrs[j]){
					int temp = arrs[j];
					arrs[j] = arrs[j-1];
					arrs[j-1] = temp;
				}
			}
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
