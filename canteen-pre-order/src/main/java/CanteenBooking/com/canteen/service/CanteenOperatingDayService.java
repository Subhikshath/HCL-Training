package CanteenBooking.com.canteen.service;

import CanteenBooking.com.canteen.dto.OperatingDayRequest;
import CanteenBooking.com.canteen.dto.OperatingDayResponse;
import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.CanteenOperatingDay;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenOperatingDayRepository;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.common.exception.ResourceNotFoundException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CanteenOperatingDayService {

    private final CanteenRepository canteenRepository;
    private final CanteenOperatingDayRepository operatingDayRepository;

    public CanteenOperatingDayService(
            CanteenRepository canteenRepository,
            CanteenOperatingDayRepository operatingDayRepository
    ) {
        this.canteenRepository = canteenRepository;
        this.operatingDayRepository = operatingDayRepository;
    }

    /*
     * Staff updates today's canteen status.
     *
     * Staff does NOT send the date.
     * The backend automatically uses today's date.
     */
    @Transactional
    public OperatingDayResponse updateTodayStatus(
            Long canteenId,
            OperatingDayRequest request
    ) {

        // 1. Find canteen
        Canteen canteen = canteenRepository.findById(canteenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + canteenId
                        )
                );

        // 2. Withdrawn canteen cannot be updated
        if (canteen.getContractStatus() == ContractStatus.WITHDRAWN) {
            throw new IllegalStateException(
                    "Cannot update status of a withdrawn canteen"
            );
        }

        // 3. Get today's date
        LocalDate today = LocalDate.now();

        // 4. Find today's operating record
        //    If it does not exist, create a new one
        CanteenOperatingDay operatingDay =
                operatingDayRepository
                        .findByCanteenIdAndOperatingDate(
                                canteenId,
                                today
                        )
                        .orElseGet(() -> {

                            CanteenOperatingDay newOperatingDay =
                                    new CanteenOperatingDay();

                            newOperatingDay.setCanteen(canteen);
                            newOperatingDay.setOperatingDate(today);

                            return newOperatingDay;
                        });

        // 5. Update today's status
        operatingDay.setOpen(request.getOpen());


        if (request.getOpen()) {

            if (request.getOperatingMode() == null) {
                throw new IllegalArgumentException(
                        "Operating mode is required when the canteen is open"
                );
            }

            operatingDay.setOperatingMode(request.getOperatingMode());

        } else {

            operatingDay.setOperatingMode(null);
        }

        operatingDay.setNote(request.getNote());

        // 6. Save
        CanteenOperatingDay savedOperatingDay =
                operatingDayRepository.save(operatingDay);

        // 7. Return response
        return convertToResponse(savedOperatingDay);
    }


    /*
     * Student sees today's canteen status.
     *
     * Student cannot request a specific date.
     * Only today's status is returned.
     */
    @Transactional(readOnly = true)
    public OperatingDayResponse getTodayStatus(
            Long canteenId
    ) {

        // 1. Check whether canteen exists
        canteenRepository.findById(canteenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + canteenId
                        )
                );

        // 2. Get today's date
        LocalDate today = LocalDate.now();

        // 3. Find today's status
        CanteenOperatingDay operatingDay =
                operatingDayRepository
                        .findByCanteenIdAndOperatingDate(
                                canteenId,
                                today
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Today's operating status has not been updated yet"
                                )
                        );

        // 4. Return today's status
        return convertToResponse(operatingDay);
    }


    /*
     * Automatically closes all canteens at 10:00 PM.
     *
     * Spring Scheduler runs this method every day at 10 PM.
     */
    @Transactional
    @Scheduled(
            cron = "0 0 22 * * *",
            zone = "Asia/Kolkata"
    )
    public void automaticallyCloseToday() {

        // 1. Get today's date
        LocalDate today = LocalDate.now();

        // 2. Get all canteen operating records for today
        List<CanteenOperatingDay> operatingDays =
                operatingDayRepository.findByOperatingDate(today);

        // 3. Close every canteen that is currently open
        for (CanteenOperatingDay operatingDay : operatingDays) {

            if (operatingDay.isOpen()) {

                operatingDay.setOpen(false);

                operatingDay.setNote(
                        "Automatically closed at 10:00 PM"
                );
            }
        }

        // 4. Save updated records
        operatingDayRepository.saveAll(operatingDays);
    }


    /*
     * Converts Entity -> Response DTO
     */
    private OperatingDayResponse convertToResponse(
            CanteenOperatingDay operatingDay
    ) {

        return new OperatingDayResponse(
                operatingDay.getId(),
                operatingDay.getCanteen().getId(),
                operatingDay.getOperatingDate(),
                operatingDay.isOpen(),
                operatingDay.getOperatingMode(),
                operatingDay.getNote()
        );
    }
}