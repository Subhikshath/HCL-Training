package CanteenBooking.com.admin.service;

import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.admin.dto.CreateStaffRequest;
import CanteenBooking.com.user.entity.Role;
import CanteenBooking.com.user.entity.User;
import CanteenBooking.com.user.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminStaffService {

    private final UserRepository userRepository;
    private final CanteenRepository canteenRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminStaffService(
            UserRepository userRepository,
            CanteenRepository canteenRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.canteenRepository = canteenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createStaff(CreateStaffRequest request) {

        // 1. Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException(
                    "User already exists with email: " + request.getEmail()
            );
        }

        // 2. Check phone
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new IllegalStateException(
                    "User already exists with phone: " + request.getPhone()
            );
        }

        // 3. Find canteen
        Canteen canteen = canteenRepository.findById(request.getCanteenId())
                .orElseThrow(() -> new IllegalStateException(
                        "Canteen not found with id: " + request.getCanteenId()
                ));

        // 4. Don't assign staff to withdrawn canteen
        if (canteen.getContractStatus() == ContractStatus.WITHDRAWN) {
            throw new IllegalStateException(
                    "Cannot assign staff to a withdrawn canteen"
            );
        }

        // 5. Create staff
        User staff = new User();

        staff.setName(request.getName());
        staff.setEmail(request.getEmail());
        staff.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        staff.setPhone(request.getPhone());
        staff.setRole(Role.STAFF);
        staff.setActive(true);

        // 6. Assign canteen
        staff.setAssignedCanteen(canteen);

        // 7. Save
        return userRepository.save(staff);
    }
}