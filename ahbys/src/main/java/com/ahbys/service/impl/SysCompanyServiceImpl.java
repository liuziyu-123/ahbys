package com.ahbys.service.impl;

import com.ahbys.common.core.domain.AjaxResult;
import com.ahbys.mapper.SysCompanyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ahbys.domain.entity.SysCompany;
import com.ahbys.service.SysCompanyService;
import com.ahbys.common.utils.SecurityUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

/**
 * 企业信息服务实现类
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany>
        implements SysCompanyService {

    @Override
    public AjaxResult addCompany(SysCompany company) {
        // 1. 数据校验
        if (!validateCompanyData(company)) {
            return AjaxResult.error("企业信息校验失败");
        }

        // 2. 设置默认值和系统字段
        setDefaultValues(company);

        // 3. 密码加密（使用BCrypt加密算法）
        encryptPassword(company);

        // 4. 执行新增操作
        boolean saveResult = save(company);
        if (saveResult) {
            return AjaxResult.success("企业信息添加成功", company.getCompanyId());
        } else {
            return AjaxResult.error("企业信息添加失败");
        }
    }

    /**
     * 数据校验
     */
    private boolean validateCompanyData(SysCompany company) {
        // 必传字段校验
        if (!StringUtils.hasText(company.getSocialCreditCode())) {
            return false;
        }
        if (!StringUtils.hasText(company.getPassword())) {
            return false;
        }
        if (!StringUtils.hasText(company.getUnitName())) {
            return false;
        }
        // 统一社会信用代码格式校验（简单示例）
        if (company.getSocialCreditCode().length() != 18) {
            return false;
        }
        // 联系电话格式校验
        if (company.getPhone().length() != 11) {
            return false;
        }
        return true;
    }

    /**
     * 设置默认值
     */
    private void setDefaultValues(SysCompany company) {
        // 设置创建信息
        company.setCreateBy(SecurityUtils.getUsername()); // 当前登录用户
        company.setCreateTime(LocalDateTime.now());

        // 设置默认状态
        if (StringUtils.isEmpty(company.getStatus())) {
            company.setStatus("1"); // 默认为正常
        }
        if (StringUtils.isEmpty(company.getDelFlag())) {
            company.setDelFlag("1"); // 默认为未删除
        }

        // 设置默认类型值
        if (company.getEnterpriseType() == null) {
            company.setEnterpriseType(0);
        }
        if (company.getEconomicType() == null) {
            company.setEconomicType(0);
        }
        if (company.getPersonnelScale() == null) {
            company.setPersonnelScale(0);
        }
    }

    /**
     * 密码加密
     */
    private void encryptPassword(SysCompany company) {
        String rawPassword = company.getPassword();
        // 使用BCrypt加密（需引入spring-security-core依赖）
        String encodedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        company.setPassword(encodedPassword);
    }
}

