package br.com.stock.endpoint;

import br.com.stock.error.CustomErrorType;
import br.com.stock.model.Company;
import br.com.stock.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("companys")
public class CompanyEndpoint {

    private CompanyRepository companyDao;

    @Autowired
    public CompanyEndpoint(CompanyRepository accountDao){
        this.companyDao = companyDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(companyDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable("companyId") Long companyId) {
        Company company = companyDao.findOne(companyId);
        if(company == null)
            return new ResponseEntity<>(new CustomErrorType("Empresa não encontrada"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Company company) {
        return new ResponseEntity<>(companyDao.save(company), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{companyId}")
    public ResponseEntity<?> delete(@PathVariable Long companyId) {
        companyDao.delete(companyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Company company) {
        companyDao.save(company);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
