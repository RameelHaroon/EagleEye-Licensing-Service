package com.thoughtmechanix.licensing_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class License {
    private String id;
    private String productName;
    private String licenseType;
    private String organizationId;
}
