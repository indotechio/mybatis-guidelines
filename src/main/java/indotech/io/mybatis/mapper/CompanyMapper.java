package indotech.io.mybatis.mapper;

import indotech.io.mybatis.model.dto.CompanyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Mapper
@Repository
public interface CompanyMapper {
    List<CompanyDTO> getCompanyList(@Param("offset") int offset, @Param("limit") int limit, @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);
    Integer countCompany();
    CompanyDTO findById(@Param("id") BigDecimal id);
    void create(CompanyDTO company);
    void update(CompanyDTO company);
    void delete(@Param("id") BigDecimal id);
}
