package arithmetic.search;

/**
 *  @Description 又叫折半查找，要求待查找的序列有序。每次取中间位置的值与待查关键字比较，如果中间位置
 * 的值比待查关键字大，则在前半部分循环这个查找的过程，如果中间位置的值比待查关键字小，
 * 则在后半部分循环这个查找的过程。直到查找到了为止，否则序列中没有待查的关键字。
 *  @Author zhangxiaojun
 *  @Date 2020/7/5
 *  @Version 1.0.0
 **/
public class BiSearch {

	public static int search(int[] array,int a){
		int low = 0;
		int hi = array.length-1;
		int mid;

		while (low <= hi){
			mid = (hi + low) / 2 ;
			if(array[mid] == a){
				return mid+1;
			}else if(array[mid] < a){
				low = mid +1;
			}else {
				hi = mid - 1;
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		int[] arrs = new int[]{0,1,2,3,4,7,9,14,110};
		System.out.println(search(arrs,9));
	}
}
