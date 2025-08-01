package it.uniroma2.eu.bookcycle.model.dao.memory;

import it.uniroma2.eu.bookcycle.model.dao.memory.LibroDaoMemory;
public class LibroVenNolDaoMemory extends LibroDaoMemory {
    private static LibroVenNolDaoMemory instanza;

    public static LibroVenNolDaoMemory ottieniIstanza() {
        if (instanza == null) {
            instanza = new LibroVenNolDaoMemory();

        }
        return instanza;
    }
}