package com.example.doma2test.tasklet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.doma2test.common.BatchConstants;
import com.example.doma2test.common.CheckUtil;
import com.example.doma2test.common.DateUtil;
import com.example.doma2test.common.LogContext;
import com.example.doma2test.common.LogUtil;
import com.example.doma2test.dao.BraMstDao;
import com.example.doma2test.dao.DenpyoDao;
import com.example.doma2test.dao.HaisoMstDao;
import com.example.doma2test.dao.HinMstDao;
import com.example.doma2test.dao.RhinMstDao;
import com.example.doma2test.dao.SokMstDao;
import com.example.doma2test.dao.TokMstDao;
import com.example.doma2test.dao.ToriMstDao;
// 練習問題11(回答例)
//import com.example.doma2test.dao.RhinConvMstDao;
// 練習問題12(回答例)
//import com.example.doma2test.dao.ZanMstDao;
import com.example.doma2test.dto.JyuOrgDto;
import com.example.doma2test.dto.JyuOrgStrDto;
import com.example.doma2test.entity.BraMst;
import com.example.doma2test.entity.HaisoMst;
import com.example.doma2test.entity.HinMst;
import com.example.doma2test.entity.RhinMst;
import com.example.doma2test.entity.SokMst;
import com.example.doma2test.entity.TokMst;
import com.example.doma2test.entity.ToriMst;
// 練習問題12(回答例)
//import com.example.doma2test.entity.ZanMst;

@Component
public class MstGetTasklet implements Tasklet {
    @Autowired
    private BraMstDao braMstDao;

    @Autowired
    private HaisoMstDao haisoMstDao;

    @Autowired
    private HinMstDao hinMstDao;

    @Autowired
    private RhinMstDao rhinMstDao;

    @Autowired
    private SokMstDao sokMstDao;

    @Autowired
    private TokMstDao tokMstDao;

    @Autowired
    private ToriMstDao toriMstDao;

    @Autowired
    private DenpyoDao denpyoDao;

    // 練習問題11(回答例)
//    @Autowired
//    private RhinConvMstDao rhinConvMstDao;

    // 練習問題12(回答例)
//  @Autowired
//  private ZanMstDao zanMstDao;
    
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Contextからデータを取得
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        @SuppressWarnings("unchecked")
        List<JyuOrgStrDto> stepDtoGet = (List<JyuOrgStrDto>) jobExecution.getExecutionContext().get(BatchConstants.CONTEXT_KEY_STEP_DTO);
        List<JyuOrgDto> stepDtoPut = new ArrayList<>();
        
        LogContext logContext = new LogContext();
        logContext.setFileName(BatchConstants.CSV_INPFILE);
        Path errorPath = Paths.get(BatchConstants.CSV_ERRFILE);

