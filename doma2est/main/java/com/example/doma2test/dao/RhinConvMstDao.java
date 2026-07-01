package com.example.doma2test.dao;


import com.example.doma2test.entity.RhinConvMst;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 */
@Dao
@ConfigAutowireable
public interface RhinConvMstDao {

    /**
     * @param toriCd
     * @param rhinCdOrg
     * @return the RhinConvMst entity
     */
    @Select
    RhinConvMst selectById(String toriCd, String rhinCdOrg);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(RhinConvMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(RhinConvMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(RhinConvMst entity);
}