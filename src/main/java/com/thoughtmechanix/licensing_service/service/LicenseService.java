package com.thoughtmechanix.licensing_service.service;

import com.thoughtmechanix.licensing_service.client.OrganizationFeignClient;
import com.thoughtmechanix.licensing_service.config.ServiceConfig;
import com.thoughtmechanix.licensing_service.model.License;
import com.thoughtmechanix.licensing_service.model.Organization;
import com.thoughtmechanix.licensing_service.repository.LicenseRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationFeignClient organizationFeignClient;

    public License getLicense(String organizationId, String licenseId) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        license.setComment(config.getExampleProperty());
        Organization org = retrieveOrgInfo(organizationId);
        if (org != null) {
            license.setOrganizationName(org.getName());
            license.setContactName(org.getContactName());
            license.setContactEmail(org.getContactEmail());
            license.setContactPhone(org.getContactPhone());
        }
        return license;
    }
    @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "licenseService", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrg(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId){
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId( organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    public void saveLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    private Organization retrieveOrgInfo(String organizationId){

        return organizationFeignClient.getOrganization(organizationId);
    }
}
