package com.carroll.blog.cms.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.carroll.blog.cms.bo.CmsManagerDetails;
import com.carroll.blog.cms.dao.CmsManagerDao;
import com.carroll.blog.cms.dto.CmsManagerParam;
import com.carroll.blog.cms.dto.CmsManagerStateEnum;
import com.carroll.blog.cms.dto.UpdateCmsManagerPasswordParam;
import com.carroll.blog.mbg.mapper.CmsManagerLoginLogMapper;
import com.carroll.blog.mbg.mapper.CmsManagerMapper;
import com.carroll.blog.mbg.model.*;
import com.carroll.blog.security.util.JwtTokenUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

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
    private CmsManagerDao cmsManagerDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CmsManagerLoginLogMapper cmsManagerLoginLogMapper;

    /**
     * 查新列表
     */
    public List<CmsManager> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        CmsManagerExample example = new CmsManagerExample();
        CmsManagerExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(CmsManagerStateEnum.Delete.getValue());
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
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
        resourceList = cmsManagerDao.getCmsResourceList(cmsManagerId);
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

    /**
     * 根据id获取管理员信息
     */
    public CmsManagerParam get(int managerId) throws JsonProcessingException {

        CmsManager cmsManager = cmsManagerMapper.selectByPrimaryKey(managerId);
        ObjectMapper mapper = new ObjectMapper();
        String manager = mapper.writeValueAsString(cmsManager);
        //jackson json转bean忽略没有的字段
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CmsManagerParam cmsManagerParam = mapper.readValue(manager, CmsManagerParam.class);
        return cmsManagerParam;
    }


    /**
     * 判断当前账号是否存在
     */
    public boolean usernameIsEmpty(String username) {
        List<String> usernameList = cmsManagerCacheService.getUsername();
        if (ObjectUtil.isNull(usernameList)) {
            //是空查询数据库
            usernameList = cmsManagerDao.getAllUserName();
            cmsManagerCacheService.setUsername(usernameList);
        }
        boolean status = usernameList.stream().anyMatch(usernameStrean -> usernameStrean.equals(username));
        return status;
    }

    /**
     * 创建
     */
    public int create(CmsManagerParam cmsManagerParam) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String manager = mapper.writeValueAsString(cmsManagerParam);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CmsManager cmsManager = mapper.readValue(manager, CmsManager.class);
        //初始密码123456
        cmsManager.setPassword(passwordEncoder.encode("123456"));
        cmsManager.setCreateDate(new Date());
        return cmsManagerMapper.insert(cmsManager);
    }


}
