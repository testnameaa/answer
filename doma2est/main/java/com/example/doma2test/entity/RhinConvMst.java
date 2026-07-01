package com.example.doma2test.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 */
@Entity(listener = RhinConvMstListener.class)
@Table(name = "rhin_conv_mst")
public class RhinConvMst {

    /** */
    @Id
    @Column(name = "tori_cd")
    String toriCd;

    /** */
    @Id
    @Column(name = "rhin_cd_org")
    String rhinCdOrg;

    /** */
    @Column(name = "rhin_cd_dst")
    String rhinCdDst;

    /** 
     * Returns the toriCd.
     * 
     * @return the toriCd
     */
    public String getToriCd() {
        return toriCd;
    }

    /** 
     * Sets the toriCd.
     * 
     * @param toriCd the toriCd
     */
    public void setToriCd(String toriCd) {
        this.toriCd = toriCd;
    }

    /** 
     * Returns the rhinCdOrg.
     * 
     * @return the rhinCdOrg
     */
    public String getRhinCdOrg() {
        return rhinCdOrg;
    }

    /** 
     * Sets the rhinCdOrg.
     * 
     * @param rhinCdOrg the rhinCdOrg
     */
    public void setRhinCdOrg(String rhinCdOrg) {
        this.rhinCdOrg = rhinCdOrg;
    }

    /** 
     * Returns the rhinCdDst.
     * 
     * @return the rhinCdDst
     */
    public String getRhinCdDst() {
        return rhinCdDst;
    }

    /** 
     * Sets the rhinCdDst.
     * 
     * @param rhinCdDst the rhinCdDst
     */
    public void setRhinCdDst(String rhinCdDst) {
        this.rhinCdDst = rhinCdDst;
    }
}