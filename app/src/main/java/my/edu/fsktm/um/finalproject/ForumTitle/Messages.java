package my.edu.fsktm.um.finalproject.ForumTitle;

import com.google.firebase.Timestamp;
import com.google.type.Date;

public class Messages {
    private String messages;
    private String user;
    private com.google.firebase.Timestamp timePosted;
    private String images;

    public Messages() {
    }

    public Messages(String messages, String user, Timestamp timePosted, String images) {
        this.messages = messages;
        this.user = user;
        this.timePosted = timePosted;
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Timestamp timePosted) {
        this.timePosted = timePosted;
    }
}
