package itmo.spring.meeting.back.model.entities;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Entity(name = "users")
@Component
@RequestScope
public class User {
    @Column(unique=true)
    private String username;
    private byte[] hash;
    private byte[] salt;

    @Transient
    private String password;

    @Id
    @GeneratedValue
    private Long id;

    public User(String username, byte[] hash, byte[] salt) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){

    }

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

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] password) {
        this.hash = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
