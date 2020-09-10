package com.netcracker.edu.distancestudyplatform.ui.dto;


import com.netcracker.edu.distancestudyplatform.dto.DatabaseFileDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long teacherId;
    private String subjectName;
    private String groupName;
    private String description;
    private Date startTime;
    private Date endTime;

    private DatabaseFileDto databaseFileDto;
}
