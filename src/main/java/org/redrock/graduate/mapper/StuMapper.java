package org.redrock.graduate.mapper;

import org.apache.ibatis.annotations.*;
import org.redrock.graduate.bean.Stu;

/**
 * Created by momo on 2018/6/11
 */
@Mapper
public interface StuMapper {
    @Select("SELECT * FROM stu WHERE openid = #{openid} ")
    Object find(@Param("openid") String openid);
    @Insert("INSERT INTO stu(openid ,usernumber,filename) VALUES(#{openid},#{usernumber},#{filename})")
    void insert(@Param("openid") String openid ,@Param("usernumber") String usernumber,@Param("filename") String filename);
    @Update("UPDATE stu set filename = #{filename} WHERE openid = #{openid}")
    void update(@Param("openid") String openid,@Param("filename") String filename);
}
