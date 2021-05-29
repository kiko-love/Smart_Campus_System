package com.scs.dao;

import com.scs.pojo.checkTeacher;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface checkTeaMapper {
    /**
     * 存入老师的当日和下一日的签到状态
     * @param checkTea
     * @return
     */
    int insertCheck(checkTeacher checkTea);

    /**
     * 根据签到日期date ，和签到老师姓名更新当日签到状态
     * @param checkTea
     * @return
     */
    int updateCheck(checkTeacher checkTea);

    /**
     * 根据老师Id查询该老师的所有签到，让老师查看到自己的签到记录
     * @param teacherId
     * @return
     */
    List<checkTeacher> selectById(String teacherId);

    /**
     * 查询是否有对应的老师签到记录
     * @param teacherId
     * @param teacherId
     * @return
     */
    List<checkTeacher> selectByTea(String teacherId,String checkDate);

    /**
     * 查询所有签到信息
     * @return
     */
    List<checkTeacher> selectAll();

}
