package indotech.io.mybatis.mapper;

import indotech.io.mybatis.model.dto.CompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CompanyMapper {
    List<CompanyDTO> getCompanyList(@Param("offset") int offset, @Param("limit") int limit, @Param("orderField") String orderField, @Param("orderType") String orderType);
    Integer countCompany();
    CompanyDTO findById(@Param("id") BigDecimal id);
    void create(CompanyDTO company);
    void update(CompanyDTO company);
    void delete(@Param("id") BigDecimal id);
}
