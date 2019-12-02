package my.edu.fsktm.um.finalproject.ForumTitle;

import com.google.firebase.Timestamp;

public class Forum {
    private String Title;
    private String Description;
    private com.google.firebase.Timestamp DatePosted;

    public Forum() {
    }

    public Forum(String title, String description, Timestamp datePosted) {
        Title = title;
        Description = description;
        DatePosted = datePosted;
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

