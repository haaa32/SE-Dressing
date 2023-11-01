package com.example.dressing.service;

import com.example.dressing.controller.FileHandler;
import com.example.dressing.dto.ClosetDTO;
import com.example.dressing.entity.ClosetEntity;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.ClosetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClosetService {

    private final ClosetRepository closetRepository;

    private final FileHandler fileHandler;

    @Autowired
    public ClosetService(ClosetRepository closetRepository) {
        this.closetRepository = closetRepository;
        this.fileHandler = new FileHandler();
    }

    public ClosetEntity addCloset(
            ClosetEntity closetEntity,
            List<MultipartFile> files,
            UserEntity userEntity
    ) throws Exception {
        // 파일을 저장하고 그 Board 에 대한 list 를 가지고 있는다
        List<ClosetEntity> list = fileHandler.parseFileInfo(closetEntity.getId(), files, userEntity);

        if (list.isEmpty()){
            // 파일이 없을 때
        }
        // 파일에 대해 DB에 저장하고 가지고 있을 것
        else{
            List<ClosetEntity> pictureBeans = new ArrayList<>();
            for (ClosetEntity closetEntity1 : list) {
                pictureBeans.add(closetRepository.save(closetEntity1));
            }
        }

        return closetRepository.save(closetEntity);
    }

    public List<ClosetEntity> findClosets() {
        return closetRepository.findAll();
    }

    public Optional<ClosetEntity> findCloset(Long id) {
        return closetRepository.findById(id);
    }

    public ClosetEntity save(ClosetDTO closetDTO) {
        // 이 메서드에서는 ClosetDTO를 ClosetEntity로 변환하고 저장하는 작업을 수행할 수 있습니다.
        ClosetEntity closetEntity = convertToEntity(closetDTO);
        return closetRepository.save(closetEntity);
    }

    // 다른 메서드 (addCloset, findClosets, findCloset)...

    private ClosetEntity convertToEntity(ClosetDTO closetDTO) {
        // ClosetDTO를 ClosetEntity로 변환하는 로직을 구현해야 합니다.
        // 예를 들어, 필드 복사 및 변환 작업을 수행할 수 있습니다.
        return null;
    }
}