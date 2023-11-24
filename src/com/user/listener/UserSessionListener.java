package com.user.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

@WebListener
public class UserSessionListener implements HttpSessionListener {
    private static HashMap<String,HttpSession> activeSessions=new HashMap<>();

    public static void addActiveMap(String userName,HttpSession session){
        activeSessions.put(userName,session);
    }
    public static boolean isUsernameInActiveSessions(String userName) {
        return activeSessions.containsKey(userName);
    }
    public static HashMap<String, HttpSession> getActiveSessions(){
        return activeSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session= httpSessionEvent.getSession();
        String username = (String) session.getAttribute("userName");
        if (username != null) {
            activeSessions.remove(username);
        }

    }
}
