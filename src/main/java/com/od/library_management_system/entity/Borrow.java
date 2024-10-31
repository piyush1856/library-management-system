package com.od.library_management_system.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Borrow {

    private Long id;

    private Long memberId;

    private Long bookId;

    private LocalDate borrowedDate;

    private LocalDate dueDate;
}
