package com.ahbys.service;

import com.ahbys.common.core.domain.AjaxResult;
import com.ahbys.domain.entity.SysCompany;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 企业信息服务接口
 */
public interface SysCompanyService extends IService<SysCompany> {
    /**
     * 新增企业信息
     * @param company 企业信息对象
     * @return 操作结果
     */
    AjaxResult addCompany(SysCompany company);
}
