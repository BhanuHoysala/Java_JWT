package com.fss.dataaccessobject;

import com.fss.domainobject.DriverDO;
import com.fss.domainvalue.OnlineStatus;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);

    /**
     * 
     * @param username
     * @return
     */
    DriverDO findByUsername(final String username);

}
