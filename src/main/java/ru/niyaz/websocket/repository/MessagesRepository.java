package ru.niyaz.websocket.repository;

import ru.niyaz.websocket.entity.Message;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 23.04.2016.
 */
public interface MessagesRepository {
    void saveMessage(Message message, Long authorID);
    List<Message> getAllMessages();
    List<Message> getMessagesBeforeDate(Date date);
}
