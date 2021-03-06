package com.netcracker.edu.distancestudyplatform.service;

import com.netcracker.edu.distancestudyplatform.dto.assignment.AssignmentDto;
import com.netcracker.edu.distancestudyplatform.dto.assignment.AssignmentPostFormDto;
import com.netcracker.edu.distancestudyplatform.model.Assignment;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;


public interface AssignmentService {
    List<AssignmentDto> getAllAssignments();
    AssignmentDto getAssignment(Long id);
    List<AssignmentDto> getAssignmentByStudent(Long studentId);
    List<AssignmentDto> getAssessedAssignments(Long studentId);
    List<AssignmentDto> getUnassessedAssignments(Long studentId);
    List<AssignmentDto> getActiveAssignments(Long studentId);
    List<AssignmentDto> getSubjectAssignments(Long studentId, Long subjectId);
    List<AssignmentDto> getEventAssignments(Long studentId, Long eventId);
    List<AssignmentDto> getEventAssessedAssignments(Long studentId, Long eventId);
    List<AssignmentDto> getEventUnassessedAssignments(Long studentId, Long eventId);
//    void saveAssignment(AssignmentDto assignmentDto);
    void update(AssignmentDto assignment);

    List<AssignmentDto> getAssignmentsByEvent(Long eventId);

    void saveAssignmentPostForm(AssignmentPostFormDto assignmentDto, Long eventId) throws IOException;
    List<Assignment> getEventAssignmentsByStudent(Long eventId, Long studentId);
}
