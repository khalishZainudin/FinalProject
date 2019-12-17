package my.edu.fsktm.um.finalproject.ForumPage;

import com.google.firebase.Timestamp;

public class Messages {
    private String messages;
    private String email;
    private com.google.firebase.Timestamp timePosted;
    private String images;

    public Messages() {
    }

    public Messages(String messages, String email, Timestamp timePosted, String images) {
        this.messages = messages;
        this.email = email;
        this.timePosted = timePosted;
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Timestamp timePosted) {
        this.timePosted = timePosted;
    }
}
