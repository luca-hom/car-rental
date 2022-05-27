package ch.juventus.carrental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Car {

    private Long id;
    private String name;
    private Type type;
    private GearShift gearShift;
    private Integer seats;
    private Double pricePerDay;
    private Boolean airCondition;
    private ArrayList<Rental> rentals;



    public Car(Long id, String name, Type type, GearShift gearShift, Integer seats, Double pricePerDay, Boolean airCondition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gearShift = gearShift;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
        this.airCondition = airCondition;
    }

    public Car(String name, Type type, GearShift gearShift, Integer seats, Double pricePerDay, Boolean airCondition) {
        this.name = name;
        this.type = type;
        this.gearShift = gearShift;
        this.seats = seats;
        this.pricePerDay = pricePerDay;
        this.airCondition = airCondition;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public GearShift getGearShift() {
        return gearShift;
    }

    public void setGearShift(GearShift gearShift) {
        this.gearShift = gearShift;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Boolean getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(Boolean airCondition) {
        this.airCondition = airCondition;
    }

    public ArrayList<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(ArrayList<Rental> rentals) {
        this.rentals = rentals;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", gearShift=" + gearShift +
                ", seats=" + seats +
                ", pricePerDay=" + pricePerDay +
                ", airCondition=" + airCondition +
                ", rentals=" + rentals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(name, car.name) && type == car.type && gearShift == car.gearShift && Objects.equals(seats, car.seats) && Objects.equals(pricePerDay, car.pricePerDay) && Objects.equals(airCondition, car.airCondition) && Objects.equals(rentals, car.rentals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, gearShift, seats, pricePerDay, airCondition, rentals);
    }


}


