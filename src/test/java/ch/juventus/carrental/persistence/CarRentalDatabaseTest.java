package ch.juventus.carrental.persistence;

import ch.juventus.carrental.service.Car;
import ch.juventus.carrental.service.GearShift;
import ch.juventus.carrental.service.Rental;
import ch.juventus.carrental.service.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@JsonInclude(JsonInclude.Include.NON_NULL)
class CarRentalDatabaseTest {

    private CarRentalDatabase carRentalDatabase;
    private String testPath = "src/main/resources/test.json";


    @BeforeEach
    void init() { this.carRentalDatabase = new CarRentalDatabase(); }

    @Test
    void testIfCarObjectIsWrittenToJsonFile() throws IOException {




        Car testCar = new Car("TestCar", Type.CABRIO, GearShift.AUTOMATIC, 2, (double) 100, true);

        //testCar is created with test-rental-array -> function call
        carRentalDatabase.writeCarToJsonFile(testCar, testPath);



        Path tP = Paths.get(testPath);
        System.out.println("TestJson: "+Files.readString(tP));

        //read out File and compare to correct String
        assertEquals(Files.readString(tP),"[{\"id\":0,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":null}]" );

        //clean up and delete testFile
        Files.deleteIfExists(tP);

    }

    @Test
    void testGetCarListFromJsonFile() throws IOException {

        Path tP = Paths.get(testPath);
        Files.createFile(tP);
        Files.writeString(tP, "[{\"id\":0,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":null}]");


        System.out.println(carRentalDatabase.getCarListFromJsonFile(testPath));

        Rental rental = new Rental(LocalDate.of(2022,01,01),LocalDate.of(2022, 01, 02));
        ArrayList<Rental> rentalList = new ArrayList<Rental>();
        rentalList.add(rental);


        ArrayList<Car> testCarList = new ArrayList<Car>();
        testCarList.add(new Car(0L,"TestCar", Type.CABRIO, GearShift.AUTOMATIC, 2, (double) 100, true));

        assertEquals(testCarList,carRentalDatabase.getCarListFromJsonFile(testPath));
        //clean up and delete testFile
        Files.deleteIfExists(tP);


    }

    @Test
    void testIfRentalIsWrittenToJsonFile() throws IOException {

        Path p = Paths.get(testPath);
        Files.createFile(p);
        Files.writeString(p, "[{\"id\":2147483649,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":null}]");



        Rental nextRental = new Rental(LocalDate.of(2022,01,01),LocalDate.of(2022, 01, 02));
        Rental nextRental2 = new Rental(LocalDate.of(2023,01,01),LocalDate.of(2022, 01, 02));


        carRentalDatabase.writeRentalToCar(testPath, 2147483649L,nextRental);
        carRentalDatabase.writeRentalToCar(testPath, 2147483649L,nextRental2);

        assertEquals("[{\"id\":2147483649,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":[{\"startDate\":[2022,1,1],\"endDate\":[2022,1,2],\"totalPrice\":100.0},{\"startDate\":[2023,1,1],\"endDate\":[2022,1,2],\"totalPrice\":-100.0}]}]",Files.readString(p) );
        System.out.println(Files.readString(p));

        Files.deleteIfExists(p);

    }

    @Test
    void testIfRentalIsReadOfJsonFile() throws IOException {

        Path p = Paths.get(testPath);
        Files.createFile(p);
        Files.writeString(p, "[{\"id\":1,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":[{\"startDate\":[2022,1,1],\"endDate\":[2022,1,2],\"totalPrice\":100.0},{\"startDate\":[2023,1,1],\"endDate\":[2022,1,2],\"totalPrice\":-100.0}]}]");


        System.out.println(carRentalDatabase.readRentalsOfCar(testPath, 1L));

        Files.deleteIfExists(p);

    }



    @Test
    void testIfCarIsReplaceable() throws IOException {
        Path p = Paths.get(testPath);
        Files.createFile(p);
        Files.writeString(p, "[{\"id\":1,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":null}]");

        Car replaceCar = new Car("TestCar", Type.CABRIO, GearShift.AUTOMATIC, 4, 200D, true);

        carRentalDatabase.replaceCarToJsonFile(testPath,1L, replaceCar);

        assertEquals( "[{\"id\":null,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":4,\"pricePerDay\":200.0,\"airCondition\":true,\"rentals\":null}]", Files.readString(p));

        System.out.println(Files.readString(p));

        Files.deleteIfExists(p);


    }


    @Test
    void testIfIdCheckerIsWorking() throws IOException {

        Path p = Paths.get(testPath);
        Files.createFile(p);
        Files.writeString(p, "[{\"id\":1,\"name\":\"TestCar\",\"type\":\"CABRIO\",\"gearShift\":\"AUTOMATIC\",\"seats\":2,\"pricePerDay\":100.0,\"airCondition\":true,\"rentals\":null}]");

        try {
            carRentalDatabase.checkIfCarIdIsValid(2L, testPath);
            System.out.println("valid");
        }
        catch (IllegalArgumentException e) {
            System.out.println("not valid");
        }

        finally {
            Files.deleteIfExists(p);
        }


    }



}
