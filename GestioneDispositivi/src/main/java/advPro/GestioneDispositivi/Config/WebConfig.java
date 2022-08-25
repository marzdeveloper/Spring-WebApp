package advPro.GestioneDispositivi.Config;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;


import advPro.GestioneDispositivi.Test.DataServiceConfig;
import advPro.GestioneDispositivi.Test.DataServiceConfigTest;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "advPro.GestioneDispositivi" },
excludeFilters  = {@ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE, classes = {DataServiceConfig.class, DataServiceConfigTest.class})})
public class WebConfig implements WebMvcConfigurer {
	
	@Bean
	public String appName() {
		return "Gestioni Dispositivi";
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/media/**").addResourceLocations("/WEB-INF/media/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/")
			.setCachePeriod(31556926);
		registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
		.setCachePeriod(31556926);
	}

	@Bean 
	StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Bean
	UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}

	@Bean
	TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions(
				"/WEB-INF/layouts/layouts.xml",
				"/WEB-INF/views/**/views.xml",
				"/WEB-INF/views/views.xml"
		);
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}
	
	
	@Override
	public void addFormatters(FormatterRegistry formatterRegistry) {			
		formatterRegistry.addFormatterForFieldType(LocalDate.class, this.dateFormatter());
	}

	@Bean
	public DateFormatter dateFormatter() {
		return new DateFormatter("dd/MM/YYYY");
	}

	@Bean
	ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(themeChangeInterceptor());
		registry.addInterceptor(webChangeInterceptor());
	}

	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("lang");
		return interceptor;
	}

	@Bean ResourceBundleThemeSource themeSource() {
		return new ResourceBundleThemeSource();
	}

	@Bean
	ThemeChangeInterceptor themeChangeInterceptor() {
		return new ThemeChangeInterceptor();
	}
	

	@Bean
	WebContentInterceptor webChangeInterceptor() {
		WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		webContentInterceptor.setSupportedMethods("GET", "POST", "PUT", "DELETE");
		return webContentInterceptor;
	}

	@Bean
	CookieLocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.ITALIAN);
		cookieLocaleResolver.setCookieMaxAge(3600);
		cookieLocaleResolver.setCookieName("locale");
		return cookieLocaleResolver;
	}

	@Bean
	CookieThemeResolver themeResolver() {
		CookieThemeResolver cookieThemeResolver = new CookieThemeResolver();
		cookieThemeResolver.setDefaultThemeName("standard");
		cookieThemeResolver.setCookieMaxAge(3600);
		cookieThemeResolver.setCookieName("theme");
		return cookieThemeResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
	}
}
