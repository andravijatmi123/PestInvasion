module com.javafx.wkwk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.media;
    
    opens com.javafx.wkwk to javafx.fxml;  // This is already correct
    opens model to javafx.fxml, javafx.base;  // Make sure model package is open to both javafx.fxml and javafx.base
    exports com.javafx.wkwk;
}
