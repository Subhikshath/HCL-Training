
package CanteenBooking.com.security;

import CanteenBooking.com.user.entity.User;
import CanteenBooking.com.user.entity.Role;
import CanteenBooking.com.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class StaffAuthorizationService {

    private final UserRepository userRepository;

    public StaffAuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean canManageCanteen(
            Authentication authentication,
            Long canteenId) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getName() == null) {
            return false;
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Logged-in user not found"
                        )
                );

        // ADMIN can manage any canteen.
        if (user.getRole() == Role.ADMIN) {
            return true;
        }

        // Only STAFF can continue.
        if (user.getRole() != Role.STAFF) {
            return false;
        }

        // STAFF must have an assigned canteen.
        if (user.getAssignedCanteen() == null) {
            return false;
        }

        // STAFF can manage only their assigned canteen.
        return user.getAssignedCanteen()
                .getId()
                .equals(canteenId);
    }
}
