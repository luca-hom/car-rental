package ch.juventus.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This SpringBoot-Application is a REST-API for a car-rental service. It has multiple endpoints for various tasks.
 * The cars and rentals are saved in one local json-file. The whole communication to this endpoint is via json.
 *
 *
 * @author Luca Homberger, Tobias Tschudi
 * @version 1.0
 * @since 2022-05-28
 */


@SpringBootApplication
public class CarRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}

}
