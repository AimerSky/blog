package com.carroll.blog.cms.bo;

import com.carroll.blog.cms.dto.CmsManagerStateEnum;
import com.carroll.blog.mbg.model.CmsManager;
import com.carroll.blog.mbg.model.CmsResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 * Created by carroll on 2020/5/30.
 */
public class CmsManagerDetails implements UserDetails {
    private CmsManager cmsManager;
    private List<CmsResource> resourceList;
    public CmsManagerDetails(CmsManager cmsManager, List<CmsResource> resourceList) {
        this.cmsManager = cmsManager;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getResourceId()+":"+role.getName()))
                .collect(Collectors.toList());
    }

    public CmsManager getCmsManager() {
        return cmsManager;
    }

    public void setCmsManager(CmsManager cmsManager) {
        this.cmsManager = cmsManager;
    }

    @Override
    public String getPassword() {
        return cmsManager.getPassword();
    }

    @Override
    public String getUsername() {
        return cmsManager.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return cmsManager.getState().equals(CmsManagerStateEnum.Normal.getValue());
    }
}
