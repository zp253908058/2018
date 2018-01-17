package com.teeny.wms.web.model.dto;

import org.apache.ibatis.type.Alias;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingBillEntity
 * @since 2017/11/6
 */
@Alias("ReceivingBillEntity")
public class ReceivingBillEntity {

    private int bill_id;
    private int p_id;
    private String MakeDate;
    private String Validdate;
    private String Batchno;
    private float Yqty;
    private float EligibleQty;
    private float TaxPrice;
    private float TaxTotal;
    private float CostPrice;
    private float CostTotal;
    private int S_id;
    private int Location_id;
    private int Supplier_id;
    private int DealStates;
    private int pdastates;
    private int original_id;
    private int rownumber;

    private float retailQty;
    private float WholeQty;

    public ReceivingBillEntity() {
    }

    public ReceivingBillEntity(ReceivingBillEntity entity) {
        this.bill_id = entity.bill_id;
        this.p_id = entity.p_id;
        this.MakeDate = entity.MakeDate;
        this.Validdate = entity.Validdate;
        this.Batchno = entity.Batchno;
        this.Yqty = entity.Yqty;
        this.EligibleQty = entity.EligibleQty;
        this.TaxPrice = entity.TaxPrice;
        this.TaxTotal = entity.TaxTotal;
        this.CostPrice = entity.CostPrice;
        this.CostTotal = entity.CostTotal;
        this.S_id = entity.S_id;
        this.Location_id = entity.Location_id;
        this.Supplier_id = entity.Supplier_id;
        this.DealStates = entity.DealStates;
        this.pdastates = entity.pdastates;
        this.original_id = entity.original_id;
        this.rownumber = entity.rownumber;
        this.retailQty = entity.retailQty;
        this.WholeQty = entity.WholeQty;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getMakeDate() {
        return MakeDate;
    }

    public void setMakeDate(String makeDate) {
        MakeDate = makeDate;
    }

    public String getValiddate() {
        return Validdate;
    }

    public void setValiddate(String validdate) {
        Validdate = validdate;
    }

    public String getBatchno() {
        return Batchno;
    }

    public void setBatchno(String batchno) {
        Batchno = batchno;
    }

    public float getYqty() {
        return Yqty;
    }

    public void setYqty(float yqty) {
        Yqty = yqty;
    }

    public float getEligibleQty() {
        return EligibleQty;
    }

    public void setEligibleQty(float eligibleQty) {
        EligibleQty = eligibleQty;
    }

    public float getTaxPrice() {
        return TaxPrice;
    }

    public void setTaxPrice(float taxPrice) {
        TaxPrice = taxPrice;
    }

    public float getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(float taxTotal) {
        TaxTotal = taxTotal;
    }

    public float getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(float costPrice) {
        CostPrice = costPrice;
    }

    public float getCostTotal() {
        return CostTotal;
    }

    public void setCostTotal(float costTotal) {
        CostTotal = costTotal;
    }

    public int getS_id() {
        return S_id;
    }

    public void setS_id(int s_id) {
        S_id = s_id;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int location_id) {
        Location_id = location_id;
    }

    public int getSupplier_id() {
        return Supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        Supplier_id = supplier_id;
    }

    public int getDealStates() {
        return DealStates;
    }

    public void setDealStates(int dealStates) {
        DealStates = dealStates;
    }

    public int getPdastates() {
        return pdastates;
    }

    public void setPdastates(int pdastates) {
        this.pdastates = pdastates;
    }

    public int getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(int original_id) {
        this.original_id = original_id;
    }

    public int getRownumber() {
        return rownumber;
    }

    public void setRownumber(int rownumber) {
        this.rownumber = rownumber;
    }

    public float getRetailQty() {
        return retailQty;
    }

    public void setRetailQty(float retailQty) {
        this.retailQty = retailQty;
    }

    public float getWholeQty() {
        return WholeQty;
    }

    public void setWholeQty(float wholeQty) {
        WholeQty = wholeQty;
    }

    @Override
    public String toString() {
        return "ReceivingBillEntity{" +
                "bill_id=" + bill_id +
                ", p_id=" + p_id +
                ", MakeDate='" + MakeDate + '\'' +
                ", Validdate='" + Validdate + '\'' +
                ", Batchno='" + Batchno + '\'' +
                ", Yqty=" + Yqty +
                ", EligibleQty=" + EligibleQty +
                ", TaxPrice=" + TaxPrice +
                ", TaxTotal=" + TaxTotal +
                ", CostPrice=" + CostPrice +
                ", CostTotal=" + CostTotal +
                ", S_id=" + S_id +
                ", Location_id=" + Location_id +
                ", Supplier_id=" + Supplier_id +
                ", DealStates=" + DealStates +
                ", pdastates=" + pdastates +
                ", original_id=" + original_id +
                ", rownumber=" + rownumber +
                ", retailQty=" + retailQty +
                ", WholeQty=" + WholeQty +
                '}';
    }
}
