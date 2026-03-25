package xdml.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xdml.entity.User;

@Mapper
public interface UserMapper {
	@Select("select * from \"USER\" where id = #{id}")
	User getUserById(@Param("id") Integer id);

}
