package com.locks.controller;


import com.locks.service.OptimisticSeatBookingTestService;
import com.locks.service.PessimisticSeatBookingTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookSeat/")
public class SeatController {

    private final OptimisticSeatBookingTestService  optimisticSeatBookingTestService;
    private final PessimisticSeatBookingTestService pessimisticSeatBookingTestService;

    public SeatController(OptimisticSeatBookingTestService optimisticSeatBookingTestService, PessimisticSeatBookingTestService pessimisticSeatBookingTestService) {
        this.optimisticSeatBookingTestService = optimisticSeatBookingTestService;
        this.pessimisticSeatBookingTestService = pessimisticSeatBookingTestService;
    }


    @GetMapping("optimistic/{seatId}")
    public String testOptimisticLocking(@PathVariable Long seatId) throws InterruptedException {
        optimisticSeatBookingTestService.testOptimisticLocking(seatId);
        return "Optimistic locking Test Started....Check logs for result";
    }

    @GetMapping("pessimistic/{seatId}")
    public String testPessimisticLocking(@PathVariable Long seatId) throws InterruptedException {
        pessimisticSeatBookingTestService.testPessimisticLocking(seatId);
        return "Pessimistic locking Test Started....Check logs for result";
    }
}
