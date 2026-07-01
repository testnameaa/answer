package com.example.doma2test.tasklet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
// 練習問題10(回答例)
//import java.util.HashMap;
import java.util.List;
// 練習問題10(回答例)
//import java.util.Map;
import java.util.Set;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.example.doma2test.common.BatchConstants;
import com.example.doma2test.common.CheckUtil;
import com.example.doma2test.common.LogContext;
import com.example.doma2test.common.LogUtil;
import com.example.doma2test.dto.JyuOrgStrDto;

@Component
public class ErrChkTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Contextからデータを取得
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        @SuppressWarnings("unchecked")
        List<JyuOrgStrDto> stepDtoGet = (List<JyuOrgStrDto>) jobExecution.getExecutionContext().get(BatchConstants.CONTEXT_KEY_STEP_DTO);
        List<JyuOrgStrDto> stepDtoPut = new ArrayList<>();
        
        LogContext logContext = new LogContext();
        logContext.setFileName(BatchConstants.CSV_INPFILE);
        Path errorPath = Paths.get(BatchConstants.CSV_ERRFILE);

        try (BufferedWriter bw = Files.newBufferedWriter(errorPath, StandardCharsets.UTF_8,
                                                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            PrintWriter pw = new PrintWriter(bw)) {

            // 練習問題10(回答例)
//        	Map<String, Integer> denNoCntMap = new HashMap<>();
//        	for (JyuOrgStrDto dto : stepDtoGet) {
//        	    String denNo = dto.getDenNo();
//        	    String denGyo = dto.getDenGyo();
//        	    if (!CheckUtil.isEmpty(denNo) && !CheckUtil.isEmpty(denGyo)) {
//        	    	String denKey = denNo + denGyo; 
//        	    	denNoCntMap.put(denKey, denNoCntMap.getOrDefault(denKey, 0) + 1);
//        	    }
//        	}
        	
            for (JyuOrgStrDto JyuOrgStrDto : stepDtoGet) {
                boolean errFlg = false;
                Integer lineNumber = JyuOrgStrDto.getLineNumber();
                logContext.setLineNumber(lineNumber);

                // 取引先コード
                if (CheckUtil.isEmpty(JyuOrgStrDto.getToriCd())) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "ToriCd", BatchConstants.ERRMSG_EMPTY);
                }

                // ブランチコード
                if (CheckUtil.isEmpty(JyuOrgStrDto.getBraCd())) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "BraCd", BatchConstants.ERRMSG_EMPTY);
                }

                // 発注日
                if (!CheckUtil.isDate(JyuOrgStrDto.getHatDate())) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "HatDate", BatchConstants.ERRMSG_NOT_DATE);
                }
                // 練習問題09(回答例)
//                String hatDate = JyuOrgStrDto.getHatDate();
//                if (CheckUtil.isEmpty(hatDate)) {
//                    errFlg = true;
//                    LogUtil.logValidatinError(logContext, "hatDate", BatchConstants.ERRMSG_EMPTY);
//                } else if (!CheckUtil.isDate(hatDate)) {
//                    errFlg = true;
//                    LogUtil.logValidatinError(logContext, "hatDate", BatchConstants.ERRMSG_NOT_DATE);
//                }
                
                // 出荷日
                String syuDate = JyuOrgStrDto.getSyuDate();
                if (!CheckUtil.isEmpty(syuDate)) {
                    if (!CheckUtil.isDate(syuDate)) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "SyuDate", BatchConstants.ERRMSG_NOT_DATE);
                    }
                }

                String nouDate = JyuOrgStrDto.getNouDate();
                if (CheckUtil.isEmpty(nouDate)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "NouDate", BatchConstants.ERRMSG_EMPTY);
                } else if (!CheckUtil.isDate(nouDate)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "NouDate", BatchConstants.ERRMSG_NOT_DATE);
                }

                String denGyo = JyuOrgStrDto.getDenGyo();
                if (!CheckUtil.isEmpty(denGyo) && !CheckUtil.isNumber(denGyo)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "DenGyo", BatchConstants.ERRMSG_NOT_NUMBER);
                }

                // 練習問題10(回答例)
