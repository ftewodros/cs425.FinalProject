package edu.miu.cs.cs425.carrentalswe.controller;

import edu.miu.cs.cs425.carrentalswe.model.Booking;
import edu.miu.cs.cs425.carrentalswe.model.Payment;
import edu.miu.cs.cs425.carrentalswe.model.Vehicle;
import edu.miu.cs.cs425.carrentalswe.service.BookingService;
import edu.miu.cs.cs425.carrentalswe.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = {"/eRental"})
public class PaymentController {

    private BookingService bookingService;
    private PaymentService paymentService;

    public PaymentController(BookingService bookingService, PaymentService paymentService) {
        this.bookingService = bookingService;
        this.paymentService = paymentService;
    }

    @GetMapping(value={"/secured/payment/{bookingId}"})
    public String getPaymentpage(Model model, @PathVariable("bookingId") Long bookingId){
        Booking booking = bookingService.getBooking(bookingId);
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(booking.getBookingFee());
        model.addAttribute("payment", payment);
        return "/secured/payment";
    }


    @PostMapping(value={"/secured/payment"})
    public String registerUser(@Valid
                               @ModelAttribute("payment") Payment payment,
                               BindingResult bindingResult,
                               @RequestParam("bookId") Long bookId,
                               Model model)throws IOException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            model.addAttribute("payment", payment);
            return "/secured/payment";
        }
        payment.setPaymentDate(LocalDate.now());
        Booking booking = bookingService.getBooking(bookId);
        payment.setBooking(booking);


        Payment payment1 = paymentService.addPayment(payment);

        return "redirect:/eRental/secured/bookingList/client";
    }

    @GetMapping(value={"/secured/paymentList"})
    public String getVehiclespage(Model model){
        List<Payment> paymentList = paymentService.getAllPayments();

        model.addAttribute("payments", paymentList);
        return "secured/paymentList";
    }

}
