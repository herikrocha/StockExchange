package br.com.stock.repository;

import br.com.stock.model.Monitoring;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringRepository extends CrudRepository<Monitoring, Long> {
    Monitoring findFirstByCompanyIdOrderByMonitoringIdDesc(Long companyId);
    List<Monitoring> findAll();
}
