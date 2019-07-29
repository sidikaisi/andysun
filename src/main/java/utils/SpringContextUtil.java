package utils;

import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @Description 获取上下文的
 * @Author zhangxiaojun
 * @Date 2018/8/2
 * @Version 1.0.0
 **/
public class SpringContextUtil {

	private static ApplicationContext applicationContext;

	//获取上下文
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//设置上下文
	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	//通过名字获取上下文中的bean
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	//通过类型获取上下文中的bean
	public static Object getBean(Class<?> requiredType) {
		return applicationContext.getBean(requiredType);
	}

    @SuppressWarnings("unchecked")
	public static <T> T getBeanByType(Class<?> requiredType) {
		return (T) applicationContext.getBean(requiredType);
	}

    public static <T> Map<String, T> getBeans(Class<T> requiredType) {
        return applicationContext.getBeansOfType(requiredType);
    }

}
