package com.keyboardforces.ChatService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class SessionManagement {
    private final static Logger LOGGER = LoggerFactory.getLogger(SessionManagement.class);

    private ArrayList<Session> onlineSessionList = new ArrayList<>();

    public void addSession(String sessionId, String nickname){
        Session session = new Session(sessionId, nickname);

        this.onlineSessionList.add(session);

        String infoMessage = getOnlineUsers(this.onlineSessionList);

        LOGGER.info( infoMessage );
    }

    public void removeSession(String sessionId){
        Iterator<Session> cloneOnline = this.onlineSessionList.iterator();

        while (cloneOnline.hasNext()){
            Session session = cloneOnline.next();
            if (session.getSessionId() == sessionId){
                this.onlineSessionList.remove(session);

                String infoMessage = getOnlineUsers(this.onlineSessionList);

                LOGGER.info( infoMessage );

                return;
            }
        }

    }

    private String getOnlineUsers(ArrayList<Session> sessions){
        String infoMessage = "Online User List =>\n";

        Iterator<Session> sessionIterator =sessions.iterator();

        while (sessionIterator.hasNext()){
            Session session1 = sessionIterator.next();
            infoMessage += (session1.toString() + '\n');
        }

        return infoMessage;
    }
}

class Session {
    private String sessionId;
    private String nickname;

    @Override
    public String toString() {
        return "Session{" +
                "sessionId='" + sessionId + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public Session() {
    }

    public Session(String sessionId, String nickname) {

        this.sessionId = sessionId;
        this.nickname = nickname;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}