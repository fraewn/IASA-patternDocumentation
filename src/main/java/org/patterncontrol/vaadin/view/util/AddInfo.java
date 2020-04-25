package org.patterncontrol.vaadin.view.util;

public class AddInfo {
    public static String getDescription(String s){
        switch (s){
            case "calldaoread":
                return "The class class a read method from the DAO class";
            case "calldaocreate":
                return "The class class a create method from the DAO class";
            case "implementsequalsmethod":
                return "The class implements a equals method";
            case "constructorprivate":
                return "The visibility of the constructor is private";
            case "privatestaticclassvar":
                return "The class owns a private static class variable in which the Singleton class is saved";
            case "gettermethod":
                return "The class owns a getter-method that returns the Singleton object";
            case "synchronized":
                return "The class is set to synchronized";
            default:
                return null;

        }

    }
}
