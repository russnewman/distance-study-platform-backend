package com.netcracker.edu.distancestudyplatform.controller;

import com.netcracker.edu.distancestudyplatform.dto.SubjectDto;
import com.netcracker.edu.distancestudyplatform.dto.SubjectDtoList;
import com.netcracker.edu.distancestudyplatform.mappers.SubjectMapper;
import com.netcracker.edu.distancestudyplatform.service.wrappers.SubjectList;
import com.netcracker.edu.distancestudyplatform.service.SubjectService;
import com.netcracker.edu.distancestudyplatform.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController( SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public List<SubjectDto> findAll() {
        return subjectService.findAll().stream().map(SubjectMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/allSubjects")
    public SubjectDtoList getAllSubjects(){
        return subjectService.getAll();
    }


    @GetMapping("/subject")
    public SubjectDtoList getAllSubjects(@RequestParam(value = "id") Long subjectId){
        return subjectService.getAllById(subjectId);
    }

    @GetMapping("/subjectsByTeacher")
    public SubjectDtoList getSubjectsByTeacher(@RequestParam("teacherId") Long teacherId){
        return subjectService.getSubjectsByTeacherId(teacherId);
    }

}
