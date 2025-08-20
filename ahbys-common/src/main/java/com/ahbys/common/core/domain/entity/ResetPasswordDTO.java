package com.ahbys.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 密码重置数据传输对象
 */

public class ResetPasswordDTO {

    @NotBlank(message = "租户名称不能为空")
    private String tenantName;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度不能小于6位")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    public @NotBlank(message = "租户名称不能为空") String getTenantName() {
        return tenantName;
    }

    public void setTenantName(@NotBlank(message = "租户名称不能为空") String tenantName) {
        this.tenantName = tenantName;
    }

    public @NotBlank(message = "用户名不能为空") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "用户名不能为空") String username) {
        this.username = username;
    }

    public @NotBlank(message = "手机号码不能为空") @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "手机号码不能为空") @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号码") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "验证码不能为空") String getCode() {
        return code;
    }

    public void setCode(@NotBlank(message = "验证码不能为空") String code) {
        this.code = code;
    }

    public @NotBlank(message = "密码不能为空") @Size(min = 6, message = "密码长度不能小于6位") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "密码不能为空") @Size(min = 6, message = "密码长度不能小于6位") String password) {
        this.password = password;
    }

    public @NotBlank(message = "确认密码不能为空") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "确认密码不能为空") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}