package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.service.Specfication.SpecificationsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final SpecificationsBuilder specificationsBuilder;
    private CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository, SpecificationsBuilder specificationsBuilder) {
        this.companyRepository = companyRepository;

        this.specificationsBuilder = specificationsBuilder;
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

    public Page<Company> handleGetCompanyWithPaginate(Pageable pageable, String name, String address) {

        Specification<Company> companySpecification = Specification.where(null);
        companySpecification = companySpecification.and(specificationsBuilder.whereAttributeContains("address", address));
        companySpecification = companySpecification.and(specificationsBuilder.whereAttributeContains("name", name));
        return this.companyRepository.findAll(companySpecification, pageable);
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
