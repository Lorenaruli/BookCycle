module it.uniroma2.eu.bookcycle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    exports it.uniroma2.eu.bookcycle.controller.gui to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.controller.gui to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.model.domain to javafx.base;
    opens it.uniroma2.eu.bookcycle.bean to javafx.base;
    opens it.uniroma2.eu.bookcycle.controller.gui2 to javafx.fxml;


    opens it.uniroma2.eu.bookcycle to javafx.fxml;
    exports it.uniroma2.eu.bookcycle;


    // li ho aggiiunti per far funzionare i test
    exports it.uniroma2.eu.bookcycle.model.dao.memory;
    exports it.uniroma2.eu.bookcycle.model.dao.file;
    exports it.uniroma2.eu.bookcycle.model.dao;
    exports it.uniroma2.eu.bookcycle.model.domain;
    exports it.uniroma2.eu.bookcycle.controller;
    exports it.uniroma2.eu.bookcycle.bean;
    exports it.uniroma2.eu.bookcycle.model.Eccezioni;

}