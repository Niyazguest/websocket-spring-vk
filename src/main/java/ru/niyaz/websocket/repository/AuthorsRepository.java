package ru.niyaz.websocket.repository;

import ru.niyaz.websocket.entity.Author;

/**
 * Created by user on 23.04.2016.
 */
public interface AuthorsRepository {
    Long saveAuthor(Author author);
    Author getAuthorByID(Long id);
    Author getAuthorByVkID(Integer vkUserID);
}
