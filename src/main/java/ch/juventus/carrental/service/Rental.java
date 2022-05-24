package ch.juventus.carrental.service;

import java.time.LocalDate;
import java.util.Objects;

public class Rental extends Car{
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalPrice;

    public Rental(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }



    public Rental() {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getTotalPrice() {
        Double totalPrice = super.getPricePerDay() * (this.startDate.compareTo(this.endDate));
        return totalPrice;
    }



    @Override
    public String toString() {
        return "Rental{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(startDate, rental.startDate) && Objects.equals(endDate, rental.endDate) && Objects.equals(totalPrice, rental.totalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, totalPrice);
    }
}
