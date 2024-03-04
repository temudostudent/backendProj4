package aor.paj.ctn.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class Login {

    @XmlElement
    String username;
    @XmlElement
    String password;

    public Login() {}

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

}
