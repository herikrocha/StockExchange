package br.com.stock.repository;

import br.com.stock.model.Deal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends CrudRepository<Deal, Long> {
    Deal findFirstByCompanyIdOrderByDealIdDesc(Long companyId);
    List<Deal> findAll();
}
