package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(Company company) {
        Company newCompany = new Company();
        newCompany.setName(company.getName());
        newCompany.setAddress(company.getAddress());
        newCompany.setDescription(company.getDescription());
        newCompany.setLogo(company.getLogo());
        return this.companyRepository.save(company);
    }

    public List<Company> handleGetAllCompany() {
        return this.companyRepository.findAll();
    }

    public Page<Company> handleGetCompanyWithPaginate(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
    }

    public Company handleUpdateCompany(Company Ucompany) {
        Company currentCompany = this.companyRepository.findById(Ucompany.getId()).get();
        currentCompany.setName(Ucompany.getName());
        currentCompany.setAddress(Ucompany.getAddress());
        currentCompany.setDescription(Ucompany.getDescription());
        currentCompany.setLogo(Ucompany.getLogo());
        return this.companyRepository.save(currentCompany);
    }

    public void handleDeleteCompany(long id) throws IllegalArgumentException {
        Optional<Company> company = this.companyRepository.findById(id);

        if (!company.isPresent())
            throw new IllegalArgumentException("Company not found");

        this.companyRepository.deleteById(id);

    }
}
