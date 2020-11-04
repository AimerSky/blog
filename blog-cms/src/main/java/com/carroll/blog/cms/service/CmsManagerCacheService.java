package com.carroll.blog.cms.service;

import cn.hutool.core.collection.CollUtil;
import com.carroll.blog.cms.dao.CmsManagerDao;
import com.carroll.blog.mbg.mapper.CmsManagerRoleMapper;
import com.carroll.blog.mbg.model.CmsManager;
import com.carroll.blog.mbg.model.CmsManagerRole;
import com.carroll.blog.mbg.model.CmsManagerRoleExample;
import com.carroll.blog.mbg.model.CmsResource;
import com.carroll.blog.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UmsAdminCacheService实现类
 * Created by carroll on 2020/5/30.
 */
@Service
public class CmsManagerCacheService {
    @Autowired
    private CmsManagerService cmsManagerService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CmsManagerRoleMapper mcManagerRoleMapper;
    @Autowired
    private CmsManagerDao cmsManagerRoleDao;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.mcmanager}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.mcresourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    private String allUsername = "ALLUSERNAME";

    /**
     * 删除后台用户缓存
     */
    public void delCmsManager(Integer cmsManagerId) {
        CmsManager mcManager = cmsManagerService.getById(cmsManagerId);
        if (mcManager != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + mcManager.getName();
            redisService.del(key);
        }
    }

    /**
     * 删除后台用户资源列表缓存
     */
    public void delCmsResourceList(Integer managerId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + managerId;
        redisService.del(key);
    }

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    public void delCmsResourceListByRole(Integer roleId) {
        CmsManagerRoleExample example = new CmsManagerRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<CmsManagerRole> relationList = mcManagerRoleMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getManagerId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    public void delCmsResourceListByRoleIds(List<Integer> roleIds) {
        CmsManagerRoleExample example = new CmsManagerRoleExample();
        example.createCriteria().andRoleIdIn(roleIds);
        List<CmsManagerRole> relationList = mcManagerRoleMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getManagerId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    public void delCmsResourceListByResource(Integer resourceId) {
        List<Integer> adminIdList = cmsManagerRoleDao.getCmsManagerIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    /**
     * 获取缓存后台用户信息
     */
    public CmsManager getCmsManager(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (CmsManager) redisService.get(key);
    }

    /**
     * 设置缓存后台用户信息
     */
    public void setCmsManager(CmsManager mcManager) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + mcManager.getUsername();
        redisService.set(key, mcManager, REDIS_EXPIRE);
    }

    /**
     * 设置缓存后台所有用户账号
     */
    public void setUsername(List<String> usernameList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + allUsername;
        redisService.set(key, usernameList, REDIS_EXPIRE);
    }

    /**
     * 获取缓存后台所有用户账号
     */
    public List<String> getUsername() {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + allUsername;
        return (List<String>) redisService.get(key);
    }

    /**
     * 获取缓存后台用户资源列表
     */
    public List<CmsResource> getCmsResourceList(Integer cmsManagerId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + cmsManagerId;
        return (List<CmsResource>) redisService.get(key);
    }

    /**
     * 设置后台后台用户资源列表
     */
    public void setCmsResourceList(Integer cmsManagerId, List<CmsResource> mcResourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + cmsManagerId;
        redisService.set(key, mcResourceList, REDIS_EXPIRE);
    }
}
