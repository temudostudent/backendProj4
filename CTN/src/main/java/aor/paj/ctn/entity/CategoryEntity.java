package aor.paj.ctn.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="category")
@NamedQuery(name="Category.findTasksByCategory", query="SELECT t FROM CategoryEntity c JOIN c.taskList t WHERE c.name = :name")
@NamedQuery(name="Category.findCategories", query="SELECT a FROM CategoryEntity a")
@NamedQuery(name="Category.findCategoryByName", query="SELECT a FROM CategoryEntity a WHERE a.name = :name")
@NamedQuery(name="Category.findCategoryById", query="SELECT a FROM CategoryEntity a WHERE a.id = :id")
@NamedQuery(name="Category.deleteCategory", query="DELETE FROM CategoryEntity a WHERE a.name = :name")
public class CategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique = true, updatable = false)
    private int id;


    @Column(name="name", nullable=false, unique = true)
    private String name;
    @OneToMany(mappedBy = "category")
    private Set<TaskEntity> taskList;

    public CategoryEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TaskEntity> getTaskList() {
        return taskList;
    }

    public void setTaskList(Set<TaskEntity> task) {
        this.taskList = task;
    }
}
