package com.example.mycomputer.igotafriend;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by My computer on 1/12/2018.
 */

public class ChatMessage implements Serializable {
    private String writer;
    private String key;
    public void setKey(String key) {
        this.key = key;
    }


    @Exclude
    private String textMsg;
    public ChatMessage(){

    }
    @Exclude
    public String getKey() {

        return key;
    }
    public ChatMessage(String writer,String textMsg){
      this.writer=writer;
      this.textMsg=textMsg;
    }

    public String getWriter() {
        return writer;
    }

    public String getTextMsg() {
        return textMsg;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }
}
