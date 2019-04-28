import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *  @Description
 *  @Author zhangxiaojun
 *  @Date 2018/10/26
 *  @Version 1.0.0
 **/
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan({"com.andysun"})
@Configuration
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.andysun")
@EnableElasticsearchRepositories(basePackages = "com.andysun.repository")
public class Application {
	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(Application.class, args);
	}

	@Bean(name = "myPool")
	public ExecutorService initMyThread() throws Exception {
		// 暂且先这样配置，等有统一的配置中心了，这些参数放入配置中心里面
		ExecutorService executorService = new ThreadPoolExecutor(5, 20, 5, TimeUnit.MINUTES, new LinkedBlockingDeque(),
				new ThreadPoolExecutor.CallerRunsPolicy());
//		return TtlExecutors.getTtlExecutorService(executorService);
		return executorService;
	}
}
