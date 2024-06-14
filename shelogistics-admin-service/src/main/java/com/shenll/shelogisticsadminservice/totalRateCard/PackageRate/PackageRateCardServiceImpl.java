package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.packageType.PackageType;
import com.shenll.shelogisticsadminservice.packageType.PackageTypeRepository;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PackageRateCardServiceImpl implements PackageRateCardService {

    @Autowired
    PackageRateCardRepository packageRateCardRepository;

    @Autowired
    PackageTypeRepository packageTypeRepository;

    @Autowired
    AgentRepository agentRepository;


    @Override
    public CommonResponse createPackageRateCard(PackageRateCardDTO packageRateCardDTO) {

        PackageType packageType = packageTypeRepository.findById(packageRateCardDTO.getPackageId())
                .orElseThrow(() -> new AppException("Invalid packageType Id", HttpStatus.BAD_REQUEST));

        Agent agent = agentRepository.findById(packageRateCardDTO.getAgentId())
                .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

        Agent destination = agentRepository.findById(packageRateCardDTO.getToDestination())
                .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

        PackageRateCard packageRateCard = new PackageRateCard().builder()
                .rate(packageRateCardDTO.getRate())
                .status(packageRateCardDTO.getStatus())
                .packageId(packageType)
                .agentId(agent)
                .toDestination(destination)
                .build();
        packageRateCardRepository.save(packageRateCard);
        return CommonResponse.builder().data(packageRateCard).code(200).message("successfully").build();
    }

    @Override
    public CommonResponse findPackagesRateCard() {
        List<PackageRateCard> packageRateCardList = packageRateCardRepository.findAll();
        return CommonResponse.builder().data(packageRateCardList).code(200).message("successfully").build();
    }
}
