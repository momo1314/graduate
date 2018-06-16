package org.redrock.graduate.service;

import org.redrock.graduate.mapper.StuMapper;
import org.redrock.graduate.bean.Stu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by momo on 2018/6/11
 */
@Service
public class StuService {
    @Autowired
    private StuMapper stumapper;


    public void Insert(Stu stu , String filename){
        stumapper.insert(stu.getOpenid(),stu.getUsernumber(),filename);
    }

    public Object find(Stu stu) {
        return stumapper.find(stu.getOpenid());
    }

    public void update(Stu stu , String filename){
        stumapper.update(stu.getOpenid(),filename);
    }


}
