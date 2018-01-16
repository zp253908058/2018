package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.model.StringMapEntity;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("signService")
public class SignServiceImpl implements SignService {

    private CommonMapper mCommonMapper;

    @Autowired
    public void setSystemMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public List<StringMapEntity> getAccountSets() {
        return mCommonMapper.getAccountSets();
    }
}
