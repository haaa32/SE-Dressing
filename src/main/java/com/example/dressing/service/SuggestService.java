package com.example.dressing.service;

import com.example.dressing.dto.SuggestDTO;
import com.example.dressing.entity.SuggestEntity;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.SuggestRepository;
import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestService {
    private final SuggestRepository suggestRepository;
    private final UserRepository userRepository;

    // 레포에 작성한 건의 등록하기
    public void save(SuggestDTO suggestDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(suggestDTO.getUid());
        SuggestEntity suggestEntity = SuggestEntity.toSuggestEntity(suggestDTO, userEntity.get());
        suggestRepository.save(suggestEntity);
    }

    // 레포에서 모든 건의함 리스트로 받아오기
    public List<SuggestDTO> findAll() {
        List<SuggestEntity> suggestEntityList = suggestRepository.findAll();
        List<SuggestDTO> suggestDTOList = new ArrayList<>();
        for(SuggestEntity suggestEntity : suggestEntityList) {
            suggestDTOList.add(SuggestDTO.toSuggestDTO(suggestEntity));
        }
        return suggestDTOList;
    }
}