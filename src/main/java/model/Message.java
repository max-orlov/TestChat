package model;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message implements Comparable<Message>{

    private Long id;
    private Date date;
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Message() {
    }

    public Message(Date date, String text, User user) {
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public Message(Long id, Date date, String text, User user) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int compareTo(Message o) {
        return this.date.compareTo(o.getDate());
    }
}
