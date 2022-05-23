package cn.ean.emp.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ean
 * @FileName PagePO
 * @Date 2022/5/23 17:58
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBO {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据list
     */
    private List<?> data;

}
