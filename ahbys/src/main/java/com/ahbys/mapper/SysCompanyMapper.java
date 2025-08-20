package com.ahbys.mapper;

import com.ahbys.domain.entity.SysCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业信息数据访问接口
 */
@Mapper
public interface SysCompanyMapper extends BaseMapper<SysCompany> {
    // 继承BaseMapper后自动拥有CRUD方法，无需额外定义
}
