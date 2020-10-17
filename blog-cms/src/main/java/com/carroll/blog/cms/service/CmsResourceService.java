package com.carroll.blog.cms.service;

import com.carroll.blog.mbg.mapper.CmsResourceMapper;
import com.carroll.blog.mbg.model.CmsResource;
import com.carroll.blog.mbg.model.CmsResourceExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carroll on 2020/5/31.
 */
@Service
public class CmsResourceService {
    @Autowired
    private CmsResourceMapper mcResourceMapper;


    /**
     * 查询全部资源
     */
    public List<CmsResource> listAll() {
        return mcResourceMapper.selectByExample(new CmsResourceExample());
    }
}
