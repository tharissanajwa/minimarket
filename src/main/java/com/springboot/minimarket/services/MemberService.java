package com.springboot.minimarket.services;

import com.springboot.minimarket.dto.requests.MemberRequest;
import com.springboot.minimarket.dto.responses.MemberResponse;
import com.springboot.minimarket.models.Member;
import com.springboot.minimarket.repositories.MemberRepository;
import com.springboot.minimarket.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // Pesan status untuk memberi informasi kepada pengguna
    private String responseMessage;

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar pelanggan yang belum terhapus melalui repository
    public Page<MemberResponse> getAllMember(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Member> result = memberRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<MemberResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Member member : result.getContent()) {
                MemberResponse memberResponse = new MemberResponse(member);
                responses.add(memberResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data pelanggan berdasarkan id melalui repository
    public Member getMemberById(Long id) {
        Optional<Member> result = memberRepository.findByIdAndDeletedAtIsNull(id);
        if (!result.isPresent()) {
            responseMessage = Utility.message("member_not_found");
            return null;
        }
        responseMessage = Utility.message("data_displayed");
        return result.get();
    }

    // Metode untuk mendapatkan data pelanggan melalui response berdasarkan id melalui repository
    public MemberResponse getMemberByIdResponse(Long id) {
        MemberResponse response = null;
        Member member = getMemberById(id);
        if (member != null) {
            response = new MemberResponse(member);
        }
        return response;
    }

    // Metode untuk menambahkan pelanggan ke dalam data melalui repository
    public MemberResponse insertMember(MemberRequest memberRequest) {
        MemberResponse response = null;
        Member result = new Member();
        String name = Utility.inputTrim(memberRequest.getName());
        String address = Utility.inputTrim(memberRequest.getAddress());
        String phoneNumber = memberRequest.getPhoneNumber();

        if (!inputValidation(name, phoneNumber).isEmpty()) {
            responseMessage = inputValidation(name, phoneNumber);
        } else {
            result.setName(name);
            result.setAddress(address);
            result.setPhoneNumber(Utility.phoneTrim(phoneNumber));
            memberRepository.save(result);
            response = new MemberResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi pelanggan melalui repository
    public MemberResponse updateMember(Long id, MemberRequest memberRequest) {
        MemberResponse response = null;
        Member result = getMemberById(id);
        String name = Utility.inputTrim(memberRequest.getName());
        String address = Utility.inputTrim(memberRequest.getAddress());
        String phoneNumber = memberRequest.getPhoneNumber();

        if (!inputValidation(name, phoneNumber).isEmpty()) {
            responseMessage = inputValidation(name, phoneNumber);
        } else if (result != null){
            result.setName(name);
            result.setAddress(address);
            result.setPhoneNumber(Utility.phoneTrim(phoneNumber));
            memberRepository.save(result);
            response = new MemberResponse(result);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk menghapus data pelanggan secara soft delete melalui repository
    public boolean deleteMember(Long id) {
        boolean result = false;
        Member member = getMemberById(id);
        if (member != null) {
            member.setDeletedAt(new Date());
            memberRepository.save(member);
            result = true;
            responseMessage = Utility.message("data_deleted");
        }
        return result;
    }

    // Metode untuk memvalidasi inputan pengguna
    private String inputValidation(String name, String phoneNumber) {
        String result = "";
        if (Utility.inputCheck(name) == 1) {
            result = "Sorry, member name cannot be blank.";
        }
        if (Utility.inputCheck(name) == 2) {
            result = "Sorry, member name can only filled by letters";
        }
        if (Utility.inputCheck(phoneNumber) == 1) {
            result = "Sorry, member phone cannot be blank.";
        } else if (!Utility.phoneTrim(phoneNumber).matches("^\\d+$") || !(Utility.phoneTrim(phoneNumber).length() >= 10 && Utility.phoneTrim(phoneNumber).length() <= 13)) {
            result = "Invalid phone number. Please enter a valid phone number.";
        }
        return result;
    }
}
