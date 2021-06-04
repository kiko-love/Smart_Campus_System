package com.scs.service.impl;

import com.scs.dao.majorMapper;
import com.scs.pojo.major;
import com.scs.service.majorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("/majorService")
public class majorServiceImpl implements majorService {
    @Autowired
    private majorMapper majorMapper;

    @Override
    public int addMajor(major major) {
        return majorMapper.addMajor(major);
    }

    @Override
    public int deleteMajor(String majorName) {
        return majorMapper.deleteMajor(majorName);
    }

    @Override
    public int updateMajor(major major) {
        return majorMapper.updateMajor(major);
    }

    @Override
    public List<major> selectAllMajor() {
        return majorMapper.selectAllMajor();
    }

    @Override
    public List<major> selectCollegeIdByName(String collegeName) {
        return majorMapper.selectCollegeIdByName(collegeName);
    }

    @Override
    public List<major> selectByMajorId(String majorId) {
        return majorMapper.selectByMajorId(majorId);
    }

    @Override
    public List<major> selectAllColleges() {
        return majorMapper.selectAllColleges();
    }

    @Override
    public int batchRemoveMajors(List<String> List) {
        return majorMapper.batchRemoveMajors(List);
    }
}
