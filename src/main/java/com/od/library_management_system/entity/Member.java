package com.od.library_management_system.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Member {
    private Long id;
    private String name;
    private String phone;
    private LocalDate registeredDate;
}
