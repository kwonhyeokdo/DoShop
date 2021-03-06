package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
	
	@Bean
    public MultipartResolver multipartResolver() {
		final int MAX_SIZE = 10 * 1024 * 1024;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_SIZE); // 10MB
		multipartResolver.setMaxUploadSizePerFile(MAX_SIZE); // 10MB
		multipartResolver.setMaxInMemorySize(0);
      return multipartResolver;
    }
	

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:8090");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* ?????? ??????????????? SigninSession ??????  */
		registry.addInterceptor(interceptorFactory.getSigninSessionUpdate()).addPathPatterns("/**");

		/* autoSignin  */
		registry.addInterceptor(interceptorFactory.getAutoSignin()).addPathPatterns("/**");
		
		/* ???????????? ????????? ????????? */
		registry.addInterceptor(interceptorFactory.getRequiredSignin()).addPathPatterns("/Member/UpdateInformation/**");
		
		/* Authentication ????????? ?????? */
		registry.addInterceptor(interceptorFactory.getAuthenticationSessionCreator()).addPathPatterns("/**");
		
		/* ????????? ????????? authority??? ?????? ?????? ?????? */
		// ?????? ?????? ??????: 1, 2, 3
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return authority != 0 ? true : false;
			}
		})).addPathPatterns("/Admin/**").excludePathPatterns("/Admin/Signin");
		
		// ?????? ?????? ??????: 1
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return authority == 1 ? true : false;
			}
		})).addPathPatterns("/Admin/AdminManagement/**");
		
		// ?????? ?????? ??????: 1, 2
		registry.addInterceptor(interceptorFactory.getEnterAdminPage(new ConditionForAdminEntry() {
			@Override
			public boolean condition(int authority) {
				return (authority >= 1 && authority <= 2) ? true : false;
			}
		})).addPathPatterns("/Admin/CategoryManagement/**");
	}
}
