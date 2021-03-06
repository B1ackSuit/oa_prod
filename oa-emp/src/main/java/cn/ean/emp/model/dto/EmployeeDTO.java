package cn.ean.emp.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.ean.emp.model.po.*;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author ean
 * @FileName EmployeeDTO
 * @Date 2022/5/23 00:51
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="EmployeeDTO对象", description="EmployeeDTO对象")
public class EmployeeDTO implements Serializable {


    @ApiModelProperty(value = "员工编号")
    private Integer id;

    @ApiModelProperty(value = "员工姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate birthday;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "婚姻状况")
    private String wedlock;

    @ApiModelProperty(value = "民族")
    private Integer nationId;

    @ApiModelProperty(value = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌")
    private Integer politicId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "所属部门")
    private Integer departmentId;

    @ApiModelProperty(value = "职称ID")
    private Integer jobLevelId;

    @ApiModelProperty(value = "职位ID")
    private Integer posId;

    @ApiModelProperty(value = "聘用形式")
    private String engageForm;

    @ApiModelProperty(value = "最高学历")
    private String tiptopDegree;

    @ApiModelProperty(value = "所属专业")
    private String specialty;

    @ApiModelProperty(value = "毕业院校")
    private String school;

    @ApiModelProperty(value = "入职日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate beginDate;

    @ApiModelProperty(value = "在职状态")
    private String workState;

    @ApiModelProperty(value = "工号")
    private String workId;

    @ApiModelProperty(value = "合同期限")
    private Double contractTerm;

    @ApiModelProperty(value = "转正日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate conversionTime;

    @ApiModelProperty(value = "离职日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate notWorkDate;

    @ApiModelProperty(value = "合同起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate beginContract;

    @ApiModelProperty(value = "合同终止日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDate endContract;

    @ApiModelProperty(value = "工龄")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套ID")
    private Integer salaryId;

    @ApiModelProperty(value = "民族")
    private NationPO nationPO;

    @ApiModelProperty(value = "政治面貌")
    private PoliticsStatusPO politicsStatusPO;

    @ApiModelProperty(value = "部门")
    private DepartmentPO departmentPO;

    @ApiModelProperty(value = "职称")
    private JobLevelPO jobLevelPO;

    @ApiModelProperty(value = "职位")
    private PositionPO positionPO;

    @ApiModelProperty(value = "工资账套")
    private SalaryPO salaryPO;

}
