package com.quchwe.gd.cms.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by quchwe on 2017/4/10 0010.
 */
@Entity
public class RepairInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private Date repairDate;
    private String accidentType;
    private Date createTime;
    private String description;
    private String repairProgress;
    private String filePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getrepairDate() {
        return repairDate;
    }

    public void setrepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescpription() {
        return description;
    }

    public void setDescpription(String description) {
        this.description = description;
    }

    public String getrepairProgress() {
        return repairProgress;
    }

    public void setrepairProgress(String repairProgress) {
        this.repairProgress = repairProgress;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
