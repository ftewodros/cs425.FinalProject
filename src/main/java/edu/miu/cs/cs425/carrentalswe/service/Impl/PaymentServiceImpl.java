package edu.miu.cs.cs425.carrentalswe.service.Impl;

import edu.miu.cs.cs425.carrentalswe.model.Payment;
import edu.miu.cs.cs425.carrentalswe.repository.PaymentRepository;
import edu.miu.cs.cs425.carrentalswe.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
