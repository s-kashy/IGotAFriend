package com.example.mycomputer.igotafriend;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My computer on 1/12/2018.
 */

public class Chat implements Serializable {

    private String visterUdi;
    private String localUdi;
    private boolean isRate=false;

    public void setRate(boolean rate) {
        isRate = rate;
    }

    public boolean isRate() {

        return isRate;
    }

    private List<ChatMessage> messages;
    @Exclude
    private String key;

    @Exclude
    public String getKey() {
        return key;
    }
    public Chat() {
        messages = new ArrayList<>();
    }


    public List<ChatMessage> getMessages() {
        return messages;
    }



    public String getVisterUdi() {
        return visterUdi;
    }

    public String getLocalUdi() {
        return localUdi;
    }
    public void addMsg(ChatMessage msg){
        this.messages.add(msg);
    }

    public void setVisterUdi(String visterUdi) {
        this.visterUdi = visterUdi;
    }

    public void setChatList(List<ChatMessage> chats) {
        this.messages = chats;
    }

    public void setLocalUdi(String localUdi) {

        this.localUdi = localUdi;
    }

    public List<ChatMessage> getChat() {
        return this.messages;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}
