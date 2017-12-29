package br.com.stock.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accountId", unique = true, nullable = false)
    private Long accountId;

    private String email;
    private double value;

    public Long getAccountId() { return accountId; }

    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() { return Objects.hash(accountId); }
}