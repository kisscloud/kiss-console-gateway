package com.kiss.apigateway.filter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import kiss.foundation.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class JWTControllerFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String uri = request.getRequestURI();
        if (!uri.contains("/login") && !uri.contains("/root/check") && !uri.contains("/root") && !uri.contains("/merchant")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("【校验JWT】准备校验");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("X-Access-Token");

        if (StringUtils.isEmpty(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            return null;
        }

        //解析token获取用户信息
        Date expired = JwtUtil.getExpired(token);

        if (expired.before(new Date())) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
        }

        return null;
    }
}
