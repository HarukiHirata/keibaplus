package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("KENSHU")
public class Kenshu {
    @Id
    @Column("KENSHU_NO")
    private Integer kenshuNo;

    @Column("KENSHU_NAME")
    private String kenshuName;

    public Kenshu(int kenshuNo, String kenshuName) {
        this.kenshuNo = kenshuNo;
        this.kenshuName = kenshuName;
    }

    public int getKenshuNo() {
        return kenshuNo;
    }

    public void setKenshuNo(int kenshuNo) {
        this.kenshuNo = kenshuNo;
    }

    public String getKenshuName() {
        return kenshuName;
    }

    public void setKenshuName(String kenshuName) {
        this.kenshuName = kenshuName;
    }
}
