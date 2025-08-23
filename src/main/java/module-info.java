module it.uniroma2.eu.bookcycle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    exports it.uniroma2.eu.bookcycle.controller.grafica.gui to javafx.fxml;
    exports it.uniroma2.eu.bookcycle.controller.grafica.gui2 to javafx.fxml;
    exports it.uniroma2.eu.bookcycle.controller.grafica.guicomune to javafx.fxml;

    opens it.uniroma2.eu.bookcycle.controller.grafica.gui to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.controller.grafica.gui2 to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.controller.grafica.guicomune to javafx.fxml;

    opens it.uniroma2.eu.bookcycle.model.domain to javafx.base;
    opens it.uniroma2.eu.bookcycle.bean to javafx.base;

    exports it.uniroma2.eu.bookcycle;
    opens it.uniroma2.eu.bookcycle to javafx.fxml;

    // === Esportazioni extra per i test ===
    exports it.uniroma2.eu.bookcycle.model.dao;
    exports it.uniroma2.eu.bookcycle.model.dao.file;
    exports it.uniroma2.eu.bookcycle.model.dao.memory;
    exports it.uniroma2.eu.bookcycle.model.domain;
    exports it.uniroma2.eu.bookcycle.model.eccezioni;
    exports it.uniroma2.eu.bookcycle.bean;
    exports it.uniroma2.eu.bookcycle.controller.applicativo;
}