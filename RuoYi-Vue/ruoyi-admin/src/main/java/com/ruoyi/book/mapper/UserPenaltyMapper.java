package com.ruoyi.book.mapper;

import java.util.List;
import com.ruoyi.book.domain.UserPenalty;

/**
 * 用户积分惩罚Mapper接口
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
public interface UserPenaltyMapper 
{
    /**
     * 查询用户积分惩罚
     * 
     * @param penaltyId 用户积分惩罚主键
     * @return 用户积分惩罚
     */
    public UserPenalty selectUserPenaltyByPenaltyId(Long penaltyId);

    /**
     * 查询用户积分惩罚列表
     * 
     * @param userPenalty 用户积分惩罚
     * @return 用户积分惩罚集合
     */
    public List<UserPenalty> selectUserPenaltyList(UserPenalty userPenalty);

    /**
     * 新增用户积分惩罚
     * 
     * @param userPenalty 用户积分惩罚
     * @return 结果
     */
    public int insertUserPenalty(UserPenalty userPenalty);

    /**
     * 修改用户积分惩罚
     * 
     * @param userPenalty 用户积分惩罚
     * @return 结果
     */
    public int updateUserPenalty(UserPenalty userPenalty);

    /**
     * 删除用户积分惩罚
     * 
     * @param penaltyId 用户积分惩罚主键
     * @return 结果
     */
    public int deleteUserPenaltyByPenaltyId(Long penaltyId);

    /**
     * 批量删除用户积分惩罚
     * 
     * @param penaltyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserPenaltyByPenaltyIds(Long[] penaltyIds);
}
