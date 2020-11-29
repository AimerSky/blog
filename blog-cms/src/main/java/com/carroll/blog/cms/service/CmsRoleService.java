package com.carroll.blog.cms.service;

import cn.hutool.core.util.ObjectUtil;
import com.carroll.blog.cms.dao.CmsRoleDao;
import com.carroll.blog.cms.dto.CmsRoleStateEnum;
import com.carroll.blog.mbg.mapper.CmsManagerRoleMapper;
import com.carroll.blog.mbg.mapper.CmsMenuMapper;
import com.carroll.blog.mbg.mapper.CmsRoleMapper;
import com.carroll.blog.mbg.mapper.CmsRoleMenuMapper;
import com.carroll.blog.mbg.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private CmsManagerRoleMapper cmsManagerRoleMapper;
    @Autowired
    private CmsMenuMapper cmsMenuMapper;
    @Autowired
    private CmsRoleMenuMapper cmsRoleMenuMapper;


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
        example.createCriteria().andStateNotEqualTo(CmsRoleStateEnum.Delete.getValue());
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
     * 根据id删除(逻辑删除)
     */
    @Transactional
    public void deleteLogicById(int roleId) {
        CmsRole cmsRole = new CmsRole();
        cmsRole.setRoleId(roleId);
        cmsRole.setState(CmsRoleStateEnum.Delete.getValue());
        cmsRole.setManagerCount(0);


        //删除该角色把下面的角色账号关联关系删除
        CmsManagerRoleExample cmre = new CmsManagerRoleExample();
        cmre.createCriteria().andRoleIdEqualTo(roleId);
        cmsManagerRoleMapper.deleteByExample(cmre);
        cmsRoleMapper.updateByPrimaryKeySelective(cmsRole);
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

    /**
     * 返回名称和id map
     */
    public List<Map<String, String>> getNameIdList() {
        return cmsRoleDao.getNameIdList();
    }

    /**
     * 角色菜单
     */
    public List<Map<String, Object>> getRoleMenu(int roleId) {
        CmsRoleMenuExample cmsRoleMenuExample = new CmsRoleMenuExample();
        cmsRoleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
        List<CmsRoleMenu> cmsRoleMenuList = cmsRoleMenuMapper.selectByExample(cmsRoleMenuExample);

        CmsMenuExample cmsMenuExample = new CmsMenuExample();
        List<CmsMenu> cmsMenuList = cmsMenuMapper.selectByExample(cmsMenuExample);
        //一级菜单
        List<CmsMenu> oneMenuList = cmsMenuList.stream().filter(cmsMenu -> cmsMenu.getLevel().equals(1)).collect(Collectors.toList());
        //二级菜单
        List<CmsMenu> twoMenuList = cmsMenuList.stream().filter(cmsMenu -> cmsMenu.getLevel().equals(2)).collect(Collectors.toList());

        List<Map<String, Object>> treeList = new ArrayList<>();

        for (CmsMenu cmsMenu : oneMenuList) {
            Map<String, Object> oneTree = new HashMap<>();
            oneTree.put("title", cmsMenu.getName());
            oneTree.put("id", cmsMenu.getMenuId());
            oneTree.put("expanded", true);
            boolean oneSstatus = cmsRoleMenuList.stream().anyMatch(roleMenu -> roleMenu.getMenuId().equals(cmsMenu.getMenuId()));
            if (oneSstatus) {
                oneTree.put("selected", true);
                oneTree.put("checked", true);
            }
            List<CmsMenu> eqMenuIdList = twoMenuList.stream().filter(twoMenu -> twoMenu.getParentId().equals(cmsMenu.getMenuId())).collect(Collectors.toList());
            List<Map<String, Object>> twoTreeList = new ArrayList<>();
            for (CmsMenu cmsMenuTwo : eqMenuIdList) {
                Map<String, Object> twoTree = new HashMap<>();
                twoTree.put("title", cmsMenuTwo.getName());
                twoTree.put("id", cmsMenuTwo.getMenuId());
                //是否存在
                boolean twoSstatus = cmsRoleMenuList.stream().anyMatch(roleMenu -> roleMenu.getMenuId().equals(cmsMenuTwo.getMenuId()));
                if (twoSstatus) {
                    twoTree.put("selected", true);
                    twoTree.put("checked", true);
                }
                twoTreeList.add(twoTree);
            }
            oneTree.put("children", twoTreeList);
            treeList.add(oneTree);
        }
        return treeList;
    }

    /**
     * 保存角色菜单
     */
    @Transactional
    public void saveRoleMenu(int roleId,  ArrayNode arrayNode) {
        //删除原有菜单
        CmsRoleMenuExample cmsRoleMenuExample= new CmsRoleMenuExample();
        cmsRoleMenuExample.createCriteria().andRoleIdEqualTo(roleId);
        cmsRoleMenuMapper.deleteByExample(cmsRoleMenuExample);

        for (JsonNode node : arrayNode) {
            CmsRoleMenu cmsRoleMenu = new CmsRoleMenu();
            cmsRoleMenu.setRoleId(roleId);
            cmsRoleMenu.setMenuId(node.intValue());
            cmsRoleMenuMapper.insert(cmsRoleMenu);
        }
    }


}
