package com.fss.config.secure;

import com.fss.dataaccessobject.DriverRepository;
import com.fss.domainobject.DriverDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads the user data from the DB EX: password ( and Roles )
 */
@Service
public class AuthenticatingUserDetails implements UserDetailsService
{

    @Autowired
    private DriverRepository driverRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        final DriverDO user = driverRepository.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
            .withUsername(username)
            .password(user.getPassword())
            .authorities("USER") // TODO - need to assign proper roles
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build();
    }

}
