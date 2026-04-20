package com.ruoyi.book.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户积分惩罚对象 user_penalty
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public class UserPenalty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 惩罚ID */
    private Long penaltyId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 当前积分 */
    @Excel(name = "当前积分")
    private Long points;

    /** 是否禁借(0正常 1禁借) */
    @Excel(name = "是否禁借(0正常 1禁借)")
    private String isBanned;

    /** 禁借截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "禁借截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date banUntil;

    public void setPenaltyId(Long penaltyId) 
    {
        this.penaltyId = penaltyId;
    }

    public Long getPenaltyId() 
    {
        return penaltyId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setPoints(Long points) 
    {
        this.points = points;
    }

    public Long getPoints() 
    {
        return points;
    }

    public void setIsBanned(String isBanned) 
    {
        this.isBanned = isBanned;
    }

    public String getIsBanned() 
    {
        return isBanned;
    }

    public void setBanUntil(Date banUntil) 
    {
        this.banUntil = banUntil;
    }

    public Date getBanUntil() 
    {
        return banUntil;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("penaltyId", getPenaltyId())
            .append("userId", getUserId())
            .append("points", getPoints())
            .append("isBanned", getIsBanned())
            .append("banUntil", getBanUntil())
            .toString();
    }
}
