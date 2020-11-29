package com.carroll.blog.cms.service;

import cn.hutool.core.util.ObjectUtil;
import com.carroll.blog.mbg.mapper.CmsMenuMapper;
import com.carroll.blog.mbg.model.CmsMenu;
import com.carroll.blog.mbg.model.CmsMenuExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by carroll on 2020/6/9.
 */
@Service
public class CmsMenuService {
    @Autowired
    private CmsMenuMapper cmsMenuMapper;

    /**
     * 添加
     */
    public int add(CmsMenu cmsMenu) {
        if (ObjectUtil.isEmpty(cmsMenu.getParentId()) || cmsMenu.getParentId() == 0) {
            cmsMenu.setLevel(1);
        } else {
            cmsMenu.setLevel(2);
        }
        return cmsMenuMapper.insert(cmsMenu);
    }

    /**
     * 修改
     */
    public int update(CmsMenu cmsMenu) {
        if (ObjectUtil.isEmpty(cmsMenu.getParentId()) || cmsMenu.getParentId() == 0) {
            cmsMenu.setLevel(1);
        } else {
            cmsMenu.setLevel(2);
        }
        return cmsMenuMapper.updateByPrimaryKeySelective(cmsMenu);
    }

    /**
     * 根据id查询
     */
    public CmsMenu get(int cmsMenuId) {
        return cmsMenuMapper.selectByPrimaryKey(cmsMenuId);
    }

    /**
     * 条件查询(分页)
     */
    public List<CmsMenu> getCmsList(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        CmsMenuExample example = new CmsMenuExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return cmsMenuMapper.selectByExample(example);
    }

    /**
     * 根据id删除
     */
    @Transactional
    public int deleteById(int cmsMenuId) {
        //删除下面的菜单
        CmsMenuExample cmsMenuExample = new CmsMenuExample();
        cmsMenuExample.createCriteria().andParentIdEqualTo(cmsMenuId);
        cmsMenuMapper.deleteByExample(cmsMenuExample);
        return cmsMenuMapper.deleteByPrimaryKey(cmsMenuId);
    }

    /**
     * 查询一级菜单
     */
    public List<CmsMenu> getOne() {
        CmsMenuExample example = new CmsMenuExample();
        example.createCriteria().andLevelEqualTo(1);
        return cmsMenuMapper.selectByExample(example);
    }


}
