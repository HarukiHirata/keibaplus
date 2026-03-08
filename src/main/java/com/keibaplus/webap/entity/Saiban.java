package com.keibaplus.webap.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("SAIBAN")
public class Saiban {

    @Id
    @Column("TABLE_NAME")
    private String tableName;

    @Column("PREFIX")
    private String prefix;

    @Column("SAIBAN_NO")
    private String saibanNo;

    public Saiban(String tableName, String prefix, String saibanNo) {
        this.tableName = tableName;
        this.prefix = prefix;
        this.saibanNo = saibanNo;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSaibanNo() {
        return saibanNo;
    }

    public void setSaibanNo(String saibanNo) {
        this.saibanNo = saibanNo;
    }

}
