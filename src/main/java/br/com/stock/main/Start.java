package br.com.stock.main;

import br.com.stock.model.Account;
import br.com.stock.model.Company;
import br.com.stock.model.Deal;
import br.com.stock.model.Deal.Operation;
import br.com.stock.model.Monitoring;
import br.com.stock.service.AccountService;
import br.com.stock.service.CompanyService;
import br.com.stock.service.DealService;
import br.com.stock.service.MonitoringService;
import br.com.stock.util.LogUtil;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Start {

    private static int iterations = 0;

    public void menu(ApplicationContext context) {

        Timer timer = new Timer();
        List<Account> accountList;
        List<Company> companyList;
        List<Deal> dealList;
        List<Monitoring> monitoringList;

        System.out.println("------- SISTEMA BOLSA DE VALORES ---------");

        MonitoringService monitoringService = (MonitoringService) context.getBean("monitoringService");
        DealService dealService = (DealService) context.getBean("dealService");
        CompanyService companyService = (CompanyService) context.getBean("companyService");
        AccountService accountService = (AccountService) context.getBean("accountService");

        accountService.deleteAll();
        companyService.deleteAll();
        dealService.deleteAll();
        monitoringService.deleteAll();

        //Criação de empresa padrão
        Company company = new Company();
        company.setName("Intel");
        companyService.save(company);

        //Criação de Conta padrão
        Account account = new Account();
        account.setEmail("contato@stockexchange.com");
        account.setValue(10000.00);
        accountService.save(account);

        //Execuções
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Iteração de Preços: " + (++iterations));
                new Start().stockIterations(company,account,dealService,monitoringService);

                if(iterations >= 100){
                    //Se a ultima iteração foi de compra, regerar preços até que seja feita a última venda para calcular o saldo final das negociações.
                    Deal lastDeal = new Deal();
                    lastDeal = dealService.findFirstByCompanyIdOrderByDealIdDesc(company.getCompanyId());
                    if(lastDeal.getOperation() == Operation.VENDA){
                        timer.cancel();
                        //Gera arquivo de LOG
                        LogUtil logUtil = new LogUtil();
                        logUtil.writeLog(context);
                    }
                }
            }
        },0, 5000);

    }

    public void stockIterations(Company company, Account account, DealService dealService ,MonitoringService monitoringService){

        //Gera preços de ações automaticamente
        Monitoring monitoring = monitoringService.sharePricesManagement(company.getCompanyId());

        //Se ainda nao existe nenhuma negociação para esta empresa, comprar ações quando o preço de compra for menor que o de venda
        Deal deal = dealService.findFirstByCompanyIdOrderByDealIdDesc(company.getCompanyId());
        if (deal == null) {
            if (monitoring.getSalePrice() >= monitoring.getBuyPrice()) {
                //Compra de ações
                dealService.save(monitoring, account, Operation.COMPRA);
            }
        } else {
            // Verifica se o novo valor de venda da ação desta empresa é maior do que o valor de compra da ultima negociação(deal)
            if (monitoring.getSalePrice() >= deal.getShareValue()) {
                dealService.save(monitoring, account, Operation.VENDA);
            } else if (monitoring.getBuyPrice() <= deal.getShareValue()) {
                //Verifica se o novo valor de compra for menor do que o preço da venda anterior, compra tudo
                dealService.save(monitoring, account, Operation.COMPRA);
            }
        }
    }
}
