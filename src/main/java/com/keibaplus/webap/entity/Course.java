package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * コーステーブル用エンティティ
 */
@Table("course")
public class Course {
    @Id
    @Column("course_no")
    private Integer courseNo;

    @Column("course_name")
    private String courseName;

    public Course(int courseNo, String courseName) {
        this.courseNo = courseNo;
        this.courseName = courseName;
    }

    public Integer getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(Integer courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
