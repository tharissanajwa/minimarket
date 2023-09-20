package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MemberSeeder {
    @Autowired
    private MemberRepository memberRepository;

    public void seed() {
        // Daftar pelanggan yang akan disimpan dalam database
        List<Member> members = new ArrayList<>(Arrays.asList(
                new Member("Tharissa Najwa", "Jl. Kemala 2", "082187343453"),
                new Member("Hanifah Hazima", "Jl. Graha 12", "08973483456"),
                new Member("Rahma Ramadhina", "Jl. BUkit Tinggi 9", "089573847583"),
                new Member("Desy Fitri Rafifah", "Jl. Hasanuddin 15", "0821736485674"),
                new Member("Muhammad Kautsar", "Jl. Moch Hatta 27", "08967838495"),
                new Member("Agitya Atlas", "Jl. Aceh 17", "0897366478"),
                new Member("Muhammad Khairan", "Jl. Hayam Wuruk 56", "08784839485"),
                new Member("Muhammad Abidzar", "Jl. Bali 88", "0896835894"),
                new Member("Athifa Rasya", "Jl. Jakarta 6", "08318347383"),
                new Member("Aaron Caden Leo", "Jl. Gatot Subroto 6", "089674859473")
        ));

        // Cek apakah database sudah berisi data pelanggan atau tidak
        if (memberRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data pelanggan ke dalam database
            memberRepository.saveAll(members);
        }
    }
}
