package com.keibaplus.webap.common;

/**
 * 各クラスで使用する定数設定用クラス
 * 
 */
public final class CommonConst {
    private CommonConst() {
    }

    // 削除フラグ
    public static final String DEL_FLG_ACTIVE = "0";
    public static final String DEL_FLG_DELETED = "1";

    // テーブル名
    public static final String USERS_TABLE_NAME = "USERS";
    public static final String SHUUSHI_TABLE_NAME = "SHUUSHI";

    public static final int SINGLE_ROW_UPDATE_COUNT = 1;

    public static final int MIN_PAGE_NUM = 0;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int MAX_PAGE_SIZE = 100;

    public static final String HANKAKU_SPACE = " ";
    public static final String LINE_SEPARATOR = "\r\n";

}
