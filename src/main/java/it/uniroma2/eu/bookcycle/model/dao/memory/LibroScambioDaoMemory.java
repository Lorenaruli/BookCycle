package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.LibroScambioDao;

public class LibroScambioDaoMemory extends LibroDaoMemory implements LibroScambioDao {
    private static LibroScambioDaoMemory instanza;

    public static LibroScambioDaoMemory ottieniIstanza(){
        if (instanza == null){
            instanza = new LibroScambioDaoMemory();

        }
        return instanza;
    }
}

