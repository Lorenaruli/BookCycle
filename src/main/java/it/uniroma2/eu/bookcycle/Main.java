package it.uniroma2.eu.bookcycle;

import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;

import java.io.InputStream;

import static javafx.application.Application.launch;

public class Main {

    public void main(String[] args){
        aggiornaIdCounter();
        InputStream input = FactoryDao.class.getClassLoader().getResourceAsStream("proprieta.properties");
        System.out.println("Trovato file: " + (input != null));
        launch(App.class);
    }

    private void aggiornaIdCounter(){
//        if (FactoryDao.getInstance().getModalita().equals("FILE")) {
//            LibroId.aggiornaIdCounter(...);
//        } else {
//            Libro.setIdCounter(0);
//        }
//
    }




    }