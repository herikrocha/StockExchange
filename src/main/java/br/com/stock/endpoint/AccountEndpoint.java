package br.com.stock.endpoint;

import br.com.stock.error.CustomErrorType;
import br.com.stock.model.Account;
import br.com.stock.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountEndpoint {
    private AccountRepository accountDao;
                                                                    
    @Autowired
    public AccountEndpoint(AccountRepository accountDao){
        this.accountDao = accountDao;
    }                                                       

    @GetMapping
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(accountDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") Long accountId) {
        Account account = accountDao.findOne(accountId);
        if(account == null)
            return new ResponseEntity<>(new CustomErrorType("Conta não encontrada"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Account account) {
        return new ResponseEntity<>(accountDao.save(account), HttpStatus.CREATED);
    }

    @GetMapping(path = "/findByEmailIgnoreCaseContaining/{email}")
    public ResponseEntity<?> findAccountsByEmail(@PathVariable String email){
        return new ResponseEntity<>(accountDao.findByEmailIgnoreCaseContaining(email),HttpStatus.OK);
    }

    @DeleteMapping(path = "/{accountId}")
    public ResponseEntity<?> delete(@PathVariable Long accountId) {
        accountDao.delete(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Account account) {
        accountDao.save(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
