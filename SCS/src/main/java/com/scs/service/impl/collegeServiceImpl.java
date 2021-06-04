package com.scs.service.impl;


import com.scs.dao.collegeMapper;
import com.scs.pojo.college;
import com.scs.service.collegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("/collegeService")
public class collegeServiceImpl implements collegeService {
    @Autowired
    private collegeMapper collegeMapper;

    @Override
    public int addCollege(college college) {
        return collegeMapper.addCollege(college);
    }

    @Override
    public int deleteCollege(String collegeName) {
        return collegeMapper.deleteCollege(collegeName);
    }

    @Override
    public int updateCollege(college college) {
        return collegeMapper.updateCollege(college);
    }

    @Override
    public List<college> selectAllCollege() {
        return collegeMapper.selectAllCollege();
    }

    @Override
    public List<college> selectCollegeById(String collegeId) {
        return collegeMapper.selectCollegeById(collegeId);
    }

    @Override
    public List<college> selectCollege(String key) {
        return collegeMapper.selectCollege(key);
    }

    @Override
    public int batchRemoveCollege(List<String> List) {
        return collegeMapper.batchRemoveCollege(List);
    }
}
