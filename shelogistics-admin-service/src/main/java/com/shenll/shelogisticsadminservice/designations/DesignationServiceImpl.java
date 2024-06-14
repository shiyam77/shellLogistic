package com.shenll.shelogisticsadminservice.designations;

import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    DesignationRepository designationRepository;

    @Override
    public CommonResponse createDesignations(DesignationDTO designationDTO) {

        if (designationDTO.getId() == null) {
            Designation designation = new Designation().builder()
                    .status(designationDTO.getStatus())
                    .designation(designationDTO.getDesignation())
                    .build();
            designationRepository.save(designation);
            return CommonResponse.builder().code(200).data(designation.getDesignationId()).message("successfully saved ").build();
        } else {
            Designation designation = designationRepository.findById(designationDTO.getId()).orElseThrow(() -> new AppException("Invalid Id", HttpStatus.BAD_REQUEST));
            designation.setDesignation(designationDTO.getDesignation() != null ? designationDTO.getDesignation() : designation.getDesignation());
            designation.setStatus(designationDTO.getDesignation() != null ? designationDTO.getStatus() : designation.getStatus());
            designationRepository.save(designation);
            return CommonResponse.builder().code(200).data(designation.getDesignationId()).message("updated successfully ").build();
        }

    }

    @Override
    public CommonResponse findDesignations() {
        List<Designation> designationList = designationRepository.findAll();
        return CommonResponse.builder().code(200).message("successfully").data(designationList).build();
    }

    @Override
    public CommonResponse findDesignationById(String id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Agent Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("successfully").data(designation).build();
    }

    @Override
    public CommonResponse deleteDesignationById(String id) {
        return designationRepository.findById(id)
                .map(designation -> {
                    designationRepository.deleteById(designation.getDesignationId());
                    return CommonResponse.builder().code(200).data(designation.getDesignationId()).message("Deleted successfully").build();
                })
                .orElseGet(() -> CommonResponse.builder().code(400).message("Invalid id").build());
    }
}
