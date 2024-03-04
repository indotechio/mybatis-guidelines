package indotech.io.mybatis.service;

import indotech.io.mybatis.model.dto.CompanyDTO;
import indotech.io.mybatis.model.response.DataResponse;
import indotech.io.mybatis.model.response.DefaultResponse;
import indotech.io.mybatis.model.response.PaginationDataResponse;

import java.math.BigDecimal;

public interface CompanyService {
    PaginationDataResponse<CompanyDTO> getDataWithPagination(int page, int limit, String sortField, String sortOrder);
    DataResponse<CompanyDTO> findOne(BigDecimal id);
    DataResponse<CompanyDTO> create(CompanyDTO company);
    DataResponse<CompanyDTO> update(BigDecimal id, CompanyDTO company);
    DefaultResponse delete(BigDecimal id);
}
