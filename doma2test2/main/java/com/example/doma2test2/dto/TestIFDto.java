package com.example.doma2test2.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.example.doma2test2.common.BatchConstants;
import com.example.doma2test2.common.DateUtil;

import lombok.Data;

@Data
public class TestIFDto {

    String toriCd;
    String toriNm;
    String braCd;
    String braNm;
    Date syuDate;
    Date nouDate;
    String tokCd;
    String tokNm;
    String denNo;
    Integer denGyo;
    String rhinCd;
    Integer barasu;
    BigDecimal baiTan;
    BigDecimal baiKin;
    // 練習問題15(回答例)
//    String biko;

    public String toCsvLine() {        
        StringBuilder sb = new StringBuilder(); 
        
        sb.append(toriCd).append(",");
        sb.append(toriNm).append(",");
        sb.append(braCd).append(",");
        sb.append(braNm).append(",");
        sb.append(DateUtil.parseString(syuDate, BatchConstants.DATE_FORMAT_SLASH)).append(",");
        sb.append(DateUtil.parseString(nouDate, BatchConstants.DATE_FORMAT_SLASH)).append(",");
        // 練習問題14(回答例)
//        sb.append(DateUtil.parseString(syuDate, BatchConstants.DATE_FORMAT_NO)).append(",");
//        sb.append(DateUtil.parseString(nouDate, BatchConstants.DATE_FORMAT_NO)).append(",");
        sb.append(denNo).append(",");
        sb.append(denGyo).append(",");
        sb.append(rhinCd).append(",");
        sb.append(barasu).append(",");
        sb.append(baiTan).append(",");
        sb.append(baiKin);
        // 練習問題15(回答例)
//        sb.append(baiKin).append(",");
//        sb.append(biko);

        return sb.toString();
    }
}
