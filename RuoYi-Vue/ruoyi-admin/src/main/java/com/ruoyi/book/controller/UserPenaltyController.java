package com.ruoyi.book.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.book.domain.UserPenalty;
import com.ruoyi.book.service.IUserPenaltyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户积分惩罚Controller
 * 
 * @author ruoyi
 * @date 2026-04-08
 */
@RestController
@RequestMapping("/book/penalty")
public class UserPenaltyController extends BaseController
{
    @Autowired
    private IUserPenaltyService userPenaltyService;

    /**
     * 查询用户积分惩罚列表
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserPenalty userPenalty)
    {
        startPage();
        List<UserPenalty> list = userPenaltyService.selectUserPenaltyList(userPenalty);
        return getDataTable(list);
    }

    /**
     * 导出用户积分惩罚列表
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:export')")
    @Log(title = "用户积分惩罚", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserPenalty userPenalty)
    {
        List<UserPenalty> list = userPenaltyService.selectUserPenaltyList(userPenalty);
        ExcelUtil<UserPenalty> util = new ExcelUtil<UserPenalty>(UserPenalty.class);
        util.exportExcel(response, list, "用户积分惩罚数据");
    }

    /**
     * 获取用户积分惩罚详细信息
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:query')")
    @GetMapping(value = "/{penaltyId}")
    public AjaxResult getInfo(@PathVariable("penaltyId") Long penaltyId)
    {
        return success(userPenaltyService.selectUserPenaltyByPenaltyId(penaltyId));
    }

    /**
     * 新增用户积分惩罚
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:add')")
    @Log(title = "用户积分惩罚", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserPenalty userPenalty)
    {
        return toAjax(userPenaltyService.insertUserPenalty(userPenalty));
    }

    /**
     * 修改用户积分惩罚
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:edit')")
    @Log(title = "用户积分惩罚", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserPenalty userPenalty)
    {
        return toAjax(userPenaltyService.updateUserPenalty(userPenalty));
    }

    /**
     * 删除用户积分惩罚
     */
    @PreAuthorize("@ss.hasPermi('book:penalty:remove')")
    @Log(title = "用户积分惩罚", businessType = BusinessType.DELETE)
	@DeleteMapping("/{penaltyIds}")
    public AjaxResult remove(@PathVariable Long[] penaltyIds)
    {
        return toAjax(userPenaltyService.deleteUserPenaltyByPenaltyIds(penaltyIds));
    }
}
