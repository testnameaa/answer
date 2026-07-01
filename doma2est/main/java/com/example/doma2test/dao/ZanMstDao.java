package com.example.doma2test.dao;


import com.example.doma2test.entity.ZanMst;
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
public interface ZanMstDao {

    /**
     * @param hinCd
     * @return the ZanMst entity
     */
    @Select
    ZanMst selectById(String hinCd);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(ZanMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(ZanMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(ZanMst entity);
}