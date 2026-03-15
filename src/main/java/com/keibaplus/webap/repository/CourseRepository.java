package com.keibaplus.webap.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.keibaplus.webap.entity.Course;

public interface CourseRepository extends ListCrudRepository<Course, Integer> {
}
