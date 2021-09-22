/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mahasiswa.repository;

import com.mahasiswa.entity.Mahasiswa;
import com.mahasiswa.entity.Matkul;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface SearchMahasiswaRepository extends PagingAndSortingRepository<Mahasiswa, Integer>{
    
    public List<Mahasiswa> findByMatkulCointains(List<Matkul> matkul, Pageable pageable);
}
