package ru.niyaz.websocket.service;

import ru.niyaz.websocket.dto.MessageDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 23.04.2016.
 */
public interface MessageService {
    void saveMessage(MessageDTO messageDTO);
    List<MessageDTO> getAllMessages();
    List<MessageDTO> getMessagesBeforeDate(Date date);
}
