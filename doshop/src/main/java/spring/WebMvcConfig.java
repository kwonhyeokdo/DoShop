package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spring.intercepteor.InterceptorFactory;
import spring.intercepteor.admin.ConditionForAdminEntry;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"admin", "etc", "member", "database", "spring"})
public class WebMvcConfig implements WebMvcConfigurer{
	@Autowired
	InterceptorFactory interceptorFactory;
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 모든 페이지마다 SigninSession 갱신  */
		registry.addInterceptor(interceptorFactory.getSigninSessionUpdate()).addPathPatterns("/**");

		/* autoSignin  */
		registry.addInterceptor(interceptorFactory.getAutoSignin()).addPathPatterns("/**");
		
		/* /Admin 페이지 authority에 따른 접근 설정 */
		// 접근 가능 권한: 1, 2, 3
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return authority != 0 ? true : false;
			}
		})).addPathPatterns("/Admin/**").excludePathPatterns("/Admin/Signin");
		
		// 접근 가능 권한: 1
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return authority == 1 ? true : false;
			}
		})).addPathPatterns("/Admin/AdminManagement/**");
		
		// 접근 가능 권한: 1, 2
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return (authority >= 1 && authority <= 2) ? true : false;
			}
		})).addPathPatterns("/Admin/CategoryManagement/**");
	}
}
