package aor.paj.ctn.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {
    @XmlElement
    private String id;
    @XmlElement
    private String description;
    @XmlElement
    private User user;
    @XmlElement
    private int commentStatus;
    @XmlElement
    public static final int STRENGTHS = 100;
    @XmlElement
    public static final int CHALLENGES = 200;
    @XmlElement
    public static final int IMPROVEMENTS = 300;


    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String generateId() {
        this.id = String.valueOf(System.currentTimeMillis());
        return this.id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(int commentStatus) {
        this.commentStatus = commentStatus;
    }

    public void generateCommentId(int stateId) {
        if (stateId == STRENGTHS) {
            this.commentStatus = STRENGTHS;
        } else if (stateId == CHALLENGES) {
            this.commentStatus = CHALLENGES;
        } else if (stateId == IMPROVEMENTS) {
            this.commentStatus = IMPROVEMENTS;
        }
    }
}