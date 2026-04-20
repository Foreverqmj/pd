package com.ruoyi.book.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书信息对象 book
 * 
 * @author foreverqmj
 * @date 2026-04-08
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图书ID */
    private Long bookId;

    /** ISBN号 */
    @Excel(name = "ISBN号")
    private String isbn;

    /** 书名 */
    @Excel(name = "书名")
    private String bookName;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 馆藏总量 */
    @Excel(name = "馆藏总量")
    private Long totalCount;

    /** 在馆数量 */
    @Excel(name = "在馆数量")
    private Long availableCount;

    /** 借阅次数 */
    @Excel(name = "借阅次数")
    private Long borrowCount;

    /** 删除标志 */
    private String delFlag;

    public void setBookId(Long bookId) 
    {
        this.bookId = bookId;
    }

    public Long getBookId() 
    {
        return bookId;
    }

    public void setIsbn(String isbn) 
    {
        this.isbn = isbn;
    }

    public String getIsbn() 
    {
        return isbn;
    }

    public void setBookName(String bookName) 
    {
        this.bookName = bookName;
    }

    public String getBookName() 
    {
        return bookName;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }

    public void setTotalCount(Long totalCount) 
    {
        this.totalCount = totalCount;
    }

    public Long getTotalCount() 
    {
        return totalCount;
    }

    public void setAvailableCount(Long availableCount) 
    {
        this.availableCount = availableCount;
    }

    public Long getAvailableCount() 
    {
        return availableCount;
    }

    public void setBorrowCount(Long borrowCount) 
    {
        this.borrowCount = borrowCount;
    }

    public Long getBorrowCount() 
    {
        return borrowCount;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("isbn", getIsbn())
            .append("bookName", getBookName())
            .append("author", getAuthor())
            .append("totalCount", getTotalCount())
            .append("availableCount", getAvailableCount())
            .append("borrowCount", getBorrowCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
