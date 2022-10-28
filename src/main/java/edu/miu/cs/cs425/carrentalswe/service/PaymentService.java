package edu.miu.cs.cs425.carrentalswe.service;

import edu.miu.cs.cs425.carrentalswe.model.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    Payment addPayment(Payment payment);
    List<Payment> getAllPayments();

}
