package com.example.doma2test2.tasklet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.doma2test2.common.BatchConstants;
import com.example.doma2test2.dao.JyuOrgDaoCustom;
import com.example.doma2test2.dto.JyuOrgDto;
import com.example.doma2test2.entity.JyuOrg;

// データ抽出
@Component
public class DbSelectTasklet implements Tasklet {

    @Autowired
    private JyuOrgDaoCustom jyuOrgDaoCustom;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {       
        List<JyuOrgDto> stepDtoPut = new ArrayList<>();

        // 今回は学習用という事で抽出条件は固定値とする
        Date startDate = Date.valueOf("2025-06-27");
        Date endDate = Date.valueOf("2025-07-02");
        String toriCd = "00000002";
        List<JyuOrg> listJyuOrg = jyuOrgDaoCustom.selectTestIF(toriCd, startDate, endDate);
        
        // 練習問題13(回答例)
//        Date startDate = Date.valueOf("2025-07-28");
//        Date endDate = Date.valueOf("2025-07-28");
//        String braCd = "AB000012";
//        List<JyuOrg> listJyuOrg = jyuOrgDaoCustom.selectTestIF(braCd, startDate, endDate);
        
        for (JyuOrg jyuOrg : listJyuOrg) {
            JyuOrgDto jyuOrgDto = new JyuOrgDto();
            BeanUtils.copyProperties(jyuOrg, jyuOrgDto);
            stepDtoPut.add(jyuOrgDto);
        }

        // Contextに保存
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        jobExecution.getExecutionContext().put(BatchConstants.CONTEXT_KEY_STEP_DTO,stepDtoPut);

        return RepeatStatus.FINISHED;
    }
}
