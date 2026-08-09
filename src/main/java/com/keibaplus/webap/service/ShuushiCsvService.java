package com.keibaplus.webap.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShuushiCsvService {

    private final ShuushiQueryService shuushiQueryService;

    public byte[] createCsv(ShuushiSearchDto searchDto) {
        List<ShuushiKenshuCourseDto> rows = shuushiQueryService.findAllShuushiByLoginUserWithoutPaging(searchDto);

        StringBuilder csv = new StringBuilder();

        csv.append("レース日,コース,レース番号,券種,購入金額,払戻金額")
                .append(CommonConst.LINE_SEPARATOR);

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

    private void appendRow(StringBuilder csv, Object... values) {
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                csv.append(',');
            }
            csv.append(escape(values[i]));
        }
        csv.append(CommonConst.LINE_SEPARATOR);
    }

    private String escape(Object value) {
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

        return "\"" + text.replace("\"", "\"\"") + "\"";
    }
}