        try (BufferedWriter bw = Files.newBufferedWriter(errorPath, StandardCharsets.UTF_8,
                                                         StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            PrintWriter pw = new PrintWriter(bw)) {
            String currentDenNoKey = "";
            String currentDenNo = "";
            Integer denGyo = 0;
            for (JyuOrgStrDto JyuOrgStrDto : stepDtoGet) {
                Integer lineNumber = JyuOrgStrDto.getLineNumber();
                logContext.setLineNumber(lineNumber);
                boolean errFlg = false;
                JyuOrgDto jyuOrgDto = new JyuOrgDto();

                // *****************************************************
                // マスタが絡まない分をentityにセット
                // *****************************************************
                jyuOrgDto.setJyuno(null);      // DB側のシーケンスで採番
                String toriCd = JyuOrgStrDto.getToriCd();
                jyuOrgDto.setToriCd(toriCd);
                String braCd = JyuOrgStrDto.getBraCd();
                jyuOrgDto.setBraCd(braCd);
                String hatDate = JyuOrgStrDto.getHatDate(); 
                jyuOrgDto.setHatDate(DateUtil.parseDate(hatDate));
                String nouDate = JyuOrgStrDto.getNouDate();
                jyuOrgDto.setNouDate(DateUtil.parseDate(nouDate));
                jyuOrgDto.setUkebaCd(JyuOrgStrDto.getUkebaCd());
                String rhinCd = JyuOrgStrDto.getRhinCd();
                jyuOrgDto.setRhinCd(rhinCd);
                jyuOrgDto.setOutFlg("0");
                jyuOrgDto.setInsAt(DateUtil.GetSysTimeStamp());

                // *****************************************************
                // マスタ関係分をentityにセット
                // *****************************************************
                // 伝票番号、伝票行ＮＯ
                // 今回に関しては「両方とも入る or 両方とも入れていない」しか考えない前提の作り
                String syuDate = JyuOrgStrDto.getSyuDate();
                String denNo = JyuOrgStrDto.getDenNo();
                if (CheckUtil.isEmptyNumber(denNo)) {
                    String nextDennoKey = toriCd + braCd + hatDate + syuDate + nouDate;
                    if (currentDenNoKey.equals(nextDennoKey)) {
                        denNo = currentDenNo;
                    	denGyo += 1 ;
                    } else {
                        denNo = denpyoDao.getNextDenNo();
                        denNo = StringUtils.leftPad(denNo, 11, '0');
                        currentDenNo = denNo;
                        currentDenNoKey = nextDennoKey;
                        denGyo = 1;
                    }
                    jyuOrgDto.setDenNo(denNo);
                    jyuOrgDto.setDenGyo(denGyo);
                } else {
                    jyuOrgDto.setDenNo(denNo);
                    jyuOrgDto.setDenGyo(Integer.parseInt(JyuOrgStrDto.getDenGyo()));
                }

                // 取引先マスタ
                ToriMst toriMst = toriMstDao.selectById(toriCd);
                if (toriMst == null) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "ToriMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                } else {
                    jyuOrgDto.setToriNm(toriMst.getToriNm());
                }   
                
                BraMst braMst = null;
                RhinMst rhinMst = null;
                // 練習問題11(回答例)
//                RhinConvMst rhinConvMst = null;
                if (toriMst != null) {
                    // ブランチマスタ
                    braMst = braMstDao.selectById(toriCd, braCd);
                    if (braMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "BraMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setBraNm(braMst.getBraNm());
                        jyuOrgDto.setTokCd(braMst.getTokCd());
                        jyuOrgDto.setSokCd(braMst.getSokCd());
                        jyuOrgDto.setHaisoCd(braMst.getHaisoCd());
                    }

                    // 練習問題11(回答例)
                    // 量販商品変換マスタ
//                    rhinConvMst = rhinConvMstDao.selectById(toriCd, rhinCd);
//                    if (rhinConvMst != null) {
//                    	rhinCd = rhinConvMst.getRhinCdDst();
//                    	jyuOrgDto.setRhinCd(rhinCd);
//                    }

                    // 量販商品マスタ
                    rhinMst = rhinMstDao.selectById(toriCd, rhinCd);
                    if (rhinMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "RhinMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setHinCd(rhinMst.getHinCd());
                    }
                }

                TokMst tokMst = null;
                SokMst sokMst = null;
                HaisoMst haisoMst = null;
                if (braMst != null) {
                    // 得意先マスタ
                    tokMst = tokMstDao.selectById(braMst.getTokCd());
                    if (tokMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "TokMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setTokNm(tokMst.getTokNm());
                    }

                    // 倉庫マスタ
                    sokMst = sokMstDao.selectById(braMst.getSokCd());
                    if (sokMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "SokMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setSokNm(sokMst.getSokNm());
                    }

                    // 配送先マスタ
                    haisoMst = haisoMstDao.selectById(braMst.getHaisoCd());
                    if (haisoMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "HaisoMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setHaisoNm(haisoMst.getHaisoNm());
                    }
                }

                HinMst hinMst = null;
                if (rhinMst != null) {
                    // 社内商品マスタ
                    hinMst = hinMstDao.selectById(rhinMst.getHinCd());
                    if (hinMst == null) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "HinMst", BatchConstants.ERRMSG_DB_NOT_FOUND);
                    } else {
                        jyuOrgDto.setHinNm(hinMst.getHinNm());
                    }
                }

                // 出荷日
                if (!CheckUtil.isEmpty(syuDate)) {
                    jyuOrgDto.setSyuDate(DateUtil.parseDate(syuDate));
                } else if(haisoMst != null) {
                    jyuOrgDto.setSyuDate(DateUtil.minusDays(nouDate, haisoMst.getNouKan()));
                }

