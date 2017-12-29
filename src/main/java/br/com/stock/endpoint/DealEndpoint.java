package br.com.stock.endpoint;

import br.com.stock.error.CustomErrorType;
import br.com.stock.model.Deal;
import br.com.stock.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("deals")
public class DealEndpoint {

    private DealRepository dealDao;
    @Autowired
    public DealEndpoint(DealRepository dealDao){
        this.dealDao = dealDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(dealDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{dealId}")
    public ResponseEntity<?> getDealById(@PathVariable("dealId") Long dealId) {
        Deal deal = dealDao.findOne(dealId);
        if(deal == null)
            return new ResponseEntity<>(new CustomErrorType("Negociação não encontrada"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Deal deal) {
        return new ResponseEntity<>(dealDao.save(deal), HttpStatus.CREATED);
    }

    @GetMapping(path = "/findFirstByCompanyIdOrderByMonitoringIdDesc/{companyId}")
    public ResponseEntity<?> findDealsByCompany(@PathVariable Long companyId){
        return new ResponseEntity<>(dealDao.findFirstByCompanyIdOrderByDealIdDesc(companyId),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{dealId}")
    public ResponseEntity<?> delete(@PathVariable Long dealId) {
        dealDao.delete(dealId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Deal deal) {
        dealDao.save(deal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
