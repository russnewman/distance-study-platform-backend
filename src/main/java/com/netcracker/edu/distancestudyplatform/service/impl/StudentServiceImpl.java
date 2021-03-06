package com.netcracker.edu.distancestudyplatform.service.impl;

import com.netcracker.edu.distancestudyplatform.dto.*;
import com.netcracker.edu.distancestudyplatform.dto.wrappers.StudentDtoList;
import com.netcracker.edu.distancestudyplatform.mappers.StudentMapper;
import com.netcracker.edu.distancestudyplatform.model.Group;
import com.netcracker.edu.distancestudyplatform.model.Student;
import com.netcracker.edu.distancestudyplatform.repository.StudentRepository;
import com.netcracker.edu.distancestudyplatform.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepo;

    public StudentServiceImpl(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public @Nullable Student findByEmail(String email) {
        return studentRepo.findByEmail(email).orElse(null);
    }

    @Override
    public @Nullable Student findById(Long id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public Group getStudentGroup(Long userId) {
        return findById(userId).getGroup();
    }

    @Override
    public Student save(Student s) {
        return studentRepo.save(s);
    }



    @Override
    public List<StudentDto> getStudentsByGroup(Long groupId) {
        return  studentRepo.findAllByGroup_Id(groupId).orElseGet(ArrayList::new)
                .stream()
                .map(StudentMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

}