                // バラ数量
                Integer barasu = 0;
                if (hinMst != null) {
                    Integer suryo = Integer.parseInt(JyuOrgStrDto.getSuryo());
                    Integer csBl = hinMst.getCsBl();
                    Integer blKo = hinMst.getBlKo();
                    switch(JyuOrgStrDto.getStani()) {
                        case "1":
                            barasu = suryo * (csBl * blKo);
                            break;
                        case "2":
                            barasu = suryo * blKo;
                            break;
                        case "3":
                            barasu = suryo;
                            break;
                        case "4":
                            barasu = suryo * 1000;
                            break;
                        case "5":
                            barasu = suryo;
                            break;
                        // 練習問題08(回答例)
//                        case "6":
//                            barasu = suryo * 100;
//                            break;
                    }
                    jyuOrgDto.setBarasu(barasu);
                }

                // 原価単価
                String genTan = JyuOrgStrDto.getGenTan();
                BigDecimal genTanDec = new BigDecimal(0);
                if (!CheckUtil.isEmptyNumber(genTan)) {
                    genTanDec = NumberUtils.toScaledBigDecimal(genTan);
                } else if (hinMst != null) {
                    genTanDec = hinMst.getGenTan();
                }
                jyuOrgDto.setGenTan(genTanDec);

                // 原価金額
                String genKin = JyuOrgStrDto.getGenKin();
                if (!CheckUtil.isEmptyNumber(genKin)) {
                    jyuOrgDto.setGenKin(NumberUtils.toScaledBigDecimal(genKin));
                } else if (hinMst != null) {
                    // バラ数 * 原価単価（切り捨て）
                    BigDecimal barasuDec = new BigDecimal(barasu);
                    BigDecimal genKinDec = barasuDec.multiply(genTanDec).setScale(0, RoundingMode.DOWN);
                    jyuOrgDto.setGenKin(genKinDec);
                }

                // 売価単価
                String baiTan = JyuOrgStrDto.getBaiTan();
                BigDecimal baiTanDec = new BigDecimal(0);
                if (!CheckUtil.isEmptyNumber(baiTan)) {
                    baiTanDec = NumberUtils.toScaledBigDecimal(baiTan);
                } else if (rhinMst != null) {
                    baiTanDec = rhinMst.getBaiTan();
                }
                jyuOrgDto.setBaiTan(baiTanDec);

                // 売価金額
                String baiKin = JyuOrgStrDto.getBaiKin();
                if (!CheckUtil.isEmptyNumber(baiKin)) {
                    jyuOrgDto.setBaiKin(NumberUtils.toScaledBigDecimal(baiKin));
                } else if (rhinMst != null) {
                    // バラ数 * 原価単価（切り捨て）
                    BigDecimal barasuDec = new BigDecimal(barasu);
                    BigDecimal baiKinDec = barasuDec.multiply(baiTanDec);
                    jyuOrgDto.setBaiKin(baiKinDec);
                }

                // 練習問題12(回答例)
                // 在庫不足チェック
//                if (!errFlg) {
//                  ZanMst zanMst = zanMstDao.selectById(rhinMst.getHinCd());
//                  if (zanMst == null) {
//	                  errFlg = true;
//	                  LogUtil.logValidatinError(logContext, "ZaniMst", BatchConstants.ERRMSG_OUT_OF_STOCK);
//                  } else if (zanMst.getZanSuryo() < barasu) {
//	                  errFlg = true;
//	                  LogUtil.logValidatinError(logContext, "ZaniMst", BatchConstants.ERRMSG_OUT_OF_STOCK);
//                  }
//                }
                
                if (errFlg) {
                    // エラーファイルに出力
                    pw.println("【" + lineNumber + "行目】, " + JyuOrgStrDto.getCsvLine());
                } else {
                    // 次のstepに引継
                    stepDtoPut.add(jyuOrgDto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Contextに保存
        jobExecution.getExecutionContext().put(BatchConstants.CONTEXT_KEY_STEP_DTO,stepDtoPut);

        return RepeatStatus.FINISHED;
    }
}
