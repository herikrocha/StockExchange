package br.com.stock.endpoint;

import br.com.stock.error.CustomErrorType;
import br.com.stock.model.Monitoring;
import br.com.stock.repository.MonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("monitorings")
public class MonitoringEndpoint {

    private MonitoringRepository monitoringDao;

    @Autowired
    public MonitoringEndpoint(MonitoringRepository monitoringDao){
        this.monitoringDao = monitoringDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(){
        return new ResponseEntity<>(monitoringDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{monitoringId}")
    public ResponseEntity<?> getAccountById(@PathVariable("monitoringId") Long monitoringId) {
        Monitoring monitoring = monitoringDao.findOne(monitoringId);
        if(monitoring == null)
            return new ResponseEntity<>(new CustomErrorType("Monitoramento não encontrado"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(monitoring, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Monitoring monitoring) {
        return new ResponseEntity<>(monitoringDao.save(monitoring), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{monitoringId}")
    public ResponseEntity<?> delete(@PathVariable Long monitoringId) {
        monitoringDao.delete(monitoringId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Monitoring monitoring) {
        monitoringDao.save(monitoring);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
