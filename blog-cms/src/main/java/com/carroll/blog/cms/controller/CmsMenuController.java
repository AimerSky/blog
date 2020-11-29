package com.carroll.blog.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.carroll.blog.cms.service.CmsMenuService;
import com.carroll.blog.common.api.CommonPage;
import com.carroll.blog.common.api.CommonResult;
import com.carroll.blog.mbg.model.CmsMenu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单管理
 * Created by carroll on 2020/6/13.
 */
@Controller
@Api(tags = "CmsMenuController", value = "菜单管理")
@RequestMapping("/cms/menu")
public class CmsMenuController {

    @Autowired
    private CmsMenuService cmsMenuService;

    @ApiOperation("获取菜单列表-分页")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "keyword", value = "查询字段", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", required = true)})
    public CommonResult<CommonPage<CmsMenu>> list(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        String keyword = node.get("keyword").asText();
        Integer pageSize = node.get("pageSize").asInt();
        Integer pageNum = node.get("pageNum").asInt();
        if (ObjectUtil.isEmpty(pageSize) || ObjectUtil.isEmpty(pageNum)) {
            return CommonResult.validateFailed();
        }
        List<CmsMenu> list = cmsMenuService.getCmsList(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody CmsMenu cmsMenu) {
        int count = cmsMenuService.add(cmsMenu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestBody CmsMenu cmsMenu) {
        int count = cmsMenuService.update(cmsMenu);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据id查询")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "menuId", value = "ID", dataType = "int", required = true)})
    public CommonResult get(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        CmsMenu cmsMenu = cmsMenuService.get(node.get("menuId").asInt());
        return CommonResult.success(cmsMenu);
    }


    @ApiOperation("根据id删除角色")
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "menuId", value = "ID", dataType = "int", required = true)})
    public CommonResult deleteById(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        cmsMenuService.deleteById(node.get("menuId").asInt());
        return CommonResult.success();
    }

    @ApiOperation("查询一级菜单")
    @RequestMapping(value = "/getOne", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getOne(@RequestBody String params) {
        List<CmsMenu> list = cmsMenuService.getOne();
        return CommonResult.success(list);
    }


}
