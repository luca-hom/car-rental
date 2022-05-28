package ch.juventus.carrental.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CarFilterDto {
    @JsonProperty("startDate")
    private LocalDate startDate;
    @JsonProperty("endDate")
    private LocalDate endDate;
    @JsonProperty("searchQuery")
    private String searchQuery;
    @JsonProperty("type")
    private List<Type> type;
    @JsonProperty("gearShift")
    private GearShift gearShift;
    @JsonProperty("minPricePerDay")
    private Double minPricePerDay;
    @JsonProperty("maxPricePerDay")
    private Double maxPricePerDay;
    @JsonProperty("seats")
    private List<Integer> seats;
    @JsonProperty("airCondition")
    private Boolean airCondition;

    public CarFilterDto(LocalDate startDate, LocalDate endDate, String searchQuery, List<Type> type, GearShift gearShift, Double minPricePerDay, Double maxPricePerDay, List<Integer> seats, Boolean airCondition) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.searchQuery = searchQuery;
        this.type = type;
        this.gearShift = gearShift;
        this.minPricePerDay = minPricePerDay;
        this.maxPricePerDay = maxPricePerDay;
        this.seats = seats;
        this.airCondition = airCondition;
    }

    public CarFilterDto() {

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

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

    public GearShift getGearShift() {
        return gearShift;
    }

    public void setGearShift(GearShift gearShift) {
        this.gearShift = gearShift;
    }

    public Double getMinPricePerDay() {
        return minPricePerDay;
    }

    public void setMinPricePerDay(Double minPricePerDay) {
        this.minPricePerDay = minPricePerDay;
    }

    public Double getMaxPricePerDay() {
        return maxPricePerDay;
    }

    public void setMaxPricePerDay(Double maxPricePerDay) {
        this.maxPricePerDay = maxPricePerDay;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }

    public Boolean getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(Boolean airCondition) {
        this.airCondition = airCondition;
    }


    @Override
    public String toString() {
        return "CarFilterDto{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", searchQuery='" + searchQuery + '\'' +
                ", type=" + type +
                ", gearShift=" + gearShift +
                ", minPricePerDay=" + minPricePerDay +
                ", maxPricePerDay=" + maxPricePerDay +
                ", seats=" + seats +
                ", airCondition=" + airCondition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarFilterDto that = (CarFilterDto) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(searchQuery, that.searchQuery) && Objects.equals(type, that.type) && gearShift == that.gearShift && Objects.equals(minPricePerDay, that.minPricePerDay) && Objects.equals(maxPricePerDay, that.maxPricePerDay) && Objects.equals(seats, that.seats) && Objects.equals(airCondition, that.airCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, searchQuery, type, gearShift, minPricePerDay, maxPricePerDay, seats, airCondition);
    }
}
