package com.Smart.Service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public void removeMessageFromSession() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            
            if (attr != null) { // Check if attributes are available
                HttpSession session = attr.getRequest().getSession();
                
                System.out.println("Removing the message!!");
                
                session.removeAttribute("message");
            } else {
                System.err.println("No request attributes available. Ensure this method is called within an HTTP request context.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
