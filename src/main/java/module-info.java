module it.uniroma2.eu.bookcycle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    exports it.uniroma2.eu.bookcycle.grafica.gui to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.grafica.gui to javafx.fxml;
    opens it.uniroma2.eu.bookcycle.model.domain to javafx.base;

    opens it.uniroma2.eu.bookcycle to javafx.fxml;
    exports it.uniroma2.eu.bookcycle;
}