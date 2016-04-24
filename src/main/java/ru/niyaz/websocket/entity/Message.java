package ru.niyaz.websocket.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by user on 23.04.2016.
 */

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @SequenceGenerator(name = "message_seq", sequenceName = "messages_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    public Message() {

    }

    public Message(String message, Date date, Author author) {
        this.message = message;
        this.date = date;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
