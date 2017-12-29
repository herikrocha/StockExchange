package br.com.stock.service;

import br.com.stock.model.Account;
import br.com.stock.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountDao;

    public AccountService(AccountRepository monitoringDao){
        this.accountDao = monitoringDao;
    }

    public List<Account> listAll(){
        return accountDao.findAll();
    }

    public void save(Account account){
        accountDao.save(account);
    }

    public void delete(Account account){
        accountDao.delete(account);
    }

    public void deleteAll(){
        accountDao.deleteAll();
    }

}