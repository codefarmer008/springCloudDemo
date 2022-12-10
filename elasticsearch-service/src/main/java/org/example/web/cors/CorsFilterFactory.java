package org.example.web.cors;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CorsFilterFactory implements FactoryBean<FilterRegistrationBean> {
    private static final long ONE_HOUR = 3600L;
    private final CorsFilter corsFilter;
    private int order;

    public CorsFilterFactory() {
        this((String)null);
    }

    public CorsFilterFactory(String origin) {
        this(origin, false);
    }

    public CorsFilterFactory(String origin, boolean debug) {
        this.order = -2147483648;
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        if (!StringUtils.isEmpty(origin)) {
            config.setAllowedOrigins((List) Stream.of(origin.split(",")).collect(Collectors.toList()));
            if (!Objects.equals("*", origin)) {
                config.setAllowCredentials(true);
            }
        } else {
            ArrayList<String> list = new ArrayList<>();
            list.add("*");
            config.setAllowedOrigins(list);
        }

        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        if (debug && Objects.nonNull(origin) && (origin.contains("127.0.0.1") || origin.contains("localhost"))) {
            config.addAllowedMethod(HttpMethod.OPTIONS);
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("Content-Length");
        list.add("Content-Disposition");
        list.add("Authorization");
        config.setExposedHeaders(list);
        config.addAllowedHeader("*");
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        this.corsFilter = new InnerCorsFilter(source, origin, debug);
    }

    public FilterRegistrationBean getObject() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns(new String[]{"/*"});
        bean.setName("accessControlAllowFilter");
        bean.setFilter(this.corsFilter);
        bean.setOrder(this.order);
        return bean;
    }

    public Class<?> getObjectType() {
        return FilterRegistrationBean.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
