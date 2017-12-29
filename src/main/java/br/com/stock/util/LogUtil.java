package br.com.stock.util;

import br.com.stock.model.Account;
import br.com.stock.model.Company;
import br.com.stock.model.Deal;
import br.com.stock.service.AccountService;
import br.com.stock.service.CompanyService;
import br.com.stock.service.DealService;
import org.springframework.context.ApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LogUtil {

    public void writeLog(ApplicationContext context) {

        List<Deal> deals;
        StringBuilder emailBody = new StringBuilder();

        try {
            DealService dealService = (DealService) context.getBean("dealService");
            CompanyService companyService = (CompanyService) context.getBean("companyService");
            AccountService accountService = (AccountService) context.getBean("accountService");
            EmailUtil emailUtil = (EmailUtil) context.getBean("mailService");

            File file = new File("src/main/resources/RELATORIO-NEGOCIACOES.txt");

            if (file.exists())
                file.delete();

            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("::::::::::::::::RELATÓRIO DE NEGOCIAÇÕES::::::::::::::::");
            bw.write("\n\n");

            //Buscar todas as Deals
            deals = dealService.listAll();

            bw.write("..:TOTAL DE NEGOCIAÇÕES REALIZADAS:.. " + deals.size() + "\n\n");
            emailBody.append("Prezado Cliente, as negociações abaixo foram efetivadas através do sistema Stock Exchange: \n\n");
            for (Deal deal : deals){
                Company company = companyService.findOne(deal.getCompanyId());
                bw.write("----- NEGOCIAÇÃO: " + deal.getDealId() + " ----- \n");
                bw.write("Empresa: " +  company.getName() +"\n");
                bw.write("Operação: " + deal.getOperation() +"\n");
                bw.write("Valor da ação: " + String.format("%.2f",deal.getShareValue()) + "\n" ) ;
                bw.write("Valor Negociado: R$" +  String.format("%.2f",deal.getNegotiatedValue()) +"\n");
                bw.write("Quantidade: " + String.format("%.2f",deal.getShareAmount()) + " ações \n");
                bw.write("Data/Hora: " + deal.getDealTime() +"\n");
                bw.write("\n");
                emailBody.append("----- NEGOCIAÇÃO: " + deal.getDealId() + " ----- \n")
                         .append("Empresa: " +  company.getName() +"\n")
                         .append("Operação: " + deal.getOperation() +"\n")
                         .append("Valor da ação: " + String.format("%.2f",deal.getShareValue()) + "\n")
                         .append("Valor Negociado: R$" +  String.format("%.2f",deal.getNegotiatedValue()) +"\n")
                         .append("Quantidade: " + String.format("%.2f",deal.getShareAmount()) + " ações \n\n");
            }
            emailBody.append("\n\nAtenciosamente, \nStock Exchange System.");

            List<Account> accounts = accountService.listAll();
            for (Account account : accounts ) {
                bw.write("----- CONTA: "+ account.getAccountId()+ " | " + account.getEmail() + " ----- \n");
                bw.write("SALDO FINAL: " +  String.format("%.2f",account.getValue()) + "\n\n" ) ;
                //Envio de E-mail
                emailUtil.sendMail(account.getEmail(), emailBody.toString());
            }
            bw.close();
            System.out.println("ARQUIVO DE TRANSAÇÕES CRIADO EM: " + file.getPath());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
