package br.com.stock.repository;

import br.com.stock.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByEmailIgnoreCaseContaining(String email);
    List<Account> findAll();

}
