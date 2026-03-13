package com.example.BuildingPermitMs.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<BuildingPermit> buildingPermits;

    public Applicant() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
    public List<BuildingPermit> getBuildingPermits() { return buildingPermits; }
    public void setBuildingPermits(List<BuildingPermit> buildingPermits) { this.buildingPermits = buildingPermits; }
}
