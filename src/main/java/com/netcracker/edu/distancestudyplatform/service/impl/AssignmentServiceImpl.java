package com.netcracker.edu.distancestudyplatform.service.impl;

import com.netcracker.edu.distancestudyplatform.dto.AssignmentDto;
import com.netcracker.edu.distancestudyplatform.dto.AssignmentPostFormDto;
import com.netcracker.edu.distancestudyplatform.mappers.AssignmentMapper;
import com.netcracker.edu.distancestudyplatform.mappers.DatabaseFileMapper;
import com.netcracker.edu.distancestudyplatform.model.Assignment;
import com.netcracker.edu.distancestudyplatform.model.DatabaseFile;
import com.netcracker.edu.distancestudyplatform.repository.AssignmentRepository;
import com.netcracker.edu.distancestudyplatform.service.AssignmentService;
import com.netcracker.edu.distancestudyplatform.service.EventService;
import com.netcracker.edu.distancestudyplatform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final EventService eventService;
    private final StudentService studentService;

    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, EventService eventService, StudentService studentService){
        this.assignmentRepository = assignmentRepository;
        this.eventService = eventService;
        this.studentService = studentService;
    }

    @Override
    public List<AssignmentDto> getAllAssignments() {
        return AssignmentMapper.INSTANCE.map(assignmentRepository.findAll());
    }

    @Override
    public AssignmentDto getAssignment(Long id) {
        return AssignmentMapper.INSTANCE.toDTO(assignmentRepository.findById(id).orElseGet(Assignment::new));
    }

    @Override
    public List<AssignmentDto> getAssignmentByStudent(Long studentId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findAllByStudent_Id(studentId).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getAssessedAssignments(Long studentId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findAllByStudent_IdAndGradeIsNotNull(studentId).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getUnassessedAssignments(Long studentId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findAllByStudent_IdAndGradeIsNull(studentId).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getActiveAssignments(Long studentId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findAllByStudent_IdAndEvent_StartDateLessThanEqualAndEvent_EndDateGreaterThanEqual(
                studentId, LocalDateTime.now(), LocalDateTime.now()
            ).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getSubjectAssignments(Long studentId, Long subjectId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findByStudent_IdAndEvent_Subject_Id(
                        studentId, subjectId
                ).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getEventAssignments(Long studentId, Long eventId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findByStudent_IdAndEvent_Id(
                        studentId, eventId
                ).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getEventAssessedAssignments(Long studentId, Long eventId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findByStudent_IdAndEvent_IdAndGradeIsNotNull(
                        studentId, eventId
                ).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<AssignmentDto> getEventUnassessedAssignments(Long studentId, Long eventId) {
        return AssignmentMapper.INSTANCE.map(
                assignmentRepository.findByStudent_IdAndEvent_IdAndGradeIsNull(
                        studentId, eventId
                ).orElseGet(ArrayList::new)
        );
    }

    @Override
    public List<Assignment> getAssignmentByEvent(Long eventId) {
        return assignmentRepository.findByEvent_Id(eventId).orElseGet(ArrayList::new);
    }

    @Override
    public void saveAssignmentPostForm(AssignmentPostFormDto assignmentDto, Long eventId) throws IOException {
        Assignment assignment = new Assignment();
        assignment.setEvent(eventService.getFullEventById(eventId));
        assignment.setStudent(studentService.findById(assignmentDto.getStudentId()));
        assignment.setDbFile(DatabaseFileMapper.INSTANCE.toDbFile(assignmentDto.getDbFileDto()));
        assignment.setCommentary(assignmentDto.getCommentary());
        assignment.setGrade(null);
        assignmentRepository.save(assignment);
    }
}