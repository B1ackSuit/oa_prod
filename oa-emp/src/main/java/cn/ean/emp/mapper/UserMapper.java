package cn.ean.emp.mapper;

import cn.ean.emp.model.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ean
 * @FileName UserMapper
 * @Date 2022/5/23 19:13
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    List<UserPO> getAllUsers(@Param("id") Integer id, @Param("keyWords") String keyWords);
}
