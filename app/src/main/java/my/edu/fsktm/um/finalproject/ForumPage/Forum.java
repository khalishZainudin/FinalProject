package my.edu.fsktm.um.finalproject.ForumPage;

import com.google.firebase.Timestamp;

public class Forum {
    private String Title;
    private String Description;
    private String Email;
    private com.google.firebase.Timestamp DatePosted;

    public Forum() {
    }

    public Forum(String title, String description, String email, Timestamp datePosted) {
        Title = title;
        Description = description;
        Email = email;
        DatePosted = datePosted;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

