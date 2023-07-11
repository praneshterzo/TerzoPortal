package com.example.Portal.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`ApplyLeave`")
public class ApplyLeave {

    @ManyToOne
    @JoinColumn(name="empId")
    private Employee employee;
    @Id
    private int applyLeaveid;
    private String LeaveType;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private boolean approval;
}
