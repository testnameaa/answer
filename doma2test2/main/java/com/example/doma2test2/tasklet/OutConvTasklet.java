package com.example.doma2test2.tasklet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.example.doma2test2.common.BatchConstants;
import com.example.doma2test2.dto.JyuOrgDto;
import com.example.doma2test2.dto.TestIFDto;

// マスター情報取得（オリジナルレイアウトに必要な名称やコードを取得する）
@Component
public class OutConvTasklet implements Tasklet {
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Contextからデータを取得
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        @SuppressWarnings("unchecked")
        List<JyuOrgDto> stepDtoGet = (List<JyuOrgDto>) jobExecution.getExecutionContext().get(BatchConstants.CONTEXT_KEY_STEP_DTO);
        
        Path outPath = Paths.get(BatchConstants.CSV_TESTIF);
        try (BufferedWriter bw = Files.newBufferedWriter(outPath, StandardCharsets.UTF_8,
                                                         StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            PrintWriter pw = new PrintWriter(bw)) {
            for (JyuOrgDto jyuOrgDto : stepDtoGet) {
                TestIFDto testIFDto = new TestIFDto();

                testIFDto.setToriCd(StringUtils.right(jyuOrgDto.getToriCd(), 6));
                testIFDto.setToriNm(jyuOrgDto.getToriNm());
                testIFDto.setBraCd("40" + jyuOrgDto.getBraCd());
                testIFDto.setBraNm(jyuOrgDto.getBraNm());
                testIFDto.setSyuDate(jyuOrgDto.getSyuDate());
                testIFDto.setNouDate(jyuOrgDto.getNouDate());
                testIFDto.setDenNo(StringUtils.right(jyuOrgDto.getDenNo(), 8));
                testIFDto.setDenGyo(jyuOrgDto.getDenGyo());
                testIFDto.setRhinCd(StringUtils.right(jyuOrgDto.getRhinCd(), 7));
                testIFDto.setBarasu(jyuOrgDto.getBarasu());
                testIFDto.setBaiTan(jyuOrgDto.getBaiTan());
                testIFDto.setBaiKin(jyuOrgDto.getBaiKin());
                // 練習問題15(回答例)
//                testIFDto.setBiko("備考テスト");
                
                pw.println(testIFDto.toCsvLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }
}
