package com.kiss.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class PermissionControlFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //requestContext.setSendZuulResponse(false);
        //requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);

        // 1. 获取请求的方法 GET/POST/PUT/DELETE
        String method = request.getMethod();
        System.out.println(method);
        // 2. 获取请求的 URI
//        String uri="/users/1";
        String uri= request.getRequestURI();
        String routePermission = method+"@"+uri;
        System.out.println(uri);
        // 3. 通过 URI 匹配用户权限
        // Params("id") == 1
        String token = request.getHeader("Authorization");

        return null;
    }
}
