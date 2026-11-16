package com.locks.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PessimisticSeatBookingTestService {

    private final SeatBookingService seatBookingService;

    public PessimisticSeatBookingTestService(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    public void testPessimisticLocking(Long seatId) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            try {
                log.info("{} attempt to book a seat", Thread.currentThread().getName());
               seatBookingService.bookSeatWithPessimistic(seatId);
                log.info("{} Successfully book a seat with Id {}", Thread.currentThread().getName(), seatId);
            } catch (Exception e) {
               log.info("{} failed to book a seat ..Reason : {}",Thread.currentThread().getName(),e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                log.info("{} attempt to book a seat", Thread.currentThread().getName());
                seatBookingService.bookSeatWithPessimistic(seatId);
                log.info("{} Successfully book a seat with Id {}", Thread.currentThread().getName(), seatId);
            } catch (Exception e) {
                log.info("{} failed to book a seat ..Reason : {}",Thread.currentThread().getName(),e.getMessage());
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

    }
}
