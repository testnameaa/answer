package com.example.doma2test.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 */
@Entity(listener = ZanMstListener.class)
@Table(name = "zan_mst")
public class ZanMst {

    /** */
    @Id
    @Column(name = "hin_cd")
    String hinCd;

    /** */
    @Column(name = "zan_suryo")
    Integer zanSuryo;

    /** 
     * Returns the hinCd.
     * 
     * @return the hinCd
     */
    public String getHinCd() {
        return hinCd;
    }

    /** 
     * Sets the hinCd.
     * 
     * @param hinCd the hinCd
     */
    public void setHinCd(String hinCd) {
        this.hinCd = hinCd;
    }

    /** 
     * Returns the zanSuryo.
     * 
     * @return the zanSuryo
     */
    public Integer getZanSuryo() {
        return zanSuryo;
    }

    /** 
     * Sets the zanSuryo.
     * 
     * @param zanSuryo the zanSuryo
     */
    public void setZanSuryo(Integer zanSuryo) {
        this.zanSuryo = zanSuryo;
    }
}