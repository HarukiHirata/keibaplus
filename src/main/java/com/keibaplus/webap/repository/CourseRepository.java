package com.keibaplus.webap.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.keibaplus.webap.entity.Course;

/**
 * コーステーブル用リポジトリ
 */
public interface CourseRepository extends ListCrudRepository<Course, Integer> {
}
