package com.smallhua.org.service;

import com.smallhua.org.common.api.CommonResult;
import com.smallhua.org.common.util.ConstUtil;
import com.smallhua.org.common.util.IdUtil;
import com.smallhua.org.common.util.SessionUtil;
import com.smallhua.org.dao.DictDao;
import com.smallhua.org.dto.UserRole;
import com.smallhua.org.mapper.TDictMapper;
import com.smallhua.org.model.TDict;
import com.smallhua.org.model.TDictExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据字典功能〉
 *
 * @author ZZH
 * @create 2021/5/22
 * @since 1.0.0
 */
@Service
@Transactional
public class DictService {

    @Autowired
    private TDictMapper dictMapper;
    @Autowired
    private DictDao dictDao;

    public CommonResult updOrSaveDict(TDict dict) {
        UserRole us = SessionUtil.getAttribute(ConstUtil.REDIS_USER, UserRole.class);
        int i = 0;
        if (dict.getId() == null) {
            dict.setId(IdUtil.generateIdBySnowFlake());
            dict.setCreateTime(new Date());
            dict.setUpdateTime(new Date());
            dict.setCreateId(us.getId());
            dict.setUpdateId(us.getId());

            i = dictMapper.insertSelective(dict);
        } else {
            TDictExample example = new TDictExample();
            example.createCriteria().andIdEqualTo(dict.getId());
            TDict tDict = dictMapper.selectByExample(example).get(0);

            if (!Objects.equals(tDict.getVersion(), dict.getVersion())) {
                return new CommonResult().failed("信息已改变，请刷新页面在更新");
            }
            BeanUtils.copyProperties(dict, tDict);
            tDict.setCreateTime(new Date());
            tDict.setUpdateTime(new Date());
            tDict.setCreateId(us.getId());
            tDict.setUpdateId(us.getId());

            i = dictMapper.updateByPrimaryKeySelective(tDict);

        }
        return new CommonResult().success(i);
    }

    public CommonResult detailDict(String categoryCode) {
        TDictExample example = new TDictExample();
        example.setOrderByClause("SEQUENCE");
        example.createCriteria()
                .andCategoryCodeEqualTo(categoryCode.trim());
        List<TDict> tDicts = dictMapper.selectByExample(example);
        return new CommonResult().success(tDicts);
    }

    public CommonResult detailOnlyDict(String categoryCode, String dictCode) {
        TDictExample example = new TDictExample();
        example.createCriteria()
                .andCategoryCodeEqualTo(categoryCode.trim())
                .andDictCodeEqualTo(dictCode);
        List<TDict> tDicts = dictMapper.selectByExample(example);
        return new CommonResult().success(tDicts.get(0));
    }

    public CommonResult detailCategorys() {
        return new CommonResult().success(dictDao.queryDictCategorys());
    }
}