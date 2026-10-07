package CanteenBooking.com.canteen.service;

import CanteenBooking.com.canteen.dto.CanteenRequest;
import CanteenBooking.com.canteen.dto.CanteenResponse;
import CanteenBooking.com.canteen.entity.Canteen;
import CanteenBooking.com.canteen.entity.ContractStatus;
import CanteenBooking.com.canteen.repository.CanteenRepository;
import CanteenBooking.com.common.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CanteenService {

    private final CanteenRepository canteenRepository;

    public CanteenService(CanteenRepository canteenRepository) {
        this.canteenRepository = canteenRepository;
    }

    @Transactional
    public CanteenResponse createCanteen(CanteenRequest request) {

        if (canteenRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(
                    "Canteen with this name already exists"
            );
        }

        Canteen canteen = new Canteen();

        canteen.setName(request.getName());
        canteen.setDescription(request.getDescription());
        canteen.setLocation(request.getLocation());

        canteen.setStaffName(request.getStaffName());
        canteen.setStaffPhone(request.getStaffPhone());
        canteen.setStaffEmail(request.getStaffEmail());

        canteen.setContractStartDate(request.getContractStartDate());

        if (request.getContractStatus() != null) {
            canteen.setContractStatus(request.getContractStatus());
        } else {
            canteen.setContractStatus(ContractStatus.ACTIVE);
        }

        canteen.setContractEndDate(request.getContractEndDate());
        canteen.setContractWithdrawalReason(
                request.getContractWithdrawalReason()
        );

        Canteen savedCanteen = canteenRepository.save(canteen);

        return convertToResponse(savedCanteen);
    }

    @Transactional(readOnly = true)
    public List<CanteenResponse> getAllCanteens() {

        return canteenRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CanteenResponse getCanteenById(Long id) {

        Canteen canteen = canteenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + id
                        )
                );

        return convertToResponse(canteen);
    }

    @Transactional
    public CanteenResponse updateCanteen(
            Long id,
            CanteenRequest request
    ) {

        Canteen canteen = canteenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + id
                        )
                );

        if (!canteen.getName().equals(request.getName())
                && canteenRepository.existsByName(request.getName())) {

            throw new IllegalArgumentException(
                    "Canteen with this name already exists"
            );
        }

        canteen.setName(request.getName());
        canteen.setDescription(request.getDescription());
        canteen.setLocation(request.getLocation());

        canteen.setStaffName(request.getStaffName());
        canteen.setStaffPhone(request.getStaffPhone());
        canteen.setStaffEmail(request.getStaffEmail());

        canteen.setContractStartDate(request.getContractStartDate());

        if (request.getContractStatus() != null) {
            canteen.setContractStatus(request.getContractStatus());
        }

        canteen.setContractEndDate(request.getContractEndDate());
        canteen.setContractWithdrawalReason(
                request.getContractWithdrawalReason()
        );

        Canteen updatedCanteen = canteenRepository.save(canteen);

        return convertToResponse(updatedCanteen);
    }

    @Transactional
    public void withdrawCanteen(
            Long id,
            String withdrawalReason
    ) {

        Canteen canteen = canteenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Canteen not found with id: " + id
                        )
                );

        canteen.setContractStatus(ContractStatus.WITHDRAWN);
        canteen.setContractEndDate(
                java.time.LocalDate.now()
        );
        canteen.setContractWithdrawalReason(
                withdrawalReason
        );

        canteenRepository.save(canteen);
    }

    private CanteenResponse convertToResponse(Canteen canteen) {

        return new CanteenResponse(
                canteen.getId(),
                canteen.getName(),
                canteen.getDescription(),
                canteen.getLocation(),
                canteen.getStaffName(),
                canteen.getStaffPhone(),
                canteen.getStaffEmail(),
                canteen.getContractStartDate(),
                canteen.getContractStatus(),
                canteen.getContractEndDate(),
                canteen.getContractWithdrawalReason()
        );
    }
}