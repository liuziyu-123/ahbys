package com.ahbys.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 企业信息实体类
 */
@Data
@TableName("SYS_COMPANY")
public class SysCompany {
    /** 企业ID */
    @TableId(type = IdType.AUTO)
    private Long companyId;

    /** 统一社会信用代码 */
    private String socialCreditCode;

    /** 密码（加密存储） */
    private String password;

    /** 单位名称 */
    private String unitName;

    /** 单位性质 */
    private Integer unitNature;

    /** 单位行业 */
    private Integer unitIndustry;

    /** 联系电话 */
    private String phone;

    /** 单位邮箱 */
    private String unitEmail;

    /** 企业类型 */
    private Integer enterpriseType;

    /** 经济类型 */
    private Integer economicType;

    /** 人员规模 */
    private Integer personnelScale;

    /** 申请地址 */
    private String applicationAddress;

    /** 联系人 */
    private String contactPerson;

    /** 联系人电话 */
    private String contactPhone;

    /** 单位地址 */
    private String unitAddress;

    /** 邮政编码 */
    private String postalCode;

    /** 状态（1正常 0停用） */
    private String status;

    /** 删除标志（1未删除 0已删除） */
    private String delFlag;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private LocalDateTime updateTime;
}

