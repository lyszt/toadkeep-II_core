module tessera.toadkeep.toadkeepii {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens tessera.toadkeep.toadkeepii to javafx.fxml;
    exports tessera.toadkeep.toadkeepii;
}