package com.carroll.blog.cms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.carroll.blog.cms.bo.CmsManagerDetails;
import com.carroll.blog.cms.dao.CmsManagerRoleDao;
import com.carroll.blog.cms.dto.CmsManagerStateEnum;
import com.carroll.blog.cms.dto.UpdateCmsManagerPasswordParam;
import com.carroll.blog.mbg.mapper.CmsManagerLoginLogMapper;
import com.carroll.blog.mbg.mapper.CmsManagerMapper;
import com.carroll.blog.mbg.model.*;
import com.carroll.blog.security.util.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理Service实现类
 * Created by carroll on 2020/5/30.
 */
@Service
public class CmsManagerService {
    private static final Logger logger = LoggerFactory.getLogger(CmsManagerService.class);

    @Autowired
    private CmsManagerMapper cmsManagerMapper;
    @Autowired
    private CmsManagerCacheService cmsManagerCacheService;
    @Autowired
    private CmsResourceService cmsResourceService;
    @Autowired
    private CmsManagerRoleDao cmsManagerRoleDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CmsManagerLoginLogMapper cmsManagerLoginLogMapper;
    @Autowired
    private CmsRoleService cmsRoleService;

    /**
     * 查新列表
     */
    public List<CmsManager> list(String username, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        CmsManagerExample example = new CmsManagerExample();
        CmsManagerExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        example.setOrderByClause("manager_id desc");

        return cmsManagerMapper.selectByExample(example);
    }

    /**
     * 根据用户id获取用户
     */
    public CmsManager getById(Integer id) {
        return cmsManagerMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据用户名获取后台管理员
     */
    public CmsManager getManagerByUsername(String username) {
        CmsManager cmsManager = cmsManagerCacheService.getCmsManager(username);
        if (cmsManager != null) {
            return cmsManager;
        }

        CmsManagerExample example = new CmsManagerExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<CmsManager> cmsManagerList = cmsManagerMapper.selectByExample(example);
        if (cmsManagerList != null && cmsManagerList.size() > 0) {
            cmsManager = cmsManagerList.get(0);
            cmsManagerCacheService.setCmsManager(cmsManager);
            return cmsManager;
        }
        return null;
    }

    /**
     * 获取指定用户的可访问资源
     */
    public List<CmsResource> getResourceList(Integer cmsManagerId) {
        List<CmsResource> resourceList = cmsManagerCacheService.getCmsResourceList(cmsManagerId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = cmsManagerRoleDao.getCmsResourceList(cmsManagerId);
        if (CollUtil.isNotEmpty(resourceList)) {
            cmsManagerCacheService.setCmsResourceList(cmsManagerId, resourceList);
        }
        return resourceList;
    }


    /**
     * 获取用户信息
     */
    public CmsManagerDetails loadUserByUsername(String username) {
        //获取用户信息
        CmsManager cmsManager = getManagerByUsername(username);
        if (cmsManager == null) {
            throw new UsernameNotFoundException("账户名或密码错误");
        } else if (StrUtil.isEmptyIfStr(cmsManager.getState()) || !cmsManager.getState().equals(CmsManagerStateEnum.Normal.getValue())) {
            throw new UsernameNotFoundException("账户状态不正常");
        }
        List<CmsResource> resourceList = getResourceList(cmsManager.getManagerId());
        return new CmsManagerDetails(cmsManager, resourceList);
    }

    /**
     * 登录
     */
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            logger.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     */
    private void insertLoginLog(String username) {
        CmsManager cmsManager = getManagerByUsername(username);
        if (cmsManager == null) {
            return;
        }
        CmsManagerLoginLog loginLog = new CmsManagerLoginLog();
        loginLog.setManagerId(cmsManager.getManagerId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(request.getRemoteAddr());
        cmsManagerLoginLogMapper.insert(loginLog);
    }

    /**
     * 修改密码
     */
    public int updatePassword(UpdateCmsManagerPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        CmsManagerExample example = new CmsManagerExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<CmsManager> cmsManagers = cmsManagerMapper.selectByExample(example);
        if (CollUtil.isEmpty(cmsManagers)) {
            return -2;
        }
        CmsManager cmsManager = cmsManagers.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), cmsManager.getPassword())) {
            return -3;
        }
        cmsManager.setPassword(passwordEncoder.encode(param.getNewPassword()));
        cmsManagerMapper.updateByPrimaryKey(cmsManager);
        cmsManagerCacheService.delCmsManager(cmsManager.getManagerId());
        return 1;
    }


/*    public Map<String, Object> info(Principal principal) {
        String username = principal.getName();
        CmsManager cmsManager = getManagerByUsername(username);
        Map<String, Object> data = new HashMap<>();

        List<CmsMenu> cmsMenuList = cmsRoleService.getMenuList(cmsManager.getManagerId());

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        return data;
    }*/
}
