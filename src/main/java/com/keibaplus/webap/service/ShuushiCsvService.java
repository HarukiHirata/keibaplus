package com.keibaplus.webap.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;

import lombok.RequiredArgsConstructor;

/**
 * CSV出力関連のservice
 */
@Service
@RequiredArgsConstructor
public class ShuushiCsvService {

    // Bean注入
    private final ShuushiQueryService shuushiQueryService;

    /**
     * CSV生成処理
     * 
     * @param searchDto
     * @return CSVデータ
     */
    public byte[] createCsv(ShuushiSearchDto searchDto) {
        // ページングなしで検索条件に紐づいた収支テーブルのデータを検索
        List<ShuushiKenshuCourseDto> rows = shuushiQueryService.findAllShuushiByLoginUserWithoutPaging(searchDto);

        // CSVデータ用文字列
        StringBuilder csv = new StringBuilder();

        // CSVヘッダー
        csv.append("レース日,コース,レース番号,券種,購入金額,払戻金額")
                .append(CommonConst.LINE_SEPARATOR);

        // 収支テーブルの件数分、CSVにデータを記載
        for (ShuushiKenshuCourseDto row : rows) {
            appendRow(csv,
                    row.getRaceDate(),
                    row.getCourseName(),
                    row.getRaceNo() != null && row.getRaceNo() == 0
                            ? ""
                            : row.getRaceNo(),
                    row.getKenshuName(),
                    row.getKounyuuKingaku(),
                    row.getHaraimodoshi());
        }

        // Excelで開いた場合の文字化けを防ぐためUTF-8 BOMを付ける
        return ("\uFEFF" + csv).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * CSV行追加処理
     * 
     * @param csv    CSVデータ用文字列
     * @param values 追加する文字列
     */
    private void appendRow(StringBuilder csv, Object... values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                csv.append(',');
            }
            csv.append(escape(values[i]));
        }
        csv.append(CommonConst.LINE_SEPARATOR);
    }

    /**
     * エスケープ処理
     * 
     * @param value 対象文字列
     * @return エスケープ後の文字列
     */
    private String escape(Object value) {
        // 引数がnullであった場合はバックスラッシュをreturn
        if (value == null) {
            return "\"\"";
        }

        String text = value.toString();

        // Excelの数式として評価されることを防止
        if (!text.isEmpty()
                && (text.charAt(0) == '='
                        || text.charAt(0) == '+'
                        || text.charAt(0) == '-'
                        || text.charAt(0) == '@')) {
            text = "'" + text;
        }

        // 上記記載の記号をエスケープ後、バックスラッシュをエスケープしてreturn
        return "\"" + text.replace("\"", "\"\"") + "\"";
    }
}
