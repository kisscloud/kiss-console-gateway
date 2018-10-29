package com.kiss.apigateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiss.apigateway.util.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class JWTControllerFilter extends ZuulFilter {
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
        if (!uri.contains("/login")) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("【校验JWT】准备校验");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        String token = request.getHeader("X-Access-Token");
//        requestContext.setRequest();

        if (StringUtils.isEmpty(token)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            return null;
        }

        //解析token获取用户信息
        String username = JwtUtil.getUserName(token);
        String userId = JwtUtil.getUserId(token);
        Date expired = JwtUtil.getExpired(token);

        if (expired.before(new Date())) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            return null;
        }

        try {

            InputStream in = (InputStream) requestContext.get("requestEntity");
            if (in == null) {
                in = requestContext.getRequest().getInputStream();
            }
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            Map<String,Object> map = new HashMap<>();

            if(!StringUtils.isEmpty(body)) {
                map= (Map)JSONObject.parseObject(body);
            }

            map.put("GateUsername",username);
            map.put("GateUserId",Integer.parseInt(userId));
            String mapStr = JSON.toJSONString(map);

            byte[] bytes = mapStr.getBytes("UTF-8");
            requestContext.setRequest(new HttpServletRequestWrapper(request) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(bytes);
                }

                @Override
                public int getContentLength() {
                    return bytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return bytes.length;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
