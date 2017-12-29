package br.com.stock.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyId;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId", referencedColumnName = "companyId")
    private List<Monitoring> monitorings;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId", referencedColumnName = "companyId")
    private List<Deal> deals;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Monitoring> getMonitorings() { return monitorings; }

    public void setMonitorings(List<Monitoring> monitorings) { this.monitorings = monitorings; }

    public Long getCompanyId() { return companyId; }

    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public List<Deal> getDeals() { return deals; }

    public void setDeals(List<Deal> deals) { this.deals = deals; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(companyId, company.companyId);
    }

    @Override
    public int hashCode() { return Objects.hash(companyId); }
}