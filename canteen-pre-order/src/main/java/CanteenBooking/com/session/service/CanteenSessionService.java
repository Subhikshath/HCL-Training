package CanteenBooking.com.session.service;

import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.session.dto.SessionRequest;
import CanteenBooking.com.session.dto.SessionResponse;
import CanteenBooking.com.session.entity.CanteenSession;
import CanteenBooking.com.session.repository.CanteenSessionRepository;
import CanteenBooking.com.common.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CanteenSessionService {

    private final CanteenRepository canteenRepository;
    private final CanteenSessionRepository sessionRepository;

    public CanteenSessionService(
            CanteenRepository canteenRepository,
            CanteenSessionRepository sessionRepository
    ) {
        this.canteenRepository = canteenRepository;
        this.sessionRepository = sessionRepository;
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

        LocalDate today = LocalDate.now();

        validateTime(request.getStartTime(), request.getEndTime());

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