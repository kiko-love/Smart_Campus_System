package com.scs.service.impl;

import com.scs.dao.portraitMapper;
import com.scs.pojo.portrait;
import com.scs.service.portraitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("portraitService")
public class portraitServiceImpl implements portraitService {
    @Autowired
    private portraitMapper portMapper;

    @Override
    public int savePortrait(portrait port) {
        return portMapper.savePortrait(port);
    }

    @Override
    public int getPortraitById(String userId) {
        return portMapper.getPortraitById(userId);
    }
}
