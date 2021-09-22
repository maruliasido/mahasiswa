package com.mahasiswa.controller;

import com.mahasiswa.entity.Fakultas;
import com.mahasiswa.entity.Jurusan;
import com.mahasiswa.entity.Mahasiswa;
import com.mahasiswa.entity.Matkul;
import com.mahasiswa.model.MahasiswaModel;
import com.mahasiswa.model.MatkulBody;
import com.mahasiswa.model.MatkulModel;
import com.mahasiswa.repository.FakultasRepository;
import com.mahasiswa.repository.JurusanRepository;
import com.mahasiswa.repository.MahasiswaRepository;
import com.mahasiswa.repository.MatkulRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MahasiswaController {

    @Autowired
    MahasiswaRepository mahasiswaRepository;

    @Autowired
    FakultasRepository fakultasRepository;

    @Autowired
    JurusanRepository jurusanRepository;

    @Autowired
    MatkulRepository matkulRepository;
    
    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @PostMapping("/register-mahasiswa")
    public ResponseEntity<String> registerMahasiswa(@RequestBody MahasiswaModel mahasiswaModel) {

        Fakultas fakultas = fakultasRepository.findById(mahasiswaModel.getId()).get();
        Jurusan jurusan = jurusanRepository.findById(mahasiswaModel.getIdJurusan()).get();
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setJursan(jurusan);
        mahasiswa.setFakultas(fakultas);
        mahasiswa.setNama(mahasiswaModel.getName());
        mahasiswa.setPassword(bcrypt.encode(mahasiswaModel.getPassword()));

        mahasiswaRepository.save(mahasiswa);

        return ResponseEntity.ok("Mahasiswa has been addded");
    }

    @PostMapping("/add-matkul")
    public ResponseEntity<String> addMatkul(@RequestBody MatkulModel matkulModel, Principal principal) {

        Mahasiswa loggedIn = mahasiswaRepository.getMahasiswaByName(principal.getName());
        Matkul matkul = matkulRepository.findById(matkulModel.getMatkulId()).get();
        Mahasiswa mahasiswa = mahasiswaRepository.findById(matkulModel.getMahasiswaId()).get();
        
        if(loggedIn.equals(mahasiswa)){
            matkul.setMahasiswa(mahasiswa);
            matkulRepository.save(matkul);
            return ResponseEntity.ok("Matkul has been added");
        }
        
        return ResponseEntity.badRequest().body("You cant add matkul");
    }
    
    @PutMapping("/update-matkul")
    public ResponseEntity<String> updateMatkul(@RequestBody MatkulModel matkulModel, Principal principal) {

        Mahasiswa loggedIn = mahasiswaRepository.getMahasiswaByName(principal.getName());
        Matkul matkul = matkulRepository.findById(matkulModel.getMatkulId()).get();
        Mahasiswa mahasiswa = mahasiswaRepository.findById(matkulModel.getMahasiswaId()).get();
        
        if(loggedIn.equals(mahasiswa)){
            matkul.setMahasiswa(mahasiswa);
            matkulRepository.save(matkul);
            return ResponseEntity.ok("Matkul has been edited");
        }
        
        return ResponseEntity.badRequest().body("You cant edit matkul");
    }

    @GetMapping("/mahasiswa")
    public ResponseEntity<List<Mahasiswa>> showAllMahasiswa() {
        Iterable<Mahasiswa> mahasiswa = mahasiswaRepository.findAll();

        List<Mahasiswa> listMahasiswa = StreamSupport.stream(mahasiswa.spliterator(), false).collect(Collectors.toList());

        return ResponseEntity.ok(listMahasiswa);
    }
    
    @GetMapping("/show-matkul-atendee")
    public ResponseEntity<List<Mahasiswa>> matkulAtendee(@RequestBody MatkulBody matkulBody){
        Iterable<Matkul> matkuls = matkulRepository.findAll();
        Iterable<Mahasiswa> mahasiswa = mahasiswaRepository.findAll();
        
        List<Mahasiswa> mahasiswas = new ArrayList<>();
        for( Mahasiswa a: mahasiswa){
            mahasiswas.add(a);
        }
        
        List<Mahasiswa> show = new ArrayList<>();
        
        for(Mahasiswa b: mahasiswas){
            if(b.getJursan().getId().equals(matkulBody.getIdMatkul())){
                show.add(b);
                
                return ResponseEntity.ok(show);
            }
        }
        
        
        return null;
        
    }

    @PutMapping("/mahasiswa")
    public ResponseEntity<String> updateMahasiwa(@RequestBody MahasiswaModel mahasiswaModel, Principal principal) {

        Mahasiswa loggedIn = mahasiswaRepository.getMahasiswaByName(principal.getName());

        Fakultas fakultas = fakultasRepository.findById(mahasiswaModel.getId()).get();
        Jurusan jurusan = jurusanRepository.findById(mahasiswaModel.getIdJurusan()).get();
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setJursan(jurusan);
        mahasiswa.setFakultas(fakultas);
        mahasiswa.setNama(mahasiswaModel.getName());

        if (mahasiswa.equals(loggedIn)) {

            mahasiswaRepository.save(mahasiswa);
            return ResponseEntity.ok("Mahasiswa has been updated");
        }

        return ResponseEntity.badRequest().body("You can't update another mahasiswa");

    }

    @DeleteMapping("/mahasiswa/{id}")
    public ResponseEntity<String> deleteMahasiswa(@PathVariable(name = "id") Integer id) {
        Mahasiswa mahasiswa = mahasiswaRepository.findById(id).get();
        mahasiswaRepository.delete(mahasiswa);

        return ResponseEntity.ok("Mahasiswa has been deleted");
    }

}
