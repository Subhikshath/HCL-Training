
package CanteenBooking.com.menu.service;

import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.CanteenOperatingDay;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenOperatingDayRepository;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.menu.dto.DailyMenuRequest;
import CanteenBooking.com.menu.dto.DailyMenuResponse;
import CanteenBooking.com.menu.dto.MenuItemRequest;
import CanteenBooking.com.menu.dto.MenuItemResponse;
import CanteenBooking.com.menu.entity.DailyMenu;
import CanteenBooking.com.menu.entity.MenuItem;
import CanteenBooking.com.menu.repository.DailyMenuRepository;
import CanteenBooking.com.session.entity.CanteenSession;
import CanteenBooking.com.session.entity.OperatingMode;
import CanteenBooking.com.session.entity.SessionType;
import CanteenBooking.com.session.repository.CanteenSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final CanteenRepository canteenRepository;
    private final CanteenOperatingDayRepository operatingDayRepository;
    private final CanteenSessionRepository sessionRepository;
    private final DailyMenuRepository dailyMenuRepository;

    public MenuService(
            CanteenRepository canteenRepository,
            CanteenOperatingDayRepository operatingDayRepository,
            CanteenSessionRepository sessionRepository,
            DailyMenuRepository dailyMenuRepository) {
        this.canteenRepository = canteenRepository;
        this.operatingDayRepository = operatingDayRepository;
        this.sessionRepository = sessionRepository;
        this.dailyMenuRepository = dailyMenuRepository;
    }

    @Transactional
    public DailyMenuResponse createTodayMenu(
            Long canteenId, DailyMenuRequest request) {

        LocalDate today = LocalDate.now();

        Canteen canteen = canteenRepository.findById(canteenId)
                .orElseThrow(() ->
                        new RuntimeException("Canteen not found"));

        if (canteen.getContractStatus() != ContractStatus.ACTIVE) {
            throw new RuntimeException(
                    "Withdrawn canteens cannot create menus");
        }

        CanteenOperatingDay operatingDay =
                operatingDayRepository
                        .findByCanteenIdAndOperatingDate(canteenId, today)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Today's operating status is not configured"));

        if (!operatingDay.isOpen()) {
            throw new RuntimeException(
                    "Cannot create a menu because the canteen is closed");
        }

        OperatingMode mode = operatingDay.getOperatingMode();

        if (mode == null) {
            throw new RuntimeException(
                    "Operating mode is not configured for today");
        }

        CanteenSession session = null;

        if (mode == OperatingMode.FULL_DAY) {

            if (request.getSessionId() != null) {
                throw new RuntimeException(
                        "FULL_DAY menus must not have a session ID");
            }

            if (dailyMenuRepository
                    .existsByCanteenIdAndMenuDateAndSessionIsNull(
                            canteenId, today)) {
                throw new RuntimeException(
                        "Today's full-day menu already exists");
            }

        } else if (mode == OperatingMode.SESSION_BASED) {

            if (request.getSessionId() == null) {
                throw new RuntimeException(
                        "Session ID is required for a session-based menu");
            }

            session = sessionRepository.findById(request.getSessionId())
                    .orElseThrow(() ->
                            new RuntimeException("Session not found"));

            if (!session.getCanteen().getId().equals(canteenId)) {
                throw new RuntimeException(
                        "Session does not belong to this canteen");
            }

            if (!session.getSessionDate().equals(today)) {
                throw new RuntimeException(
                        "Only today's sessions can have menus created");
            }

            if (!session.isActive()) {
                throw new RuntimeException(
                        "Cannot create a menu for an inactive session");
            }

            if (session.getSessionType() == SessionType.FULL_DAY) {
                throw new RuntimeException(
                        "FULL_DAY sessions cannot be used in SESSION_BASED mode");
            }

            if (dailyMenuRepository
                    .existsByCanteenIdAndMenuDateAndSessionId(
                            canteenId, today, session.getId())) {
                throw new RuntimeException(
                        "A menu already exists for this session");
            }

        } else {
            throw new RuntimeException("Unsupported operating mode");
        }

        DailyMenu menu = new DailyMenu();
        menu.setCanteen(canteen);
        menu.setMenuDate(today);
        menu.setSession(session);
        menu.setActive(true);

        for (MenuItemRequest itemRequest : request.getItems()) {

            MenuItem item = new MenuItem();
            item.setName(itemRequest.getName().trim());
            item.setDescription(itemRequest.getDescription());
            item.setPrice(itemRequest.getPrice());
            item.setMenuType(itemRequest.getMenuType());
            item.setAvailable(true);
            item.setSideDishes(itemRequest.getSideDishes());

            menu.addItem(item);
        }

        DailyMenu savedMenu = dailyMenuRepository.save(menu);

        return toResponse(savedMenu);
    }

    @Transactional(readOnly = true)
    public List<DailyMenuResponse> getTodayMenus(Long canteenId) {

        LocalDate today = LocalDate.now();

        if (!canteenRepository.existsById(canteenId)) {
            throw new RuntimeException("Canteen not found");
        }

        return dailyMenuRepository
                .findByCanteenIdAndMenuDateAndActiveTrue(canteenId, today)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public List<DailyMenuResponse> getTodayMenusBySession(
            Long canteenId,
            SessionType sessionType) {

        LocalDate today = LocalDate.now();

        if (!canteenRepository.existsById(canteenId)) {
            throw new RuntimeException("Canteen not found");
        }

        CanteenOperatingDay operatingDay =
                operatingDayRepository
                        .findByCanteenIdAndOperatingDate(canteenId, today)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Today's operating status is not configured"));

        if (!operatingDay.isOpen()) {
            throw new RuntimeException("Canteen is closed today");
        }

        if (operatingDay.getOperatingMode() == OperatingMode.FULL_DAY) {
            throw new RuntimeException(
                    "This canteen uses FULL_DAY mode. No session selection is required.");
        }

        if (sessionType == SessionType.FULL_DAY) {
            throw new RuntimeException(
                    "Choose BREAKFAST, LUNCH, EVENING, or DINNER.");
        }

        return dailyMenuRepository
                .findByCanteenIdAndMenuDateAndSessionSessionTypeAndActiveTrue(
                        canteenId, today, sessionType)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    private DailyMenuResponse toResponse(DailyMenu menu) {

        List<MenuItemResponse> items = menu.getItems()
                .stream()
                .map(item -> new MenuItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getMenuType(),
                        item.isAvailable(),
                        item.getSideDishes()
                ))
                .toList();

        Long sessionId = menu.getSession() == null
                ? null
                : menu.getSession().getId();

        return new DailyMenuResponse(
                menu.getId(),
                menu.getCanteen().getId(),
                menu.getMenuDate(),
                sessionId,
                menu.isActive(),
                items
        );
    }
}
