package com.carroll.blog.cms.service;

import cn.hutool.core.util.ObjectUtil;
import com.carroll.blog.cms.dao.CmsRoleDao;
import com.carroll.blog.mbg.mapper.CmsRoleMapper;
import com.carroll.blog.mbg.model.CmsMenu;
import com.carroll.blog.mbg.model.CmsRole;
import com.carroll.blog.mbg.model.CmsRoleExample;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * cms角色实现类
 * Created by carroll on 2020/6/9.
 */
@Service
public class CmsRoleService {
    @Autowired
    private CmsRoleDao cmsRoleDao;
    @Autowired
    private CmsRoleMapper cmsRoleMapper;


    /**
     * 根据管理员ID获取对应菜单
     */
    public ArrayNode getManinMenuList(Integer managerId) {

        List<CmsMenu> cmsMenuList = cmsRoleDao.getManinMenuList(managerId);
        //一级菜单
        List<CmsMenu> oneMenuList = cmsMenuList.stream().filter(cmsMenu -> cmsMenu.getLevel().equals(1)).collect(Collectors.toList());
        //二级菜单
        List<CmsMenu> twoMenuList = cmsMenuList.stream().filter(cmsMenu -> cmsMenu.getLevel().equals(2)).collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode navMenuItems = mapper.createArrayNode();
        for (CmsMenu cmsMenu : oneMenuList) {
            ObjectNode objectNode = mapper.valueToTree(cmsMenu);
            List<CmsMenu> eqMenuIdList = twoMenuList.stream().filter(twoMenu -> twoMenu.getParentId().equals(cmsMenu.getMenuId())).collect(Collectors.toList());
            if (!ObjectUtil.isEmpty(eqMenuIdList)) {
                ArrayNode submenu = mapper.valueToTree(eqMenuIdList);
                objectNode.set("submenu", submenu);
            }
            navMenuItems.add(objectNode);
        }
        return navMenuItems;
    }

    /**
     * 根据用户id 查询所有角色名称
     *
     * @param managerId
     * @return
     */
    public String roleByIdName(Integer managerId) {
        List<String> roleNames = cmsRoleDao.getRoleNames(managerId);
        String name = "";
        for (String roleName : roleNames) {
            name = roleName + "、";
        }
        return name.substring(0, name.length() - 1);
    }

    /**
     * 分页获取角色列表
     */
    public List<CmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        CmsRoleExample example = new CmsRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        return cmsRoleMapper.selectByExample(example);
    }

    /**
     * 根据id删除
     */
    public void deleteById(int roleId) {
        cmsRoleMapper.deleteByPrimaryKey(roleId);
    }


    /**
     * 创建
     */
    public int create(CmsRole cmsRole) {
        return cmsRoleMapper.insertSelective(cmsRole);
    }

    /**
     * 更新
     */
    public int update(CmsRole cmsRole) {
        return cmsRoleMapper.updateByPrimaryKey(cmsRole);
    }

    /**
     * 根据id查询
     */
    public CmsRole get(int roleId) {
        return cmsRoleMapper.selectByPrimaryKey(roleId);
    }


}
