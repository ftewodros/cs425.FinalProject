package edu.miu.cs.cs425.carrentalswe.controller;

import edu.miu.cs.cs425.carrentalswe.model.*;
import edu.miu.cs.cs425.carrentalswe.repository.UserRepository;
import edu.miu.cs.cs425.carrentalswe.service.BookingService;
import edu.miu.cs.cs425.carrentalswe.service.ExternalUserService;
import edu.miu.cs.cs425.carrentalswe.service.VehicleService;
import edu.miu.cs.cs425.carrentalswe.utils.FileUploadUtil;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping(value = {"/eRental"})
public class BookController {

    private BookingService bookingService;
    private VehicleService vehicleService;
    private UserRepository userRepository;
    private ExternalUserService externalUserService;

    public BookController(BookingService bookingService, VehicleService vehicleService, UserRepository userRepository, ExternalUserService externalUserService) {
        this.bookingService = bookingService;
        this.vehicleService = vehicleService;
        this.userRepository = userRepository;
        this.externalUserService = externalUserService;
    }

    @GetMapping(value={"/secured/bookVehicle/{vehicleId}"})
    public String getBookingpage(Model model, @PathVariable("vehicleId") Long vehicleId){
        Vehicle vehicle = vehicleService.getOneVehicle(vehicleId);
      Booking booking = new Booking();

      booking.setVehicle(vehicle);
      booking.setUser(getLoggedInUser());
        model.addAttribute("booking", booking);
        return "/secured/bookVehicle";
    }


    @PostMapping(value={"/secured/bookVehicle"})
    public String registerUser(@Valid
                               @ModelAttribute("booking") Booking booking,
                               BindingResult bindingResult,
                               Model model)throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            model.addAttribute("booking", booking);
            return "/secured/bookVehicle";
        }

        LocalDate datefrom = booking.getBookingDate();
        LocalDate dateTo = booking.getReturnDate();

        long diff = DAYS.between(datefrom, dateTo);

//        Duration duration = Duration.between(dateTo, datefrom);
//
//        long diff = Math.abs(duration.toDays());
        booking.setUser(getLoggedInUser());
        booking.setDuration(diff);
        booking.setBookingFee(10.00 * diff);

        Long vehicleId = booking.getVehicle().getVehicleId();

        Vehicle vehicle = vehicleService.getOneVehicle(vehicleId);

        vehicle.setStatus("Booked");

        vehicleService.saveVehicle(vehicle);

      Booking booking1 = bookingService.saveBooking(booking);

        return "redirect:/eRental/secured/payment/" + booking1.getBookingId();
    }

    @GetMapping(value={"/secured/bookingList"})
    public String getVehiclespage(Model model){
        List<Booking> bookingList = bookingService.getBookings();

        model.addAttribute("bookings", bookingList);
        return "secured/bookingList";
    }

    @GetMapping(value={"/secured/bookingList/client"})
    public String getVehiclesClientpage(Model model){
        List<Booking> bookingList = bookingService.getUserBookings(getLoggedInUser());
        model.addAttribute("bookings", bookingList);
        return "secured/bookingList";
    }


    private ExternalUser getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
            User user = userRepository.findByUsername(name).orElseThrow(()-> new UsernameNotFoundException(name));
            ExternalUser externalUser = externalUserService.findByUser(user);
            return  externalUser;


    }
}
