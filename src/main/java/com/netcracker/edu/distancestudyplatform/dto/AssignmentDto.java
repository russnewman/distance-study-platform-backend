package com.netcracker.edu.distancestudyplatform.dto;


import lombok.Data;

@Data
public class AssignmentDto {
    private Long id;
    private Integer grade;
    private EventDto event;
    private StudentDto student;
    private DatabaseFileDto dbFile;
    private String commentary;
}
