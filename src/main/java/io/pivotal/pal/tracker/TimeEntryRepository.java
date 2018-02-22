package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry te) ;

    TimeEntry find(long id) ;

    List<TimeEntry> list();

    TimeEntry update(long id, TimeEntry te);

    void delete(long id);
}
