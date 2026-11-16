package com.locks.service;

import com.locks.entity.Seat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OptimisticSeatBookingTestService {

    private final SeatBookingService seatBookingService;

    public OptimisticSeatBookingTestService(SeatBookingService seatBookingService) {
        this.seatBookingService = seatBookingService;
    }

    public void testOptimisticLocking(Long seatId) throws InterruptedException {
        Thread t1 = new Thread(() -> {

            try {
                log.info("{} attempt to book a seat", Thread.currentThread().getName());
                Seat seat = seatBookingService.bookSeatWithOptimistic(seatId);
                log.info("{} Successfully book a seat with Version {}", Thread.currentThread().getName(), seat.getVersion());
            } catch (Exception e) {
               log.info("{} failed to book a seat ..Reason : {}",Thread.currentThread().getName(),e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                log.info("{} attempt to book a seat", Thread.currentThread().getName());
                Seat seat = seatBookingService.bookSeatWithOptimistic(seatId);
                log.info("{} Successfully book a seat with Version {}", Thread.currentThread().getName(), seat.getVersion());
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
