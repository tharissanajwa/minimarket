package com.springboot.minimarket.controllers;

import com.springboot.minimarket.dto.requests.MemberRequest;
import com.springboot.minimarket.dto.responses.MemberResponse;
import com.springboot.minimarket.models.ApiResponse;
import com.springboot.minimarket.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait pelanggan
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // Metode untuk mengambil semua data pelanggan dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllMember(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<MemberResponse> members = memberService.getAllMember(page, limit);
        ApiResponse response = new ApiResponse(memberService.getResponseMessage(), members);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data pelanggan berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMemberById(@PathVariable("id") Long id) {
        MemberResponse members = memberService.getMemberByIdResponse(id);
        ApiResponse response = new ApiResponse(memberService.getResponseMessage(), members);
        if (members != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat pelanggan baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertMember(@RequestBody MemberRequest memberRequest) {
        MemberResponse members = memberService.insertMember(memberRequest);
        ApiResponse response = new ApiResponse(memberService.getResponseMessage(), members);
        if (members != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi pelanggan dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMember(@PathVariable("id") Long id, @RequestBody MemberRequest memberRequest) {
        MemberResponse members = memberService.updateMember(id, memberRequest);
        ApiResponse response = new ApiResponse(memberService.getResponseMessage(), members);
        if (members != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus pelanggan berdasarkan dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableMember(@PathVariable("id") Long id) {
        boolean valid = memberService.deleteMember(id);
        ApiResponse response = new ApiResponse(memberService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
