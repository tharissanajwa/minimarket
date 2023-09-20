package com.springboot.minimarket.seeders;

import com.springboot.minimarket.models.Product;
import com.springboot.minimarket.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductSeeder {
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void seed() {
        // Daftar barang yang akan disimpan dalam database
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("PRD-1", "Ultra Milk Chocolate", 5000, "Susu segar merk Ultra Milk kemasan 200mL", 100),
                new Product("PRD-2", "Ultra Milk Vanilla", 4500, "Susu segar merk Ultra Milk kemasan 200mL", 100),
                new Product("PRD-3", "Ultra Milk Strawberry", 5500, "Susu segar merk Ultra Milk kemasan 200mL", 100),
                new Product("PRD-4", "Cimory Milk Cashew", 7000, "Susu segar merk Cimory kemasan 210mL", 100),
                new Product("PRD-5", "Aqua Mineral Water", 5000, "Minuman mineral merk Aqua kemasan 300mL", 100),
                new Product("PRD-6", "Lays Seaweed", 15000, "Keripik kentang merk Lays kemasan 500g", 100),
                new Product("PRD-7", "Kanzler Chicken Nugget", 55000, "Makanan beku merk Kenzler kemasan 250g", 100),
                new Product("PRD-8", "Oreo Vanilla", 9000, "Biskuit vanilla merk Oreo kemasan 100g", 100),
                new Product("PRD-9", "Milo Bites", 6500, "Permen merk Milo kemasan 20g", 100),
                new Product("PRD-10", "Indomie Goreng", 3500, "Mie instan goreng merk Indomie kemasan 100g", 100),
                new Product("PRD-11", "Indomie Ayam Bawang", 3200, "Mie instan kuah merk Indomie kemasan 100g", 100),
                new Product("PRD-12", "Sedaap Soto Ayam", 3000, "Mie instan merk Sedaap kemasan 100g", 100),
                new Product("PRD-13", "Glico Mochi Ice Cream", 6500, "Es krim merk Glico kemasan 70g", 100),
                new Product("PRD-14", "Yupi Hearts", 11000, "Permen merk Yupi kemasan 200g", 100),
                new Product("PRD-15", "Gaga Hot 100", 2500, "Mie instan merk Gaga kemasan 100g", 100)
        ));

        // Cek apakah database sudah berisi data barang atau tidak
        if (productRepository.findAll().isEmpty()) {
            // Jika tidak ada data, maka simpan data barang ke dalam database
            productRepository.saveAll(products);
        }
    }
}
