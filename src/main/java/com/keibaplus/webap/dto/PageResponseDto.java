package com.keibaplus.webap.dto;

import java.util.List;

/**
 * 収支テーブル取得結果用（ページングあり）DTO
 * 
 * @param content       収支テーブルデータ
 * @param page          ページ番号
 * @param size          該当ページのレコード数
 * @param totalElements 該当ユーザーの全件のレコード数
 * @param totalPages    該当ユーザーの全件のページ数
 */
public record PageResponseDto<T>(
                List<T> content,
                int page,
                int size,
                long totalElements,
                int totalPages) {

}
