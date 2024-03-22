package aor.paj.ctn.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name="user")
@NamedQuery(name = "User.findAllUsers", query = "SELECT u FROM UserEntity u WHERE u.username <> 'ADMIN'")
@NamedQuery(name = "User.findAllUsersByTypeOfUser", query = "SELECT u FROM UserEntity u WHERE u.typeOfUser = :typeOfUser AND u.username <> 'ADMIN'")
@NamedQuery(name = "User.findAllUsersByVisibility", query = "SELECT u FROM UserEntity u WHERE u.visible = :visible AND u.username <> 'ADMIN'")
@NamedQuery(name = "User.findAllUsersByTypeOfUserByVisibility", query = "SELECT u FROM UserEntity u WHERE u.typeOfUser = :typeOfUser AND u.visible = :visible  AND u.username <> 'ADMIN'")
@NamedQuery(name = "User.findUserByUsername", query = "SELECT u FROM UserEntity u WHERE u.username = :username")
@NamedQuery(name = "User.findUserByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email AND u.username <> 'ADMIN'")
@NamedQuery(name = "User.findUserByPhone", query = "SELECT  u FROM UserEntity u WHERE u.phone = :phone AND u.username <> 'ADMIN'")
@NamedQuery(name = "User.findUserByToken", query = "SELECT DISTINCT u FROM UserEntity u WHERE u.token = :token")
@NamedQuery(name = "User.findUserByUsernameAndPassword", query = "SELECT u FROM UserEntity u WHERE u.username = :username AND u.password = :password")
public class UserEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    //user unique username has ID - not updatable, unique, not null
    @Id
    @Column(name="username", nullable=false, unique = true, updatable = false)
    private String username;

    @Column(name="password", nullable=false, unique = false, updatable = true)
    private String password;

    @Column(name="type_of_user", nullable=false, unique = false, updatable = true)
    private int typeOfUser;

    @Column(name="email", nullable=false, unique = true, updatable = true)
    private String email;

    @Column(name="first_name", nullable=false, unique = false, updatable = true)
    private String firstName;

    @Column(name="last_name", nullable=false, unique = false, updatable = true)
    private String lastName;

    @Column(name="phone", nullable=false, unique = true, updatable = true)
    private String phone;

    @Column(name="photo_url", nullable=true, unique = false, updatable = true)
    private String photoURL;

    @Column(name="token", nullable=true, unique = true, updatable = true)
    private String token;

    @Column(name="visible", nullable = false, unique = false, updatable = true)
    private boolean visible;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TaskEntity> userTasks;


    //default empty constructor
    public UserEntity() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(int typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<TaskEntity> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(Set<TaskEntity> userTasks) {
        this.userTasks = userTasks;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVisible() {return visible;}

    public void setVisible(boolean visivel) {this.visible = visivel;}

    public void addNewTasks(ArrayList<TaskEntity> tasks){
        for(TaskEntity t: tasks)
            userTasks.add(t);
    }
}
