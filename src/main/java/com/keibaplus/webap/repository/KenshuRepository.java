package com.keibaplus.webap.repository;

import org.springframework.data.repository.ListCrudRepository;
import com.keibaplus.webap.entity.Kenshu;

/**
 * 券種テーブル用リポジトリ
 */
public interface KenshuRepository extends ListCrudRepository<Kenshu, Integer> {
}
