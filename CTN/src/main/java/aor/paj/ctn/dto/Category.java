package aor.paj.ctn.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class Category {
    @XmlElement
    private int id;
    @XmlElement
    private String name;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
