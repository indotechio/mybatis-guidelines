package indotech.io.mybatis.controller;

import indotech.io.mybatis.model.dto.CompanyDTO;
import indotech.io.mybatis.model.response.DataResponse;
import indotech.io.mybatis.model.response.DefaultResponse;
import indotech.io.mybatis.model.response.PaginationDataResponse;
import indotech.io.mybatis.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@RestController
@RequestMapping("/company")
@Api(tags = {"Company Service"})
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @ApiOperation("Get Company List")
    ResponseEntity<PaginationDataResponse<CompanyDTO>> getList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortField,
            @RequestParam(defaultValue = "DESC", required = false) String sortOrder
    ) {
        PaginationDataResponse<CompanyDTO> list = companyService.getDataWithPagination(page, limit, sortField, sortOrder);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get Detailed Company Data")
    ResponseEntity<DataResponse<CompanyDTO>> getById(@PathVariable(value = "id", required = true) BigDecimal id) {
        DataResponse<CompanyDTO> company = companyService.findOne(id);
        return ResponseEntity.ok().body(company);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Create a new Company")
    ResponseEntity<DataResponse<CompanyDTO>> create(@Valid @RequestBody CompanyDTO company) {
        DataResponse<CompanyDTO> data = companyService.create(company);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Update an existing company data")
    ResponseEntity<DataResponse<CompanyDTO>> update(
            @Valid @RequestBody CompanyDTO company,
            @PathVariable(value = "id", required = true) BigDecimal id
    ) {
        DataResponse<CompanyDTO> data = companyService.update(id, company);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Delete a company data")
    public ResponseEntity<DefaultResponse> delete(@PathVariable(value = "id", required = true) BigDecimal id) {
        DefaultResponse doDelete = companyService.delete(id);
        return ResponseEntity.ok().body(doDelete);
    }
}
