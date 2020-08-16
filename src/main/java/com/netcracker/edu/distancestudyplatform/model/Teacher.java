package com.netcracker.edu.distancestudyplatform.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "teachers")
@EqualsAndHashCode(callSuper = true)
public class Teacher extends AbstractUser {
    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Schedule> schedules;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "teachers_2_subjects",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")}
    )
    private List<Subject> subjects;
}
