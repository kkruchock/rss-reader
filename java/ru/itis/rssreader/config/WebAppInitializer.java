package ru.itis.rssreader.config;

import jakarta.servlet.Filter;
import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.itis.rssreader.filter.AuthenticationFilter;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new AuthenticationFilter()};
    }
}