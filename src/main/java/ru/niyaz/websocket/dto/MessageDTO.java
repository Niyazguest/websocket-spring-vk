package ru.niyaz.websocket.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 16.04.2016.
 */
public class MessageDTO implements Serializable {
    private String message;
    private Date date;
    private UserInfo author;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public MessageDTO() {

    }

    public MessageDTO(String message) {
        this.message = message;
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

    public String getDateFormatted() {
        return dateFormat.format(date);
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }
}
