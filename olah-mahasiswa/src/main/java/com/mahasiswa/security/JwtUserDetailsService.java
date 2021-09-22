package com.mahasiswa.security;



import com.mahasiswa.entity.Mahasiswa;
import com.mahasiswa.repository.MahasiswaRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Mahasiswa mahasiswa = mahasiswaRepository.getMahasiswaByName(username);

        if (mahasiswa != null) {
            return new org.springframework.security.core.userdetails.User(mahasiswa.getNama(), mahasiswa.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
