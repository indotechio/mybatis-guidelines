package indotech.io.mybatis.service.impl;

import indotech.io.mybatis.exception.custom.NotFoundException;
import indotech.io.mybatis.mapper.CompanyMapper;
import indotech.io.mybatis.model.dto.CompanyDTO;
import indotech.io.mybatis.model.response.DataResponse;
import indotech.io.mybatis.model.response.DefaultResponse;
import indotech.io.mybatis.model.response.PaginationDataResponse;
import indotech.io.mybatis.model.response.ResponseMessage;
import indotech.io.mybatis.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;

    @Override
    public PaginationDataResponse<CompanyDTO> getDataWithPagination(int page, int limit, String orderField, String orderType) {
        try {
            Map<String, String> allowedOrder = new HashMap<>();
            allowedOrder.put("createdAt", "created_at");
            String orderColumn = "created_at";
            if ( allowedOrder.containsKey(orderField) ) {
                orderColumn = allowedOrder.getOrDefault(orderField, null);
            }
            orderType = Objects.equals(orderType, "DESC") ? "DESC": "ASC";

            int offset = (page-1)*limit;
            List<CompanyDTO> pageResult = companyMapper.getCompanyList(offset, limit, orderColumn, orderType);
            Integer countResult = companyMapper.countCompany();

            return new PaginationDataResponse<>(
                    true, ResponseMessage.DATA_FETCHED, page, limit,
                    countResult, pageResult
            );
        } catch (Exception e) {
            log.error("Error when get company data with pagination.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<CompanyDTO> findOne(BigDecimal id) {
        try {
            CompanyDTO data = companyMapper.findById(id);
            if ( data != null ) {
                return new DataResponse<>(true, ResponseMessage.DATA_FETCHED, data);
            } else {
                throw  new NotFoundException(ResponseMessage.DATA_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error when get detailed company.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<CompanyDTO> create(CompanyDTO company) {
        try {
            company.setCreatedAt(Timestamp.from(Instant.now()));
            companyMapper.create(company);
            BigDecimal newId = company.getId();

            CompanyDTO data = companyMapper.findById(newId);
            return new DataResponse<>(true, ResponseMessage.DATA_CREATED, data);
        } catch (Exception e) {
            log.error("Error when create a new company.", e);
            throw e;
        }
    }

    @Override
    public DataResponse<CompanyDTO> update(BigDecimal id, CompanyDTO company) {
        try {
            company.setId(id);
            companyMapper.update(company);
            CompanyDTO data = companyMapper.findById(id);
            if ( data != null ) {
                return new DataResponse<>(true, ResponseMessage.DATA_UPDATED, data);
            } else {
                throw  new NotFoundException();
            }
        } catch (Exception e) {
            log.error("Error when update an existing company.", e);
            throw e;
        }
    }

    @Override
    public DefaultResponse delete(BigDecimal id) {
        try {
            companyMapper.delete(id);
            return new DefaultResponse(true, ResponseMessage.DATA_DELETED);
        } catch (Exception e) {
            log.error("Error when delete an existing company.", e);
            throw e;
        }
    }
}
