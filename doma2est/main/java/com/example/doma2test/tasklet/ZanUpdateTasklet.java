// 練習問題12(回答例)
package com.example.doma2test.tasklet;

import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.doma2test.common.BatchConstants;
import com.example.doma2test.dao.ZanMstDao;
import com.example.doma2test.dto.JyuOrgDto;
import com.example.doma2test.entity.ZanMst;

@Component
public class ZanUpdateTasklet implements Tasklet {

    @Autowired
    private ZanMstDao zanMstDao;

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Contextからデータを取得
        JobExecution jobExecution = chunkContext.getStepContext().getStepExecution().getJobExecution();
        @SuppressWarnings("unchecked")
        List<JyuOrgDto> stepDtoGet = (List<JyuOrgDto>) jobExecution.getExecutionContext().get(BatchConstants.CONTEXT_KEY_STEP_DTO);

        for (JyuOrgDto jyuOrgDto : stepDtoGet) {
        	String hinCd = jyuOrgDto.getHinCd();
        	int baraSuryo = jyuOrgDto.getBarasu();
            ZanMst zanMst = zanMstDao.selectById(hinCd);
            int zanSuryo = zanMst.getZanSuryo();
            zanMst.setZanSuryo(zanSuryo - baraSuryo);
            zanMstDao.update(zanMst);
        }
        return RepeatStatus.FINISHED;
    }
}
