package ru.niyaz.websocket.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by user on 23.04.2016.
 */

@Entity
@Table(name="authors")
public class Author {

    @Id
    @SequenceGenerator(name = "author_seq", sequenceName = "authors_id_seq", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "vk_user_id")
    private Integer vkUserID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "avatar_url")
    private String avatarURL;

    @Column(name = "page_url")
    private String pageURL;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "author")
    private Set<Message> messages;

    public Author() {

    }

    public Author(Integer vkUserID, String firstName, String lastName, String pageURL, String avatarURL) {
        this.vkUserID = vkUserID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pageURL = pageURL;
        this.avatarURL = avatarURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVkUserID() {
        return vkUserID;
    }

    public void setVkUserID(Integer vkUserID) {
        this.vkUserID = vkUserID;
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

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
