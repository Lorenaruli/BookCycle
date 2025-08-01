package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.DaoException;
import it.uniroma2.eu.bookcycle.model.dao.LibroDao;
import it.uniroma2.eu.bookcycle.model.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class LibroScambioDaoMemory extends LibroDaoMemory{
    private static LibroScambioDaoMemory instanza;

    public static LibroScambioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new LibroScambioDaoMemory();

        }
        return instanza;
    }
};

