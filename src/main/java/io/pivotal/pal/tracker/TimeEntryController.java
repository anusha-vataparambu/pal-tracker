package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class TimeEntryController {

    TimeEntryRepository repo;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository ter, CounterService counter, GaugeService gauge){
        repo = ter;
        this.counter=counter;
        this.gauge=gauge;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry te){ // throws URISyntaxException{
        TimeEntry newte = repo.create(te);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", repo.list().size());
        return new ResponseEntity<>(newte, HttpStatus.CREATED);
        //return ResponseEntity.created(new URI("/time-entries" + String.valueOf(te.getId()))).body(repo.create(te));
    }

    @GetMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> read(@PathVariable Long id){
        TimeEntry te = repo.find(id);
        if (te!=null) {
            counter.increment("TimeEntry.read");
            return ResponseEntity.ok(te);
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/time-entries")
    public @ResponseBody ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        List<TimeEntry> teList = repo.list();
        return ResponseEntity.ok(teList);
    }

    @PutMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry te) {
        TimeEntry newTe = repo.update(id, te);
        if (newTe != null) {
            counter.increment("TimeEntry.updated");
            return ResponseEntity.ok(newTe);
        }else
            return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> delete(@PathVariable long id){
        repo.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", repo.list().size());
        return ResponseEntity.noContent().build();
    }
}
