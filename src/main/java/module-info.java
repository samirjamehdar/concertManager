module com.example.databas_gruppupp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
//    requires java.persistence;
    requires jakarta.persistence;
    requires java.naming;
    requires mysql.connector.j;
    requires java.sql;
    requires junit;


    exports com.example.databas_gruppupp;
    exports com.example.databas_gruppupp.FXLoginTest;
    opens com.example.databas_gruppupp.FXLoginTest to javafx.fxml, org.hibernate.orm.core;
    exports com.example.databas_gruppupp.entities;
    opens com.example.databas_gruppupp.entities to javafx.fxml, org.hibernate.orm.core;
    opens com.example.databas_gruppupp.Admin to javafx.fxml, org.hibernate.orm.core, javafx.graphics;
    exports com.test;
    opens com.example.databas_gruppupp to javafx.fxml, javafx.graphics, org.hibernate.orm.core;
}