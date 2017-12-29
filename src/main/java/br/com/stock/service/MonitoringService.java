package br.com.stock.service;

import br.com.stock.model.Account;
import br.com.stock.model.Company;
import br.com.stock.model.Monitoring;
import br.com.stock.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private MonitoringRepository monitoringDao;

    public MonitoringService(MonitoringRepository monitoringDao){
        this.monitoringDao = monitoringDao;
    }

    public Monitoring sharePricesManagement(Long companyId){
        Monitoring monitoring = new Monitoring();
        monitoring.setBuyPrice(10+(1*Math.random()));
        monitoring.setSalePrice(10+(1*Math.random()));
        monitoring.setCompanyId(companyId);
        monitoringDao.save(monitoring);
        return monitoring;
    }

    public Monitoring findFirstByCompanyIdOrderByMonitoringIdDesc(Long companyId){
        return monitoringDao.findFirstByCompanyIdOrderByMonitoringIdDesc(companyId);
    }

    public void deleteAll(){
        monitoringDao.deleteAll();
    }

}
