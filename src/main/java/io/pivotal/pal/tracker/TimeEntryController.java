package io.pivotal.pal.tracker;

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

    public TimeEntryController(TimeEntryRepository ter){
        repo = ter;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry te){ // throws URISyntaxException{
        TimeEntry newte = repo.create(te);
        return new ResponseEntity<>(newte, HttpStatus.CREATED);
        //return ResponseEntity.created(new URI("/time-entries" + String.valueOf(te.getId()))).body(repo.create(te));
    }

    @GetMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> read(@PathVariable Long id){
        TimeEntry te = repo.find(id);
        if (te!=null)
            return ResponseEntity.ok(te);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/time-entries")
    public @ResponseBody ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> teList = repo.list();
        return ResponseEntity.ok(teList);
    }

    @PutMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry te) {
        if (repo.update(id, te) != null)
            return ResponseEntity.ok(te);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> delete(@PathVariable long id){
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }
}
