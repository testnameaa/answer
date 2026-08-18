package com.example.doma2test.tasklet;

import java.io.IOException;
// 練習問題04(回答例)
//import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

//練習問題04(回答例)
//import com.example.doma2test.common.AmountUtil;
import com.example.doma2test.common.BatchConstants;
import com.example.doma2test.common.CheckUtil;
import com.example.doma2test.common.DateUtil;
import com.example.doma2test.dto.JyuOrgStrDto;

@Component
public class InConvTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Path inputPath = Paths.get(BatchConstants.CSV_INPFILE);

        try (Stream<String> lines = Files.lines(inputPath, StandardCharsets.UTF_8)) {
            List<String> linesList = lines.collect(Collectors.toList());
            List<JyuOrgStrDto> stepDtoPut = new ArrayList<>();

                Integer lineNumber = 0;
                for (String line : linesList) {
                    lineNumber += 1;

                    //「,」区切りや「"」除去は今回は簡易実装（特殊なパターンは無い想定）
                    String[] parts = line.split(",", -1);
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].replace("\"", "").trim();
                    }

                    // 練習問題06(回答例)
//                    if ("4100012".equals(parts[BatchConstants.INPCSV_IDX_RHIN_CD])) {
//                        continue;
//                    }
                    
                    JyuOrgStrDto JyuOrgStrDto = new JyuOrgStrDto();

                    String tori_Cd = parts[BatchConstants.INPCSV_IDX_TORI_CD];
                    if (!CheckUtil.isEmpty(tori_Cd)) {tori_Cd = StringUtils.leftPad(tori_Cd, 8, '0');}
                    // 練習問題02(回答例)
//                    if (!CheckUtil.isEmpty(tori_Cd)) {
//                    	if ("141601".equals(tori_Cd)) {
//                    		tori_Cd = "00000003";
//                    	} else {
//                    		tori_Cd = StringUtils.leftPad(tori_Cd, 8, '0');
//                    	}
//                    }
                    JyuOrgStrDto.setToriCd(tori_Cd);

                    String bra_Cd = parts[BatchConstants.INPCSV_IDX_BRA_CD];
                    if (!CheckUtil.isEmpty(bra_Cd)) {
                        bra_Cd = StringUtils.right(bra_Cd, 8);
                        bra_Cd = StringUtils.leftPad(bra_Cd, 8, '0');
                      // 練習問題01(回答例)
//                        bra_Cd = StringUtils.right(bra_Cd, 6);
//                        bra_Cd = "AB" + StringUtils.leftPad(bra_Cd, 6, '0');
                    }
                    JyuOrgStrDto.setBraCd(bra_Cd);

                    String hat_Date = parts[BatchConstants.INPCSV_IDX_HAT_DATE];
                    if (StringUtils.isEmpty(hat_Date)) {hat_Date = DateUtil.GetSysString();}
                    JyuOrgStrDto.setHatDate(hat_Date);
                    // 練習問題09(回答例)
//                    JyuOrgStrDto.setHatDate(parts[BatchConstants.INPCSV_IDX_HAT_DATE]);
                    
                    JyuOrgStrDto.setSyuDate(parts[BatchConstants.INPCSV_IDX_SYU_DATE]);
                    JyuOrgStrDto.setNouDate(parts[BatchConstants.INPCSV_IDX_NOU_DATE]);
                    String denNo = parts[BatchConstants.INPCSV_IDX_DEN_NO];
                    if (!CheckUtil.isEmptyNumber(denNo) && CheckUtil.isNumber(denNo)) {denNo = StringUtils.leftPad(denNo, 11, '0');}
                    JyuOrgStrDto.setDenNo(denNo);
                    JyuOrgStrDto.setUkebaCd("1000");
                    JyuOrgStrDto.setDenGyo(parts[BatchConstants.INPCSV_IDX_DEN_GYO]);

                    String rhin_Cd = parts[BatchConstants.INPCSV_IDX_RHIN_CD];
                    if (!CheckUtil.isEmpty(rhin_Cd)) {rhin_Cd = StringUtils.leftPad(rhin_Cd, 15, '0');}
                    JyuOrgStrDto.setRhinCd(rhin_Cd);
                     
                    JyuOrgStrDto.setGenTan(parts[BatchConstants.INPCSV_IDX_GEN_TAN]);
                    // 練習問題03(回答例)
