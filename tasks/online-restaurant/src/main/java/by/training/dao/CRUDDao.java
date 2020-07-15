package by.training.dao;

import by.training.contact.ContactNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDDao<ENTITY, KEY> {
    KEY save(ENTITY entity) throws DaoException;

    boolean update(ENTITY entity) throws DaoException;

    boolean delete(KEY id) throws DaoException;

    ENTITY getById(KEY id) throws DaoException;

    List<ENTITY> findAll() throws DaoException;
}