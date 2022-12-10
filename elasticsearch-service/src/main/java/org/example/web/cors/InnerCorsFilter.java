package org.example.web.cors;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InnerCorsFilter extends CorsFilter {
    public static final String ALL_ORIGIN = "*";
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
    private final List<String> origin;
    private final List<String> domains;
    private final boolean debug;

    public InnerCorsFilter(CorsConfigurationSource configSource) {
        this(configSource, (String)null);
    }

    public InnerCorsFilter(CorsConfigurationSource configSource, String origin) {
        this(configSource, origin, false);
    }

    public InnerCorsFilter(CorsConfigurationSource configSource, String origin, boolean debug) {
        super(configSource);
        this.origin = isNotBlank(origin) ? (List) Stream.of(origin.split(",")).distinct().collect(Collectors.toList()) : Collections.emptyList();
        this.debug = debug;
        if (this.origin != null && !this.origin.isEmpty()) {
            this.domains = (List)this.origin.stream().map((o) -> {
                try {
                    return getTopDomain(new URL(o));
                } catch (MalformedURLException var2) {
                    return "";
                }
            }).filter(o->isNotBlank(o)).distinct().collect(Collectors.toList());
        } else {
            this.domains = Collections.emptyList();
        }

    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.origin != null && !this.origin.isEmpty() && !this.origin.contains("*")) {
            String referrer = request.getHeader("Referer");
            if (isNotBlank(referrer)) {
                try {
                    if (!this.debug) {
                        if (!this.domains.contains(getTopDomain(new URL(referrer)))) {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            return;
                        }
                    } else if (!this.domains.contains(getTopDomain(new URL(referrer))) && !referrer.contains("127.0.0.1") && !referrer.contains("localhost")) {
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        return;
                    }
                } catch (MalformedURLException var6) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    return;
                }
            }
        }

        super.doFilterInternal(request, response, filterChain);
    }

    private static String getTopDomain(URL url) {
        String host = url.getHost().toLowerCase();
        Matcher matcher = DOMAIN_PATTERN.matcher(host);
        return matcher.find() ? matcher.group() : null;
    }
    
    private boolean isNotBlank(String a){
        return !StringUtils.isEmpty(a);
    }
}
