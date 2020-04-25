package org.patterncontrol.vaadin.model.util;

public class ProjectDatabaseCredentials {
    // add project database credentials here
    // add projectdatabase url here
    public static String projectdatabase_url = "bolt://localhost:7687";
    // add projectdatabase user here
    public static String projectdatabase_username = "";
    // add projectdatabase password
    public static String projectdatabase_password = "";

    public static String getProjectdatabase_url() {
        return projectdatabase_url;
    }

    public static String getProjectdatabase_username() {
        return projectdatabase_username;
    }

    public static String getProjectdatabase_password() {
        return projectdatabase_password;
    }
}
