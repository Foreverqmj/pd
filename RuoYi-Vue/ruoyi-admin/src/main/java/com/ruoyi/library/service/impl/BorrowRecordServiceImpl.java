package com.ruoyi.library.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.library.mapper.BorrowRecordMapper;
import com.ruoyi.library.domain.BorrowRecord;
import com.ruoyi.library.service.IBorrowRecordService;

/**
 * 借阅记录Service业务层处理
 * 
 * @author foreverqmj
 * @date 2026-04-08
 */
@Service
public class BorrowRecordServiceImpl implements IBorrowRecordService 
{
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    /**
     * 查询借阅记录
     * 
     * @param recordId 借阅记录主键
     * @return 借阅记录
     */
    @Override
    public BorrowRecord selectBorrowRecordByRecordId(Long recordId)
    {
        return borrowRecordMapper.selectBorrowRecordByRecordId(recordId);
    }

    /**
     * 查询借阅记录列表
     * 
     * @param borrowRecord 借阅记录
     * @return 借阅记录
     */
    @Override
    public List<BorrowRecord> selectBorrowRecordList(BorrowRecord borrowRecord)
    {
        return borrowRecordMapper.selectBorrowRecordList(borrowRecord);
    }

    /**
     * 新增借阅记录
     * 
     * @param borrowRecord 借阅记录
     * @return 结果
     */
    @Override
    public int insertBorrowRecord(BorrowRecord borrowRecord)
    {
        return borrowRecordMapper.insertBorrowRecord(borrowRecord);
    }

    /**
     * 修改借阅记录
     * 
     * @param borrowRecord 借阅记录
     * @return 结果
     */
    @Override
    public int updateBorrowRecord(BorrowRecord borrowRecord)
    {
        return borrowRecordMapper.updateBorrowRecord(borrowRecord);
    }

    /**
     * 批量删除借阅记录
     * 
     * @param recordIds 需要删除的借阅记录主键
     * @return 结果
     */
    @Override
    public int deleteBorrowRecordByRecordIds(Long[] recordIds)
    {
        return borrowRecordMapper.deleteBorrowRecordByRecordIds(recordIds);
    }

    /**
     * 删除借阅记录信息
     * 
     * @param recordId 借阅记录主键
     * @return 结果
     */
    @Override
    public int deleteBorrowRecordByRecordId(Long recordId)
    {
        return borrowRecordMapper.deleteBorrowRecordByRecordId(recordId);
    }
}
