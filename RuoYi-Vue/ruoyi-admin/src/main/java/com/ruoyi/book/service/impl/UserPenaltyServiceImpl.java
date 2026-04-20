package com.ruoyi.book.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.book.mapper.UserPenaltyMapper;
import com.ruoyi.book.domain.UserPenalty;
import com.ruoyi.book.service.IUserPenaltyService;

/**
 * 用户积分惩罚Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@Service
public class UserPenaltyServiceImpl implements IUserPenaltyService 
{
    @Autowired
    private UserPenaltyMapper userPenaltyMapper;

    /**
     * 查询用户积分惩罚
     * 
     * @param penaltyId 用户积分惩罚主键
     * @return 用户积分惩罚
     */
    @Override
    public UserPenalty selectUserPenaltyByPenaltyId(Long penaltyId)
    {
        return userPenaltyMapper.selectUserPenaltyByPenaltyId(penaltyId);
    }

    /**
     * 查询用户积分惩罚列表
     * 
     * @param userPenalty 用户积分惩罚
     * @return 用户积分惩罚
     */
    @Override
    public List<UserPenalty> selectUserPenaltyList(UserPenalty userPenalty)
    {
        return userPenaltyMapper.selectUserPenaltyList(userPenalty);
    }

    /**
     * 新增用户积分惩罚
     * 
     * @param userPenalty 用户积分惩罚
     * @return 结果
     */
    @Override
    public int insertUserPenalty(UserPenalty userPenalty)
    {
        return userPenaltyMapper.insertUserPenalty(userPenalty);
    }

    /**
     * 修改用户积分惩罚
     * 
     * @param userPenalty 用户积分惩罚
     * @return 结果
     */
    @Override
    public int updateUserPenalty(UserPenalty userPenalty)
    {
        return userPenaltyMapper.updateUserPenalty(userPenalty);
    }

    /**
     * 批量删除用户积分惩罚
     * 
     * @param penaltyIds 需要删除的用户积分惩罚主键
     * @return 结果
     */
    @Override
    public int deleteUserPenaltyByPenaltyIds(Long[] penaltyIds)
    {
        return userPenaltyMapper.deleteUserPenaltyByPenaltyIds(penaltyIds);
    }

    /**
     * 删除用户积分惩罚信息
     * 
     * @param penaltyId 用户积分惩罚主键
     * @return 结果
     */
    @Override
    public int deleteUserPenaltyByPenaltyId(Long penaltyId)
    {
        return userPenaltyMapper.deleteUserPenaltyByPenaltyId(penaltyId);
    }
}
