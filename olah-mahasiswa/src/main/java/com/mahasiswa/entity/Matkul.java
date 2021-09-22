package com.mahasiswa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "t_matkul")
public class Matkul {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matukul;
    
    @Column(name = "nama_matkul",nullable = false)
    private String namaMatkul;
    
    @ManyToOne
    @JoinColumn(name = "mahasiwa_id")
    private Mahasiswa mahasiswa;

    public Integer getMatukul() {
        return matukul;
    }

    public void setMatukul(Integer matukul) {
        this.matukul = matukul;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }


    
    

}