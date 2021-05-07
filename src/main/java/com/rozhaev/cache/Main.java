package com.rozhaev.cache;

public class Main {
    public static void main(String[] args) {
        var cache = new FutureCache<String, String>();
        try {
            cache.setValue("A", "B1");
            cache.setValue("A", "B1");
            System.out.println(cache.getValue("A", String::new));
            System.out.println(cache.getValue("B", String::new));
        } catch (Exception e) {
            System.out.println("=)");
        }
    }
}
