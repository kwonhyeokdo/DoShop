package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:8090");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 모든 페이지마다 SigninSession 갱신  */
		registry.addInterceptor(interceptorFactory.getSigninSessionUpdate()).addPathPatterns("/**");

		/* autoSignin  */
		registry.addInterceptor(interceptorFactory.getAutoSignin()).addPathPatterns("/**");
		
		/* 로그인이 필요한 사이트 */
		registry.addInterceptor(interceptorFactory.getRequiredSignin()).addPathPatterns("/Member/Information/**");
		
		/* Authentication 없으면 생성 */
		registry.addInterceptor(interceptorFactory.getAuthenticationSessionCreator()).addPathPatterns("/**");
		
		/* 관리자 페이지 authority에 따른 접근 설정 */
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
