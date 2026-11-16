package com.locks.service;

import com.locks.entity.Seat;
import com.locks.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SeatBookingService {

    private final SeatRepository seatRepository;

    public SeatBookingService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Seat bookSeatWithOptimistic(Long seatId) {
        //first fetch the seat details
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat Not Found With Id " + seatId));

        log.info("{} fetch seat with version {}", Thread.currentThread().getName(), seat.getVersion());
        if (seat.isBooked()) {
            throw new RuntimeException(seatId + " is Already Booked !");
        }
        seat.setBooked(true);
        seat.setStatus("BOOKED");

        // when try to save in database it will check version value. version check will appear here...
        return seatRepository.save(seat);
    }

    @Transactional
    public void bookSeatWithPessimistic(Long seatId) {

        log.info("{} attempt to fetch seat and do booking...", Thread.currentThread().getName());

        // this is pessimistic lock, first try to read and then acquired lock
        Seat seat = seatRepository.findByIdAndLock(seatId);

        log.info("{} acquired lock on seat id {} ", Thread.currentThread().getName(),seat.getId());
        if (seat.isBooked()) {
            log.info("{} failed to book seat with id {} ", Thread.currentThread().getName(),seatId);
            throw new RuntimeException(seatId + " is Already Booked !");
        }
        seat.setBooked(true);
        seat.setStatus("BOOKED");
        seatRepository.save(seat);
        log.info("{} booked seat with Id {} ", Thread.currentThread().getName(),seatId);

    }


}
