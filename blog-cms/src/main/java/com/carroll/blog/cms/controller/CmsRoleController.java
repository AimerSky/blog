package com.carroll.blog.cms.controller;

import com.carroll.blog.cms.service.CmsRoleService;
import com.carroll.blog.common.api.CommonPage;
import com.carroll.blog.common.api.CommonResult;
import com.carroll.blog.mbg.model.CmsRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 * Created by carroll on 2020/6/13.
 */
@Controller
@Api(tags = "CmsRoleController", value = "角色管理")
@RequestMapping("/cms/role")
public class CmsRoleController {
    @Autowired
    private CmsRoleService roleService;


    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "keyword", value = "查询字段", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", required = true)})
    public CommonResult<CommonPage<CmsRole>> list(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        List<CmsRole> roleList = roleService.list(node.get("keyword").asText(), node.get("pageSize").asInt(), node.get("pageNum").asInt());
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("根据id删除角色")
    @RequestMapping(value = "/deletebyid", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "ID", dataType = "int", required = true)})
    public CommonResult deleteById(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        roleService.deleteById(node.get("roleId").asInt());
        return CommonResult.success();
    }

    @ApiOperation("创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody CmsRole cmsRole) {
        int count = roleService.create(cmsRole);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据id查询")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "ID", dataType = "int", required = true)})
    public CommonResult get(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        CmsRole cmsRole = roleService.get(node.get("roleId").asInt());
        return CommonResult.success(cmsRole);
    }

    @ApiOperation("更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestBody CmsRole cmsRole) {
        int count = roleService.update(cmsRole);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
