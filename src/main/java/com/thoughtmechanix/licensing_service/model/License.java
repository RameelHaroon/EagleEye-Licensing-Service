package com.thoughtmechanix.licensing_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "license")
public class License {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Transient
    private String organizationName ="";
    @Transient
    private String contactName ="";
    @Transient
    private String contactPhone ="";
    @Transient
    private String contactEmail ="";
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;
    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;
}
