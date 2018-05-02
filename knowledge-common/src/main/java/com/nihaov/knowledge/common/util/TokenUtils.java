package com.nihaov.knowledge.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by nihao on 18/4/27.
 */
public class TokenUtils {
    public static Integer getUserId(HttpServletRequest request){
        String token = request.getHeader("X-Token");
        if(token != null){
            try {
                return Integer.parseInt(DesUtils.decrypt(token));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
