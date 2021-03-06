package com.netcracker.edu.distancestudyplatform.controller;

import com.netcracker.edu.distancestudyplatform.dto.assignment.AssignmentDto;
import com.netcracker.edu.distancestudyplatform.dto.assignment.AssignmentEventDto;
import com.netcracker.edu.distancestudyplatform.dto.assignment.AssignmentPostFormDto;
import com.netcracker.edu.distancestudyplatform.mappers.AssignmentEventMapper;
import com.netcracker.edu.distancestudyplatform.mappers.AssignmentMapper;

import com.netcracker.edu.distancestudyplatform.model.Assignment;
import com.netcracker.edu.distancestudyplatform.repository.AssignmentRepository;
import com.netcracker.edu.distancestudyplatform.repository.EventRepository;
import com.netcracker.edu.distancestudyplatform.repository.StudentRepository;
import com.netcracker.edu.distancestudyplatform.service.AssignmentService;
import com.netcracker.edu.distancestudyplatform.service.DatabaseFileService;
import com.netcracker.edu.distancestudyplatform.service.EventService;
import com.netcracker.edu.distancestudyplatform.service.StudentService;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentRepository assignmentRepository;
    private final EventRepository eventRepository;
    private final StudentRepository studentRepository;

    private final DatabaseFileService dbFileService;
    private final StudentService studentService;
    private final EventService eventService;

    public AssignmentController(AssignmentService assignmentService, AssignmentRepository assignmentRepository, EventRepository eventRepository, StudentRepository studentRepository, DatabaseFileService dbFileService, StudentService studentService, EventService eventService) {
        this.assignmentService = assignmentService;
        this.assignmentRepository = assignmentRepository;
        this.eventRepository = eventRepository;
        this.studentRepository = studentRepository;
        this.dbFileService = dbFileService;
        this.studentService = studentService;
        this.eventService = eventService;
    }

    @GetMapping("/studentAssignments")
    public List<AssignmentDto> getAllStudentAssignments(
            @RequestParam(name = "studentId") Long studentId)
    {
      return assignmentService.getAssignmentByStudent(studentId);
    }

    @GetMapping("/studentSubjectAssignments")
    public List<AssignmentDto> getStudentSubjectAssignments(
            @RequestParam(name = "studentId") Long studentId, @RequestParam(name = "subjectId") Long subjectId)
    {
        return assignmentService.getSubjectAssignments(studentId, subjectId);
    }

    @GetMapping("/studentAssessedAssignments")
    public List<AssignmentDto> getStudentAssessedAssignments(@RequestParam(name = "studentId") Long studentId){
        return assignmentService.getAssessedAssignments(studentId);
    }

    @GetMapping("/studentUnassessedAssignments")
    public List<AssignmentDto> getStudentUnassessedAssignments(@RequestParam(name = "studentId") Long studentId){
        return assignmentService.getUnassessedAssignments(studentId);
    }

    @GetMapping("/studentActiveAssignments")
    public List<AssignmentDto> getStudentActiveAssignments(@RequestParam(name = "studentId") Long studentId){
        return assignmentService.getActiveAssignments(studentId);
    }

    @GetMapping("/{eventId}/assignments")
    public List<AssignmentDto> getEventAssignments(
            @RequestParam(name = "studentId") Long studentId,
            @PathVariable Long eventId
    ) {
        return assignmentService.getEventAssignments(studentId, eventId);
    }

    @GetMapping("/{eventId}/assignments/assessed")
    public List<AssignmentDto> getEventAssesedAssignments(
            @RequestParam(name = "studentId") Long studentId,
            @PathVariable Long eventId
    ) {
        return assignmentService.getEventAssessedAssignments(studentId, eventId);
    }

    @GetMapping("/{eventId}/assignments/unassessed")
    public List<AssignmentDto> getEventUnassesedAssignments(
            @RequestParam(name = "studentId") Long studentId,
            @PathVariable Long eventId
    ) {
        return assignmentService.getEventUnassessedAssignments(studentId, eventId);
    }


    @PostMapping("/updateAssignment")
    public void update(@RequestBody AssignmentDto assingmentDto){
        assignmentService.update(assingmentDto);
    }




    @PostMapping("/addAssignment")
    public void add(@RequestBody AssignmentDto assignment){
        assignmentRepository.save(AssignmentMapper.INSTANCE.toAssignment(assignment));
    }


    @PostMapping("/saveEmptyAssignment")
    public AssignmentDto saveEmptyAssignment(@RequestParam Long eventId,
                                    @RequestParam Long studentId){


        Assignment assignment = new Assignment();
        assignment.setEvent(eventRepository.findById(eventId).orElseThrow());
        assignment.setStudent(studentRepository.findById(studentId).orElseThrow());
        assignment.setTeacherCommentary("The homework wasn't submitted on time");
        assignment.setGrade(2);

        Assignment assignment1 = assignmentRepository.save(assignment);
        return AssignmentMapper.INSTANCE.toDTO(assignment1);
    }


    @PostMapping("/events/{eventId}/assignments")
    public void add(
            @PathVariable Long eventId,
            @RequestBody AssignmentPostFormDto assignment) throws IOException {
        assignmentService.saveAssignmentPostForm(assignment, eventId);
    }

    @GetMapping("/events/{eventId}/assignments")
    public List<AssignmentEventDto> getEventAssignmentByStudent(
            @PathVariable Long eventId,
            @RequestParam Long studentId) {
        return assignmentService.getEventAssignmentsByStudent(eventId, studentId).stream()
                .map(AssignmentEventMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
