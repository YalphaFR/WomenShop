module com.example.womenshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.womenshop to javafx.fxml;
    exports com.example.womenshop;
    exports com.example.womenshop.controller;
    opens com.example.womenshop.controller to javafx.fxml;
    exports com.example.womenshop.repository.mysql;
    opens com.example.womenshop.repository.mysql to javafx.fxml;
    exports com.example.womenshop.repository;
    opens com.example.womenshop.repository to javafx.fxml;
}
