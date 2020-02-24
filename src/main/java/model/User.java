package model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    private Long id;
    private String name;
    private String FIO;
    private String tel;
    private String e_mail;
    private String password;

    public User() {
    }

    public User(Long id, String name, String FIO, String tel, String e_mail, String password) {
        this.id = id;
        this.name = name;
        this.FIO = FIO;
        this.tel = tel;
        this.e_mail = e_mail;
        this.password = password;
    }

    public User(String name, String FIO, String tel, String e_mail, String password) {
        this.name = name;
        this.FIO = FIO;
        this.tel = tel;
        this.e_mail = e_mail;
        this.password = password;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private transient List<Message> messageList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                name.equals(user.name) &&
                FIO.equals(user.FIO) &&
                tel.equals(user.tel) &&
                e_mail.equals(user.e_mail) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, FIO, tel, e_mail, password);
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
