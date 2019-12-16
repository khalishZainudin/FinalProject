package my.edu.fsktm.um.finalproject.ForumPage;

import com.google.firebase.Timestamp;

public class Forum {
    private String Title;
    private String Description;
    private String User;
    private com.google.firebase.Timestamp DatePosted;

    public Forum() {
    }

    public Forum(String title, String description, Timestamp datePosted,String user) {
        Title = title;
        Description = description;
        DatePosted = datePosted;
        User = user;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Timestamp getDatePosted() {
        return DatePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        DatePosted = datePosted;
    }
}

