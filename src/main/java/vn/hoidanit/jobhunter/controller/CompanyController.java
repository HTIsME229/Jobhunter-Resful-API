package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.res.*;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.utils.annotation.ApiMessage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1")

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> CreateCompany(@Valid @RequestBody Company company) {
        Company newCompany = this.companyService.handleCreateCompany(company);
        return ResponseEntity.status(201).body(newCompany);
    }

    @ApiMessage("Fetch companies Success")
    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> GetCompanies(@PathVariable Long id) {
        Company company = this.companyService.handleGetCompanyWithId(id);
        return ResponseEntity.status(200).body(company);

    }

    @ApiMessage("Fetch companies Success")
    @GetMapping("/companies")
    public ResponseEntity<RestPaginateDTO> GetAllCompaniesWithPaginate(Pageable pageable, @RequestParam("name") Optional<String> optionalName, @RequestParam("address") Optional<String> optionalAddresss) {
        String name = optionalName.isPresent() ? optionalName.get() : "";
        String address = optionalAddresss.isPresent() ? optionalAddresss.get() : "";
        Page<Company> Company = this.companyService.handleGetCompanyWithPaginate(pageable, name, address);

        List<Company> listCompany = Company.getContent();
        List<ResCompanyDTO> restCompanyDtos = new ArrayList<>();
        for (Company company : listCompany) {
            ResCompanyDTO resCompanyDTO = new ResCompanyDTO(company.getId(), company.getUpdatedBy(), company.getCreatedBy(), company.getCreatedAt(), company.getUpdatedAt(),
                    company.getLogo(), company.getAddress(), company.getDescription(), company.getName());
            restCompanyDtos.add(resCompanyDTO);
        }
        Meta meta = new Meta();
        meta.setCurrent(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalsItems((int) Company.getTotalElements());
        meta.setTotalsPage(Company.getTotalPages());
        RestPaginateDTO restPaginateDTO = new RestPaginateDTO();
        restPaginateDTO.setMeta(meta);
        restPaginateDTO.setResult(restCompanyDtos);
        return ResponseEntity.status(200).body(restPaginateDTO);
    }

    @PutMapping("companies")
    public ResponseEntity<Company> UpdateCompany(@Valid @RequestBody Company Ucompany) {
        Company DataUpdate = this.companyService.handleUpdateCompany(Ucompany);
        return ResponseEntity.status(200).body(DataUpdate);
    }

    @DeleteMapping("companies/{id}")
    public ResponseEntity<RestResponse> DeleteCompany(@PathVariable long id) {
        this.companyService.handleDeleteCompany(id);

        return ResponseEntity.status(200).build();
    }
}
