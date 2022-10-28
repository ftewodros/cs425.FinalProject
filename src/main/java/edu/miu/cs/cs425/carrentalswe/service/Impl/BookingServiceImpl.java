package edu.miu.cs.cs425.carrentalswe.service.Impl;


import edu.miu.cs.cs425.carrentalswe.model.Booking;
import edu.miu.cs.cs425.carrentalswe.model.ExternalUser;
import edu.miu.cs.cs425.carrentalswe.repository.BookRepository;
import edu.miu.cs.cs425.carrentalswe.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private BookRepository bookRepository;

    public BookingServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookRepository.save(booking);
    }

    @Override
    public List<Booking> getBookings() {
        return bookRepository.findAll();
    }

    @Override
    public List<Booking> getUserBookings(ExternalUser user) {
        return bookRepository.findAllByUser(user);
    }

    @Override
    public Booking getBooking(Long id) {
        return bookRepository.getById(id);
    }
}