//              String denNo = JyuOrgStrDto.getDenNo();
//              String denKey = denNo + denGyo;
//              if (!CheckUtil.isEmpty(denNo) && !CheckUtil.isEmpty(denGyo) && denNoCntMap.get(denKey) > 1) {
//                  errFlg = true;
//                  LogUtil.logValidatinError(logContext, "DenNo, DenGyo", BatchConstants.ERRMSG_DUPLICATE);
//              }
                
                if (CheckUtil.isEmpty(JyuOrgStrDto.getRhinCd())) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "RhinCd", BatchConstants.ERRMSG_EMPTY);
                }

                String stani = JyuOrgStrDto.getStani();
                if (CheckUtil.isEmpty(stani)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "Stani", BatchConstants.ERRMSG_EMPTY);
                } else if (!Set.of("1", "2", "3", "4", "5").contains(stani)) {
                // 練習問題08(回答例)
//                } else if (!Set.of("1", "2", "3", "4", "5", "6").contains(stani)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "Stani", BatchConstants.ERRMSG_INVALID);
                }

                String suryo = JyuOrgStrDto.getSuryo();
                if (CheckUtil.isEmptyNumber(suryo)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "Suryo", BatchConstants.ERRMSG_EMPTY);
                } else if (!CheckUtil.isNumber(suryo)) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "Suryo", BatchConstants.ERRMSG_NOT_NUMBER);
                } else if (Integer.parseInt(suryo) < 0) {
                    errFlg = true;
                    LogUtil.logValidatinError(logContext, "Suryo", BatchConstants.ERRMSG_MINUS);
                // 練習問題07(回答例)
//                } else if (Integer.parseInt(suryo) > 99999) {
//                    errFlg = true;
//                    LogUtil.logValidatinError(logContext, "Suryo", BatchConstants.ERRMSG_OVER_LENGTH);
                }

                String genTan = JyuOrgStrDto.getGenTan();
                if (!CheckUtil.isEmptyNumber(genTan)) {
                    if (!CheckUtil.isNumber(genTan)) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "GenTan", BatchConstants.ERRMSG_NOT_NUMBER);
                    } else if (Integer.parseInt(genTan) < 0) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "GenTan", BatchConstants.ERRMSG_MINUS);
                    }
                }

                String genKin = JyuOrgStrDto.getGenKin();
                if (!CheckUtil.isEmptyNumber(genKin)) {
                    if (!CheckUtil.isNumber(genKin)) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "GenKin", BatchConstants.ERRMSG_NOT_NUMBER);
                    } else if (Integer.parseInt(genKin) < 0) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "GenKin", BatchConstants.ERRMSG_MINUS);
                    }
                }

                String baiTan = JyuOrgStrDto.getBaiTan();
                if (!CheckUtil.isEmptyNumber(baiTan)) {
                    if (!CheckUtil.isNumber(baiTan)) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "BaiTan", BatchConstants.ERRMSG_NOT_NUMBER);
                    } else if (Integer.parseInt(baiTan) < 0) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "BaiTan", BatchConstants.ERRMSG_MINUS);
                    }
                }

                String baiKin = JyuOrgStrDto.getBaiKin();
                if (!CheckUtil.isEmptyNumber(baiKin)) {
                    if (!CheckUtil.isNumber(baiKin)) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "BaiKin", BatchConstants.ERRMSG_NOT_NUMBER);
                    } else if (Integer.parseInt(baiKin) < 0) {
                        errFlg = true;
                        LogUtil.logValidatinError(logContext, "BaiKin", BatchConstants.ERRMSG_MINUS);
                    }
                }

                if (errFlg) {
                    // エラーファイルに出力
                    pw.println("【" + lineNumber + "行目】, " + JyuOrgStrDto.getCsvLine());
                } else {
                    // 次のstepに引継
                    stepDtoPut.add(JyuOrgStrDto);
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
