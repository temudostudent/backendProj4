package aor.paj.ctn.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;

@XmlRootElement
public class Retrospective {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private LocalDate date;
    private ArrayList<User> retrospectiveUsers = new ArrayList<>();
    @XmlElement
    private ArrayList<Comment> retrospectiveComments = new ArrayList<>();


    public Retrospective() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String generateId() {
        this.id = String.valueOf(System.currentTimeMillis());
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = LocalDate.now();
    }

    public void addUser(User user) {
        retrospectiveUsers.add(user);
    }

    public ArrayList<User> getRetrospectiveUsers() {
        return retrospectiveUsers;
    }

    public void addComment(Comment comment) {
        retrospectiveComments.add(comment);
    }

    public ArrayList<Comment> getRetrospectiveComments() {
        return retrospectiveComments;
    }
}
