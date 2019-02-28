#!/bin/sh
#java -jar --module-path target/classes/javafx-sdk-11.0.1/lib/ --add-modules=javafx.fxml,javafx.controls target/java-fx-application-1.0-SNAPSHOT-jar-with-dependencies.jar
mvn clean compile assembly:single
mvn package