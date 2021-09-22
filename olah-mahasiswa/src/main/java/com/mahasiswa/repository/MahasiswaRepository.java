/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mahasiswa.repository;

import com.mahasiswa.entity.Mahasiswa;
import org.springframework.data.repository.CrudRepository;


public interface MahasiswaRepository extends CrudRepository<Mahasiswa, Integer>{
    public Mahasiswa getMahasiswaByName(String name);
}
