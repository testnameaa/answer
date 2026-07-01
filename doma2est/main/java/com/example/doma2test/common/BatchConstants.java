package com.example.doma2test.common;

public class BatchConstants {
    // test用に書いているだけで実際はこういう指定はしない
    public static final String CSV_INPFILE = "src/main/resources/InpFile.csv";
    public static final String CSV_ERRFILE = "src/main/resources/ErrFile.csv";

    // 取込CSVのindex位置
    public static final int INPCSV_IDX_TORI_CD = 0;
    public static final int INPCSV_IDX_BRA_CD = 1;
    public static final int INPCSV_IDX_HAT_DATE = 2;
    public static final int INPCSV_IDX_SYU_DATE = 3;
    public static final int INPCSV_IDX_NOU_DATE = 4;
    public static final int INPCSV_IDX_DEN_NO = 5;
    public static final int INPCSV_IDX_DEN_GYO = 6;
    public static final int INPCSV_IDX_RHIN_CD = 7;
    public static final int INPCSV_IDX_STANI = 8;
    public static final int INPCSV_IDX_SURYO = 9;
    public static final int INPCSV_IDX_GEN_TAN = 10;
    public static final int INPCSV_IDX_GEN_KIN = 11;
    public static final int INPCSV_IDX_BAI_TAN = 12;
    public static final int INPCSV_IDX_BAI_KIN = 13;

    // エラーメッセージ
    public static final String ERRMSG_EMPTY = "必須項目が未入力";
    public static final String ERRMSG_NOT_DATE = "型エラー（日付）";
    public static final String ERRMSG_NOT_NUMBER = "型エラー（数値）";
    public static final String ERRMSG_INVALID = "無効な値";
    public static final String ERRMSG_MINUS = "マイナスエラー";
    // 練習問題07(回答例)
//    public static final String ERRMSG_OVER_LENGTH = "桁数オーバー";
    // 練習問題10(回答例)
//    public static final String ERRMSG_DUPLICATE = "重複エラー";
    public static final String ERRMSG_DB_NOT_FOUND = "ＤＢ未登録";
    // 練習問題12(回答例)
//  public static final String ERRMSG_OUT_OF_STOCK = "在庫不足エラー";

    // Context key
    public static final String CONTEXT_KEY_STEP_DTO = "stepDto";
}