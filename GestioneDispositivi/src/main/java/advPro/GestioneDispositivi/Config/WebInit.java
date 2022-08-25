package advPro.GestioneDispositivi.Config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import advPro.GestioneDispositivi.Security.WebSecurityConfig;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// NB 1st level config class
		return new Class<?>[]{
			DataServiceConfigWeb.class, 
			WebSecurityConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// NB 2nd level config class
		return new Class<?>[]{
				WebConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter cef = new CharacterEncodingFilter();
		cef.setEncoding("UTF-8");
		cef.setForceEncoding(true);
		return new Filter[]{new HiddenHttpMethodFilter(), cef};
	}
}
