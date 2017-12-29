package br.com.stock.service;

import br.com.stock.model.Company;
import br.com.stock.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyDao;

    public CompanyService(CompanyRepository companyDao){
        this.companyDao = companyDao;
    }

    public Company findOne(Long companyId){ return companyDao.findOne(companyId); }

    public void save(Company company){
        companyDao.save(company);
    }

    public void deleteAll(){
        companyDao.deleteAll();
    }


}
