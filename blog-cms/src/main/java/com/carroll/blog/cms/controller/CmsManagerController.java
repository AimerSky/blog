package com.carroll.blog.cms.controller;

import com.carroll.blog.cms.dto.CmsManagerParam;
import com.carroll.blog.cms.dto.UpdateCmsManagerPasswordParam;
import com.carroll.blog.cms.service.CmsManagerService;
import com.carroll.blog.cms.service.CmsRoleService;
import com.carroll.blog.common.api.CommonPage;
import com.carroll.blog.common.api.CommonResult;
import com.carroll.blog.mbg.model.CmsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试Controller
 * Created by carroll on 2020/5/30.
 */
@Controller
@Api(tags = "CmsManagerController", value = "管理员管理")
@RequestMapping("/cms/manager")
public class CmsManagerController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private CmsManagerService cmsManagerService;
    @Autowired
    private CmsRoleService cmsRoleService;


    @ApiOperation(value = "登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody CmsManagerParam cmsManagerParam, BindingResult result) {
        String token = cmsManagerService.login(cmsManagerParam.getUsername(), cmsManagerParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误!");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("用户列表-分页")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CommonPage<CmsManager>> list(@RequestParam(value = "username", required = false) String username,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<CmsManager> cmsManagers = cmsManagerService.list(username, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(cmsManagers));

    }

    @ApiOperation("修改指定用户密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@RequestBody UpdateCmsManagerPasswordParam cmsManagerPasswordParam) {
        int status = cmsManagerService.updatePassword(cmsManagerPasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getManagerInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        CmsManager cmsManager = cmsManagerService.getManagerByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("nikename", cmsManager.getNickname());
        data.put("roleName", cmsRoleService.roleName(cmsManager.getManagerId()));
        data.put("icon", cmsManager.getIcon());
        data.put("menus", cmsRoleService.getManinMenuList(cmsManager.getManagerId()));
        return CommonResult.success(data);
    }

    @ApiOperation(value = "获取当前登录用户菜单")
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getMenu(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        CmsManager cmsManager = cmsManagerService.getManagerByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("menus", cmsRoleService.getManinMenuList(cmsManager.getManagerId()));
        return CommonResult.success(data);
    }
}
