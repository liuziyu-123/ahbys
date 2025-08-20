package com.ahbys.controller;

import com.ahbys.common.core.domain.AjaxResult;
import com.ahbys.domain.entity.SysCompany;
import com.ahbys.service.SysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企业信息控制器
 */
@RestController
public class SysCompanyController {

    @Autowired
    private SysCompanyService sysCompanyService;
    /**
     * 新增企业
     */
    @PostMapping("/company")
    public AjaxResult add(@RequestBody SysCompany company) {
        return sysCompanyService.addCompany(company);
    }
}
