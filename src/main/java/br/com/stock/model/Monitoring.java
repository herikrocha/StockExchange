package br.com.stock.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Monitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "monitoringId", unique = true, nullable = false)
    private Long monitoringId;

    private double salePrice;
    private double buyPrice;

    private Long companyId;

    public Long getCompanyId() { return companyId; }

    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Long getMonitoringId() { return monitoringId; }

    public void setMonitoringId(Long monitoringId) { this.monitoringId = monitoringId; }

    public double getSalePrice() { return salePrice; }

    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }

    public double getBuyPrice() { return buyPrice; }

    public void setBuyPrice(double buyPrice) { this.buyPrice = buyPrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Monitoring that = (Monitoring) o;
        return Objects.equals(monitoringId, that.monitoringId);
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), monitoringId); }
}
