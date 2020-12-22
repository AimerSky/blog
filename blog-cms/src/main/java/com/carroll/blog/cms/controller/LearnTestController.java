package com.carroll.blog.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.carroll.blog.cms.service.LearnTestService;
import com.carroll.blog.common.api.CommonPage;
import com.carroll.blog.common.api.CommonResult;
import com.carroll.blog.mbg.model.LearnTest;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 学习测试管理
 * Created by carroll on 2020/12/5.
 */
@Controller
@Api(tags = "LearnTestController", value = "学习测试管理")
@RequestMapping("/cms/learnTest")
public class LearnTestController {

    @Autowired
    private LearnTestService learnTestService;

    @ApiOperation("获取学习测试列表-分页")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "keyword", value = "查询字段", dataType = "String", required = false),
            @ApiImplicitParam(name = "pageSize", value = "显示条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageNum", value = "页码", dataType = "int", required = true)})
    public CommonResult<CommonPage<LearnTest>> list(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        String keyword = node.get("keyword").asText();
        Integer pageSize = node.get("pageSize").asInt();
        Integer pageNum = node.get("pageNum").asInt();
        if (ObjectUtil.isEmpty(pageSize) || ObjectUtil.isEmpty(pageNum)) {
            return CommonResult.validateFailed();
        }
        List<LearnTest> list = learnTestService.getCmsList(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(list));
    }

    @ApiOperation("创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody LearnTest learnTest) {
        int count = learnTestService.add(learnTest);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("更新")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@RequestBody LearnTest learnTest) {
        int count = learnTestService.update(learnTest);
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
        LearnTest learnTest = learnTestService.get(node.get("menuId").asLong());
        return CommonResult.success(learnTest);
    }


    @ApiOperation("根据id删除学习测试")
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "menuId", value = "ID", dataType = "int", required = true)})
    public CommonResult deleteById(@RequestBody String params) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readValue(params, JsonNode.class);
        learnTestService.deleteById(node.get("menuId").asLong());
        return CommonResult.success();
    }


}
