package my.edu.fsktm.um.finalproject;

import java.sql.Timestamp;

public class Messages {
    private String name, messages,time ;
    public Messages(){
    }

    public Messages(String name, String messages, String time) {
        this.name = name;
        this.messages = messages;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessage(String message) {
        this.messages = message;
    }

    public String getTime() {
        return time;
    }

    public void setTimestamp(String timestamp) {
        this.time = timestamp;
    }
}
