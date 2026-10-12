package CanteenBooking.com.session.service;

import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.CanteenOperatingDay;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenOperatingDayRepository;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.common.exception.ResourceNotFoundException;
import CanteenBooking.com.session.dto.SessionRequest;
import CanteenBooking.com.session.dto.SessionResponse;
import CanteenBooking.com.session.entity.CanteenSession;
import CanteenBooking.com.session.entity.OperatingMode;
import CanteenBooking.com.session.entity.SessionType;
import CanteenBooking.com.session.repository.CanteenSessionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CanteenSessionService {

    private final CanteenRepository canteenRepository;
    private final CanteenSessionRepository sessionRepository;
    private final CanteenOperatingDayRepository operatingDayRepository;

    public CanteenSessionService(
            CanteenRepository canteenRepository,
            CanteenSessionRepository sessionRepository,
            CanteenOperatingDayRepository operatingDayRepository
    ) {
        this.canteenRepository = canteenRepository;
        this.sessionRepository = sessionRepository;
        this.operatingDayRepository = operatingDayRepository;
    }

    @Transactional
    public SessionResponse createTodaySession(
            Long canteenId,
            SessionRequest request
    ) {

        Canteen canteen = canteenRepository.findById(canteenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + canteenId
                        )
                );

        if (canteen.getContractStatus() == ContractStatus.WITHDRAWN) {
            throw new IllegalStateException(
                    "Cannot create session for a withdrawn canteen"
            );
        }

        // Get today's operating-day configuration
        CanteenOperatingDay operatingDay =
                getTodayOperatingDay(canteenId);

        // Check whether requested session matches operating mode
        validateSessionType(
                operatingDay.getOperatingMode(),
                request.getSessionType()
        );

        LocalDate today = LocalDate.now();

        validateTime(
                request.getStartTime(),
                request.getEndTime()
        );

        if (sessionRepository
                .existsByCanteenIdAndSessionDateAndSessionType(
                        canteenId,
                        today,
                        request.getSessionType()
                )) {

            throw new IllegalStateException(
                    "Session already exists for today: "
                            + request.getSessionType()
            );
        }

        CanteenSession session = new CanteenSession();

        session.setCanteen(canteen);
        session.setSessionDate(today);
        session.setSessionType(request.getSessionType());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setActive(true);

        CanteenSession savedSession =
                sessionRepository.save(session);

        return convertToResponse(savedSession);
    }

    @Transactional(readOnly = true)
    public List<SessionResponse> getTodaySessions(
            Long canteenId
    ) {

        canteenRepository.findById(canteenId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + canteenId
                        )
                );

        LocalDate today = LocalDate.now();

        return sessionRepository
                .findByCanteenIdAndSessionDateAndActiveTrue(
                        canteenId,
                        today
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public SessionResponse updateTodaySession(
            Long canteenId,
            Long sessionId,
            SessionRequest request
    ) {

        CanteenSession session =
                sessionRepository.findById(sessionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Session not found with id: "
                                                + sessionId
                                )
                        );

        if (!session.getCanteen().getId().equals(canteenId)) {
            throw new IllegalStateException(
                    "Session does not belong to this canteen"
            );
        }

        if (!session.getSessionDate()
                .equals(LocalDate.now())) {

            throw new IllegalStateException(
                    "Only today's session can be updated"
            );
        }

        // Get today's operating-day configuration
        CanteenOperatingDay operatingDay =
                getTodayOperatingDay(canteenId);

        // Check whether the new session type matches today's mode
        validateSessionType(
                operatingDay.getOperatingMode(),
                request.getSessionType()
        );

        validateTime(
                request.getStartTime(),
                request.getEndTime()
        );

        session.setSessionType(request.getSessionType());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());

        CanteenSession updatedSession =
                sessionRepository.save(session);

        return convertToResponse(updatedSession);
    }

    @Transactional
    public void deactivateTodaySession(
            Long canteenId,
            Long sessionId
    ) {

        CanteenSession session =
                sessionRepository.findById(sessionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Session not found with id: "
                                                + sessionId
                                )
                        );

        if (!session.getCanteen().getId().equals(canteenId)) {
            throw new IllegalStateException(
                    "Session does not belong to this canteen"
            );
        }

        if (!session.getSessionDate()
                .equals(LocalDate.now())) {

            throw new IllegalStateException(
                    "Only today's session can be deactivated"
            );
        }

        session.setActive(false);

        sessionRepository.save(session);
    }

    /**
     * Gets today's operating-day configuration.
     */
    private CanteenOperatingDay getTodayOperatingDay(
            Long canteenId
    ) {

        LocalDate today = LocalDate.now();

        CanteenOperatingDay operatingDay =
                operatingDayRepository
                        .findByCanteenIdAndOperatingDate(
                                canteenId,
                                today
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Today's operating status has not been configured"
                                )
                        );

        if (!operatingDay.isOpen()) {
            throw new IllegalStateException(
                    "Canteen is closed today"
            );
        }

        if (operatingDay.getOperatingMode() == null) {
            throw new IllegalStateException(
                    "Operating mode has not been configured for today"
            );
        }

        return operatingDay;
    }

    /**
     * Makes sure the session type matches the
     * operating mode of the canteen for today.
     */
    private void validateSessionType(
            OperatingMode operatingMode,
            SessionType sessionType
    ) {

        if (operatingMode == OperatingMode.FULL_DAY) {

            if (sessionType != SessionType.FULL_DAY) {

                throw new IllegalStateException(
                        "FULL_DAY operating mode allows only FULL_DAY session"
                );
            }

        } else if (operatingMode == OperatingMode.SESSION_BASED) {

            if (sessionType == SessionType.FULL_DAY) {

                throw new IllegalStateException(
                        "SESSION_BASED operating mode does not allow FULL_DAY session"
                );
            }
        }
    }

    private void validateTime(
            LocalTime startTime,
            LocalTime endTime
    ) {

        if (!startTime.isBefore(endTime)) {

            throw new IllegalStateException(
                    "Session start time must be before end time"
            );
        }
    }

    private SessionResponse convertToResponse(
            CanteenSession session
    ) {

        return new SessionResponse(
                session.getId(),
                session.getCanteen().getId(),
                session.getSessionDate(),
                session.getSessionType(),
                session.getStartTime(),
                session.getEndTime(),
                session.isActive()
        );
    }
}