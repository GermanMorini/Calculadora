module german.calculadora1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires exp4j;

    opens german.calculadora1 to javafx.fxml;
    exports german.calculadora1;
}