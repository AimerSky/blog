package com.carroll.blog.cms.service;

import com.carroll.blog.mbg.mapper.LearnTestMapper;
import com.carroll.blog.mbg.model.LearnTest;
import com.carroll.blog.mbg.model.LearnTestExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 学习测试
 * Created by carroll on 2020/12/5.
 */
@Service
public class LearnTestService {
    @Autowired
    private LearnTestMapper learnTestMapper;

    /**
     * 添加
     */
    public int add(LearnTest learnTest) {
        return learnTestMapper.insert(learnTest);
    }

    /**
     * 修改
     */
    public int update(LearnTest learnTest) {
        return learnTestMapper.updateByPrimaryKeySelective(learnTest);
    }

    /**
     * 根据id查询
     */
    public LearnTest get(long learnTestId) {
        return learnTestMapper.selectByPrimaryKey(learnTestId);
    }

    /**
     * 条件查询(分页)
     */
    public List<LearnTest> getCmsList(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        LearnTestExample example = new LearnTestExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        /*LearnTestDataSetver learnTestDataSetver = new LearnTestDataSetver(learnTestMapper);
        learnTestDataSetver.contextLoads();*/
        return learnTestMapper.selectByExample(example);
    }

    /**
     * 根据id删除
     */
    public int deleteById(long learnTestId) {
        return learnTestMapper.deleteByPrimaryKey(learnTestId);
    }


}
