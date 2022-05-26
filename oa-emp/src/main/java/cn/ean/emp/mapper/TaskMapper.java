package cn.ean.emp.mapper;

import cn.ean.emp.model.po.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ean
 * @FileName TaskMapper
 * @Date 2022/5/26 15:08
 **/
@Mapper
public interface TaskMapper extends BaseMapper<UserPO> {

    List<UserPO> getUserLimit10();

}
