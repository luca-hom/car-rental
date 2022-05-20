package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;
import ch.juventus.carrental.service.GearShift;
import ch.juventus.carrental.service.Rental;
import ch.juventus.carrental.service.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@JsonInclude(JsonInclude.Include.NON_NULL)
class CarRentalDatabaseTest {

    private CarRentalDatabase carRentalDatabase;
    private String testPath = "src/main/resources/test.json";


    @BeforeEach
    void init() { this.carRentalDatabase = new CarRentalDatabase(); }

    @Test
    void testIfCarObjectIsWrittenToJsonFile() throws IOException {

        Rental rental = new Rental(LocalDate.of(2022,01,01),LocalDate.of(2022, 01, 02), 100D);
        ArrayList<Rental> rentalList = new ArrayList<Rental>();
        rentalList.add(rental);


        Car testCar = new Car("TestCar", Type.CABRIO, GearShift.AUTOMATIC, 2, (double) 100, true, rentalList);

        //testCar is created with test-rental-array -> function call
        carRentalDatabase.writeCarToJsonFile(testCar, testPath);


        Path tP = Paths.get(testPath);
        System.out.println("TestJson: "+Files.readString(tP));

        //read out File and compare to correct String
        assertEquals(Files.readString(tP),"[{\"id\":0,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":[{\"startDate\":[2022,1,1],\"endDate\":[2022,1,2],\"totalPrice\":100.0}]}]" );

        //clean up and delete testFile
        Files.deleteIfExists(tP);


    }
}