package indotech.io.mybatis.controller;

import indotech.io.mybatis.model.dto.CompanyDTO;
import indotech.io.mybatis.model.response.DataResponse;
import indotech.io.mybatis.model.response.DefaultResponse;
import indotech.io.mybatis.model.response.PaginationDataResponse;
import indotech.io.mybatis.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/company")
@Tag(name = "Company Service", description = "API Collections for Company Data")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    @Operation(
            summary = "Get Company List",
            description = "fetches all company entities and their data from data source"
    )
    ResponseEntity<PaginationDataResponse<CompanyDTO>> getList(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String orderField,
            @RequestParam(defaultValue = "DESC", required = false) String orderType
    ) {
        PaginationDataResponse<CompanyDTO> list = companyService.getDataWithPagination(page, limit, orderField, orderType);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get Detailed Company Data",
            description = "fetch detailed company data from data source"
    )
    ResponseEntity<DataResponse<CompanyDTO>> getById(@PathVariable(value = "id", required = true) BigDecimal id) {
        DataResponse<CompanyDTO> company = companyService.findOne(id);
        return ResponseEntity.ok().body(company);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a new Company",
            description = "Create a new Company and save it to the data source"
    )
    ResponseEntity<DataResponse<CompanyDTO>> create(@Valid @RequestBody CompanyDTO company) {
        DataResponse<CompanyDTO> data = companyService.create(company);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Update an existing company data"
    )
    ResponseEntity<DataResponse<CompanyDTO>> update(
            @Valid @RequestBody CompanyDTO company,
            @PathVariable(value = "id", required = true) BigDecimal id
    ) {
        DataResponse<CompanyDTO> data = companyService.update(id, company);
        return ResponseEntity.ok().body(data);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "Delete a company data"
    )
    public ResponseEntity<DefaultResponse> delete(@PathVariable(value = "id", required = true) BigDecimal id) {
        DefaultResponse doDelete = companyService.delete(id);
        return ResponseEntity.ok().body(doDelete);
    }
}
