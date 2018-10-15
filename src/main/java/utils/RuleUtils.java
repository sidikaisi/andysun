package utils;

import lombok.Data;
import org.apache.commons.jexl3.*;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/15
 *  @Version 1.0.0
 **/
public class RuleUtils {

	public static void main(String[] args) {
		JexlEngine jexl = new JexlBuilder().create();
		JexlContext context = new MapContext();
		context.set("G1",1);
		context.set("G2",1);
		context.set("G3",1);
		context.set("G4",1);

		JexlExpression expression = jexl.createExpression("((G1+G2+G3)*0.1)+G4");
//		System.out.println(expression.evaluate(context));
//
//
//		JexlExpression e2 = jexl.createExpression("(G1 == G2)? 1 : 2");
//		System.out.println(e2.evaluate(context));

		D d = new D(1,"一级");
		context.set("d",d);
		JexlExpression e3 = jexl.createExpression("d.cacl()");
		System.out.println(e3.evaluate(context));

	}
}
