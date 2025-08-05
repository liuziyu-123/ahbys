package com.ahbys.web.controller.system;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ahbys.common.constant.Constants;
import com.ahbys.common.core.domain.AjaxResult;
import com.ahbys.common.core.domain.entity.SysMenu;
import com.ahbys.common.core.domain.entity.SysUser;
import com.ahbys.common.core.domain.model.LoginBody;
import com.ahbys.common.utils.SecurityUtils;
import com.ahbys.framework.web.service.SysLoginService;
import com.ahbys.framework.web.service.SysPermissionService;
import com.ahbys.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    private static final String SECRET_KEY = "1234567890"; // 安全密钥，应与拦截器一致
    private static final long EXPIRATION_TIME = 60 * 1000; // 1分钟过期时间

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        long expireTime = System.currentTimeMillis() + EXPIRATION_TIME;
        String expire = String.valueOf(expireTime);
        String signature = generateSignature(user.getAvatar(), expire);

        user.setAvatar(String.format("%s?expire=%s&sig=%s", user.getAvatar(), expire, signature));
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    private String generateSignature(String filename, String expire) {
        String data = filename + expire + SECRET_KEY;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 实际提供图片的接口
//    @GetMapping(value = "images", produces = MediaType.IMAGE_JPEG_VALUE)
//    public ResponseEntity<byte[]> getImage(@RequestParam String filename) throws IOException {
//        // 实际项目中可能从文件系统、云存储等获取图片
//        Resource resource = new FileSystemResource(RuoYiConfig.getProfile() +filename);
//        Path path = Paths.get(resource.getURI());
//        byte[] imageBytes = Files.readAllBytes(path);
//
//        return ResponseEntity.ok(imageBytes);
//    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
