package aor.paj.ctn.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement
public class Task {
    @XmlElement
    private String id;
    @XmlElement
    private String title;
    @XmlElement
    private String description;
    @XmlElement
    private int stateId;
    @XmlElement
    private int priority;
    @XmlElement
    private LocalDate startDate;
    @XmlElement
    private LocalDate limitDate;
    @XmlElement
    public static final int TODO = 100;
    @XmlElement
    public static final int DOING = 200;
    @XmlElement
    public static final int DONE = 300;
    @XmlElement
    public static final int LOWPRIORITY = 100;
    @XmlElement
    public static final int MEDIUMPRIORITY = 200;
    @XmlElement
    public static final int HIGHPRIORITY = 300;
    @XmlElement
    public Category category;
    @XmlElement
    public boolean erased;
    @XmlElement
    public User owner;

    public Task() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generateId() {
        this.id = String.valueOf(System.currentTimeMillis());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public void setInitialStateId() {
        this.stateId = TODO;
    }

    public void editStateId(int stateId) {
        if (stateId == TODO) {
            this.stateId = TODO;
        } else if (stateId == DOING) {
            this.stateId = DOING;
        } else {
            this.stateId = DONE;
        }

    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority == LOWPRIORITY) {
            this.priority = LOWPRIORITY;
        } else if (priority == MEDIUMPRIORITY) {
            this.priority = MEDIUMPRIORITY;
        } else if (priority == HIGHPRIORITY) {
            this.priority = HIGHPRIORITY;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getErased() {
        return erased;
    }

    public void setErased(boolean erased) {
        this.erased = erased;
    }
}