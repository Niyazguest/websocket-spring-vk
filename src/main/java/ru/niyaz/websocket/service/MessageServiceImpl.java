package ru.niyaz.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.niyaz.websocket.dto.MessageDTO;
import ru.niyaz.websocket.dto.UserInfo;
import ru.niyaz.websocket.entity.Author;
import ru.niyaz.websocket.entity.Message;
import ru.niyaz.websocket.repository.MessagesRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 23.04.2016.
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessagesRepository messagesRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setDate(messageDTO.getDate());
        messagesRepository.saveMessage(message, authenticationService.getCurrentUserID());
    }

    public List<MessageDTO> getAllMessages() {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MessageDTO> getMessagesBeforeDate(Date date) {
        return converMessagesToDTO(messagesRepository.getMessagesBeforeDate(date));
    }

    private List<MessageDTO> converMessagesToDTO(List<Message> messages) {
        List<MessageDTO> messageDTOList = new ArrayList<MessageDTO>();
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessage(message.getMessage());
            messageDTO.setDate(message.getDate());
            messageDTO.setAuthor(converAuthorToDTO(message.getAuthor()));
            messageDTOList.add(messageDTO);
        }
        return messageDTOList;
    }

    private UserInfo converAuthorToDTO(Author author) {
        UserInfo userInfo = new UserInfo(author.getFirstName(),
                author.getLastName(),
                author.getAvatarURL(),
                author.getVkUserID(),
                author.getPageURL());
        return userInfo;
    }
}
