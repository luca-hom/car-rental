package ch.juventus.carrental.service;

import java.util.List;

public class Car {
    private Long id;
    private String name;

    private enum type {SUV, VAN};
    private enum gearShift {manual, automatic};
    private Integer seats;
    private Double pricePerDay;
    private Boolean airCondition;
    private List<Rental> rentals;

}


