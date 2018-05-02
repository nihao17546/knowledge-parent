package com.nihaov.knowledge.web.handler;

import com.nihaov.knowledge.common.util.TokenUtils;
import com.nihaov.knowledge.web.result.JsonResult;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by nihao on 18/4/27.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        String uri = request.getRequestURI();
        if(uri.endsWith(".needLogin")){
            Integer userId = TokenUtils.getUserId(request);
            if(userId == null){
                JsonResult jsonResult = JsonResult.fail("未登录");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                try(PrintWriter out=response.getWriter()){
                    out.append(jsonResult.json());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
            else{
                request.setAttribute("userId", userId);
            }
        }
        return true;
    }
}
