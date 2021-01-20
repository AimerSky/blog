package com.carroll.blog.search.service;

import com.carroll.blog.search.dao.EsLearnTestDao;
import com.carroll.blog.search.model.EsLearnTestHundred;
import com.carroll.blog.search.repository.EsLearnTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service
public class EsLearnTestService {
    @Autowired
    private EsLearnTestRepository esLearnTestRepository;
    @Autowired
    private EsLearnTestDao esLearnTestDao;

    /**
     * 从数据库中导入所有商品到ES
     */
    public int importAll() {
        List<EsLearnTestHundred> sqlList = esLearnTestDao.getAllEsTestList(null);
        Iterable<EsLearnTestHundred> esProductIterable = esLearnTestRepository.saveAll(sqlList);
        Iterator<EsLearnTestHundred> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    /**
     * 条件查询
     */


}
