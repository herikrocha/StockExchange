package br.com.stock.service;

import br.com.stock.model.Account;
import br.com.stock.model.Deal;
import br.com.stock.model.Deal.Operation;
import br.com.stock.model.Monitoring;
import br.com.stock.repository.AccountRepository;
import br.com.stock.repository.DealRepository;
import br.com.stock.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DealService {

    private DealRepository dealDao;
    private AccountRepository accountDao;

    @Autowired
    public DealService(DealRepository dealDao, AccountRepository accountDao){
        this.dealDao = dealDao;
        this.accountDao = accountDao;
    }

    public Deal findFirstByCompanyIdOrderByDealIdDesc(Long companyId){
        return dealDao.findFirstByCompanyIdOrderByDealIdDesc(companyId);
    }

    public void save(Monitoring monitoring, Account account, Operation operation){

        double amount = 0;
        Deal lastDeal = this.findFirstByCompanyIdOrderByDealIdDesc(monitoring.getCompanyId());

        Deal deal = new Deal();
        if(operation == Operation.COMPRA) {
            if(account.getValue()< monitoring.getBuyPrice()){
                System.out.println("Saldo insuficiente para efetivar a compra das ações");
                return;
            }
            deal.setShareValue(monitoring.getBuyPrice());
            amount = account.getValue() / monitoring.getBuyPrice();
            deal.setNegotiatedValue(monitoring.getBuyPrice() * amount);
            account.setValue(account.getValue() - (monitoring.getBuyPrice() * amount)    );
        } else {
            if(lastDeal.getOperation() == Operation.VENDA)
                return;
            deal.setShareValue(monitoring.getSalePrice());
            amount = lastDeal.getShareAmount();
            deal.setNegotiatedValue(monitoring.getSalePrice()*amount);
            account.setValue(account.getValue() + (monitoring.getSalePrice()*amount));
        }
        deal.setCompanyId(monitoring.getCompanyId());
        deal.setOperation(operation);
        deal.setShareAmount(amount);
        deal.setDealTime(new DateUtil().formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        dealDao.save(deal);
        accountDao.save(account);
    }

    public List<Deal> listAll(){
        return dealDao.findAll();
    }

    public void deleteAll(){
        dealDao.deleteAll();
    }


}
