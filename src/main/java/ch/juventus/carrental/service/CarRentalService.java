package ch.juventus.carrental.service;

import java.io.IOException;

public interface CarRentalService {
    String getGreeting();
    String getCarList();
    String getCarById(Long id);
}
