package com.thoughtmechanix.licensing_service.client;

import com.thoughtmechanix.licensing_service.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    @GetMapping("/v1/organizations/{organizationId}")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
