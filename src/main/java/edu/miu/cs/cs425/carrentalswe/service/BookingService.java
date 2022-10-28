package edu.miu.cs.cs425.carrentalswe.service;

import edu.miu.cs.cs425.carrentalswe.model.Booking;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    Booking saveBooking(Booking booking);

    Booking getBooking(Long id);

    List<Booking> getBookings();

    List<Booking> getUserBookings(ExternalUser user);
}
