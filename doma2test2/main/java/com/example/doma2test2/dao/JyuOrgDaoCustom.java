package com.example.doma2test2.dao;

import java.sql.Date;
import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.doma2test2.entity.JyuOrg;

@Dao
@ConfigAutowireable
public interface JyuOrgDaoCustom {

    @Select
    List<JyuOrg> selectTestIF(String toriCd, Date startDate, Date endDate);
    // 練習問題13(回答例)
//    List<JyuOrg> selectTestIF(String braCd, Date startDate, Date endDate);

    @Update(sqlFile = true)
    int updateOutFlg(Integer jyuno);
}
