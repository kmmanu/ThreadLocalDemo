package org.manu.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.manu.threadlocal.context.RequestContext;
import org.manu.threadlocal.context.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class UserNameFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        logHttpValues(servletRequest, httpRequest);

        createRequestContext(httpRequest);

        filterChain.doFilter(servletRequest, response);
    }

    private void createRequestContext(HttpServletRequest request) {
        Map<String, String> headerMap = getHeaderMap(request);
        Map<String, String> requestParameters = getRequestParameters(request);

        String userName = headerMap.getOrDefault("name", requestParameters.getOrDefault("name", ""));
        RequestContext.set(new User(userName));
    }

    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        log.info("Headers are  : {}", headerMap);
        return headerMap;
    }

    private Map<String, String> getRequestParameters(ServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> requestParamMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            requestParamMap.put(parameterName, request.getParameter(parameterName));
        }
        log.info("Request params = {}", requestParamMap);
        return requestParamMap;
    }

    @Override
    public void destroy() {

    }

    private void logHttpValues(ServletRequest servletRequest, HttpServletRequest httpRequest) {
        String url = httpRequest.getRequestURL().toString();
        String query = httpRequest.getQueryString();

        String scheme = servletRequest.getScheme();
        String serverName = servletRequest.getServerName();
        int portNumber = servletRequest.getServerPort();

        log.info("Url: " + url + "<br/>");
        log.info("Scheme: " + scheme + "<br/>");
        log.info("Server Name: " + serverName + "<br/>");
        log.info("Port: " + portNumber + "<br/>");
        log.info("Query: " + query);
    }
}
