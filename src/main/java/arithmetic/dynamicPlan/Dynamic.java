package arithmetic.dynamicPlan;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/12
 *  @Version 1.0.0
 **/
public class Dynamic {

	/**
	 * @Author zhangxiaojun
	 * @Description
	 * Fibonacci数列  问题描述：有一座高度为n级的楼梯，从下往上走，每跨一步只能向上1级或者2级台阶，求出一共有多少种走法。
	 * F(1) = 1;
	 * F(2) = 2;
	 * F(n) = F(n - 1) + F(n - 2);
	 * @Date 下午5:50 2018/10/12
	 * @Param [n]
	 * @return int
	 * @Version 1.0.0
	 **/
	public static int getNum(int n) {
		if (n <= 2)
			return n;
		int a = 1;//只有1级台阶的情况
		int b = 2;//有2级台阶的情况
		int tmp = 0;//辅助变量
		for (int i = 3; i <= n; i++) {
			tmp = a + b;
			a = b;
			b = tmp;
		}
		return tmp;
	}

	/**
	 * 非递归方式实现求两个字符串最长公共字序列的长度
	 * @param str1 第一个字符串
	 * @param m 第一个字符串需要比较的长度
	 * @param str2 第二个字符串
	 * @param n 第一个字符串需要比较的长度
	 * @return
	 */
	public static int lcs1(String str1, int m, String str2, int n) {
		if (m == 0 || n == 0)
			return 0;
		//构建一个m + 1行 n + 1列的辅助二维数组,里面的元素初始值都为0
		int[][] arr = new int[m + 1][n + 1];
		//依次求二维数组中的值
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				//当最后一个字符相等时等于左上角的元素加1
				if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
					arr[i][j] = arr[i - 1][j - 1] + 1;
				}//不相等时取紧邻左边元素和上边元素者的大者
				else {
					arr[i][j] = getMax(arr[i - 1][j], arr[i][j - 1]);
				}
			}
		}
		return arr[m][n];//二维数组右下角的元素即我们需要的值
	}

	public static int getMax(int a, int b) {
		if (a <= b) {
			return b;
		}
		return a;
	}


	public static void main(String[] args) {
		System.out.println(getNum(4));
	}

}
