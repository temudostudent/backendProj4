package aor.paj.ctn.bean;

import aor.paj.ctn.dto.Comment;
import aor.paj.ctn.dto.Retrospective;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

@ApplicationScoped
public class RetrospectiveBean {
    final String filename = "retrospectives.json";
    private ArrayList<Retrospective> retrospectives;

    public RetrospectiveBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                retrospectives = JsonbBuilder.create().fromJson(filereader, new ArrayList<Retrospective>() {
                }.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            retrospectives = new ArrayList<Retrospective>();
    }

    public boolean addRetrospective(Retrospective retrospective) {
        boolean added = true;
        if (retrospective.getTitle().isBlank() && retrospective.getDate() == null) {
            added = false;
        } else {
            retrospective.setId(retrospective.generateId());
            retrospective.setTitle(retrospective.getTitle());
            retrospective.setDate(retrospective.getDate());
            retrospectives.add(retrospective);
            writeIntoJsonFile();
        }
        return added;
    }


    public boolean addCommentToRetrospective(String id, Comment comment) {
        boolean added = true;
        if (comment.getDescription().isBlank() && comment.getUser() == null && !validateCommentStatus(comment)) {
            added = false;
        } else {
            for (Retrospective a : retrospectives) {
                if (a.getId().equals(id)) {
                    comment.generateId();
                    a.addComment(comment);
                    a.addUser(comment.getUser());
                    writeIntoJsonFile();
                }
            }
        }
        return added;
    }

    public Retrospective getRetrospective(String id) {
        Retrospective retrospective = null;
        boolean found = false;
        while (!found) {
            for (Retrospective a : retrospectives) {
                if (a.getId().equals(id)) {
                    retrospective = a;
                    found = true;
                }
            }
        }
        return retrospective;
    }

    public ArrayList<Retrospective> getRetrospectives() {
        return retrospectives;
    }

    public ArrayList<Comment> getComments(String id) {
        ArrayList<Comment> comment = null;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                comment = a.getRetrospectiveComments();
            }
        }
        return comment;
    }

    public Comment getComment(String id, String commentId) {
        Comment comment = null;
        for (Retrospective a : retrospectives) {
            if (a.getId().equals(id)) {
                for (Comment c : a.getRetrospectiveComments()) {
                    if (c.getId().equals(commentId)) {
                        comment = c;
                    }
                }
            }
        }
        return comment;
    }

    public boolean validateCommentStatus(Comment comment) {
        boolean valid = true;
        if (comment.getCommentStatus() != Comment.STRENGTHS && comment.getCommentStatus() != Comment.CHALLENGES && comment.getCommentStatus() != Comment.IMPROVEMENTS) {
            valid = false;
        }
        return valid;
    }


    private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new
                JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(retrospectives, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}