package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.entity.Course;
import com.keibaplus.webap.entity.Kenshu;
import com.keibaplus.webap.repository.CourseRepository;
import com.keibaplus.webap.repository.KenshuRepository;

/**
 * マスターテーブルのデータ取得関連のService
 * MasterDataService
 */
@Service
public class MasterDataService {

    private final KenshuRepository kenshuRepository;
    private final CourseRepository courseRepository;

    public MasterDataService(KenshuRepository kenshuRepository, CourseRepository courseRepository) {
        this.kenshuRepository = kenshuRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * 券種テーブル取得処理
     * 
     * @return 券種テーブル取得結果
     */
    public List<Kenshu> findAllKenshu() {
        return kenshuRepository.findAll();
    }

    /**
     * コーステーブル取得処理
     * 
     * @return コーステーブル取得結果
     */
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

}
