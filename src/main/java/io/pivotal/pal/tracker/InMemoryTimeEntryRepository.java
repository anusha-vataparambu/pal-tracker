package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry>  teStore = new HashMap<>();
    Long id = 0L;

    public TimeEntry create(TimeEntry te){
        id = id + 1L;
        te.setId(id);
        teStore.put(id, te);
        return te;
    }

    public TimeEntry find(long id) {
        return teStore.get(id);
    }

    public void delete(long id) {
        teStore.remove(id);
    }

    public List<TimeEntry> list() {
        List<TimeEntry> teList = new ArrayList<TimeEntry>();
        teList.addAll(teStore.values());
        return teList;
    }

    public TimeEntry update(long id, TimeEntry te) {
        te.setId(id);
        TimeEntry oldTe = teStore.replace(id, te);
        return teStore.get(id);

    }
}