//                    String genTan = parts[BatchConstants.INPCSV_IDX_GEN_TAN];
//	                if (CheckUtil.isEmptyNumber(genTan) || !CheckUtil.isNumber(genTan)) {genTan = "0";}
//	                JyuOrgStrDto.setGenTan(genTan);

                    // 練習問題05(回答例)
//                    JyuOrgStrDto.setGenTan(parts[BatchConstants.INPCSV_IDX_BAI_TAN]);
                    
                    JyuOrgStrDto.setGenKin(parts[BatchConstants.INPCSV_IDX_GEN_KIN]);
	                // 練習問題03(回答例)
//                    String genKin = parts[BatchConstants.INPCSV_IDX_GEN_KIN];
//	                if (CheckUtil.isEmptyNumber(genKin) || !CheckUtil.isNumber(genKin)) {genKin = "0";}
//	                JyuOrgStrDto.setGenKin(genKin);

	                // 練習問題05(回答例)
//                    JyuOrgStrDto.setGenKin(parts[BatchConstants.INPCSV_IDX_BAI_KIN]);
                    
                    JyuOrgStrDto.setBaiTan(parts[BatchConstants.INPCSV_IDX_BAI_TAN]);
	                // 練習問題03(回答例)
//                    String baiTan = parts[BatchConstants.INPCSV_IDX_BAI_TAN];
//	                if (CheckUtil.isEmptyNumber(baiTan) || !CheckUtil.isNumber(baiTan)) {baiTan = "0";}
//	                JyuOrgStrDto.setBaiTan(baiTan);                    

                    // 練習問題05(回答例)                    
//                    JyuOrgStrDto.setBaiTan(parts[BatchConstants.INPCSV_IDX_GEN_TAN]);
                    
                    JyuOrgStrDto.setBaiKin(parts[BatchConstants.INPCSV_IDX_BAI_KIN]);
	                // 練習問題03(回答例)
//                    String baiKin = parts[BatchConstants.INPCSV_IDX_BAI_KIN];
//	                if (CheckUtil.isEmptyNumber(baiKin) || !CheckUtil.isNumber(baiKin)) {baiKin = "0";}
//	                JyuOrgStrDto.setBaiKin(baiKin);
                    
	                // 練習問題05(回答例)
//                    JyuOrgStrDto.setBaiKin(parts[BatchConstants.INPCSV_IDX_GEN_KIN]);

	                // 練習問題04(回答例)
//                    String baiKin = parts[BatchConstants.INPCSV_IDX_BAI_KIN];
//	              	baiKin = AmountUtil.floorToString(baiKin);
//	                JyuOrgStrDto.setBaiKin(baiKin);
	                
                    JyuOrgStrDto.setStani(parts[BatchConstants.INPCSV_IDX_STANI]);
                    JyuOrgStrDto.setSuryo(parts[BatchConstants.INPCSV_IDX_SURYO]);
	                // 練習問題03(回答例)
//                    String suryo = parts[BatchConstants.INPCSV_IDX_SURYO];
//	                if (CheckUtil.isEmptyNumber(suryo) || !CheckUtil.isNumber(suryo)) {suryo = "0";}
//	                JyuOrgStrDto.setSuryo(suryo);
                    
                    JyuOrgStrDto.setCsvLine(line);
                    JyuOrgStrDto.setLineNumber(lineNumber);

                    stepDtoPut.add(JyuOrgStrDto);                   
                }

                // Contextに保存
                JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
                jobExecution.getExecutionContext().put(BatchConstants.CONTEXT_KEY_STEP_DTO,stepDtoPut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }
}
