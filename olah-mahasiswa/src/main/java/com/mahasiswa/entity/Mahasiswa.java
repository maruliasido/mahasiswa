package com.mahasiswa.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "t_mahasiswa")
public class Mahasiswa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="nama",nullable = false)
    private String nama;
    
    @Column(name = "passoword")
    private String password;
    
    @OneToOne
    @JoinColumn(name = "fakultas_id")
    private Fakultas fakultas;
    
    @OneToOne
    @JoinColumn(name = "jurusan_id")
    private Jurusan jursan;
    
    @OneToMany(mappedBy = "mahasiswa",cascade = CascadeType.ALL)
    private List<Matkul> matkul;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public Fakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
    }

    public Jurusan getJursan() {
        return jursan;
    }

    public void setJursan(Jurusan jursan) {
        this.jursan = jursan;
    }

    public List<Matkul> getMatkul() {
        return matkul;
    }

    public void setMatkul(List<Matkul> matkul) {
        this.matkul = matkul;
    }


    
    
}