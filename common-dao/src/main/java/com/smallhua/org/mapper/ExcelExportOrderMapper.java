package com.smallhua.org.mapper;

import com.small.org.modal.dto.ExportOrderForExcel;
import com.smallhua.org.model.ExcelExportOrder;
import com.smallhua.org.model.ExcelExportOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExcelExportOrderMapper {
    long countByExample(ExcelExportOrderExample example);

    int deleteByExample(ExcelExportOrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ExcelExportOrder record);

    int insertSelective(ExcelExportOrder record);

    List<ExcelExportOrder> selectByExample(ExcelExportOrderExample example);

    ExcelExportOrder selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ExcelExportOrder record, @Param("example") ExcelExportOrderExample example);

    int updateByExample(@Param("record") ExcelExportOrder record, @Param("example") ExcelExportOrderExample example);

    int updateByPrimaryKeySelective(ExcelExportOrder record);

    int updateByPrimaryKey(ExcelExportOrder record);

    Long queryTotalRecordsForExportProduct();

    List<ExportOrderForExcel> queryOrderProductForExport(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

    Long queryTotalRecordsForExport();

    List<ExcelExportOrder> queryOrderForExport(@Param("currentPage") int currentPage, @Param("pageSize") int pageSize);
}