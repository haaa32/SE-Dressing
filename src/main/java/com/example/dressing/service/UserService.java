package com.example.dressing.service;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service //spring been 등록 (스프링이 관리해주는 객체)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입 로직
    public void join(UserDTO userDTO) {
        // DB에 저장하기 위해: Repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야함)
        // 1. DTO -> ENTITY 변환
        // 2. Repository의 save 메서드 호출
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity); //여기서 save는 Jpa 제공 메소드!!
    }

    //로그인 로직
    public UserDTO login(UserDTO userDTO) { //로그인 정보 일치 : DTO 반환, 불일치 : null 반환
        /* 1. 회원이 입력한 아이디로 DB에서 조회
        *  2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        * */

        //Optional<T>: null이 올 수 있는 값을 감싸는 Wrapper 클래스, 참조하더라도 NPE가 발생하지 않도록 도와줌
        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());

        if(byUserId.isPresent()) { //조회결과 있다(회원정보에 아이디 존재)
            UserEntity userEntity = byUserId.get(); //옵셔널은 get하면 T 객체 리턴
            if(userEntity.getUserPassword().equals(userDTO.getUserPassword())) { //비밀번호 일치 (로그인 성공)
                System.out.println("로그인 성공!");

                return UserDTO.toUserDTO(userEntity); //컨트롤러로 돌아갈 땐 DTO로 변경 후 반환
            }
            else { //비밀번호 불일치 (로그인 실패)
                System.out.println("비밀번호가 일치하지 않습니다.");
                return null;
            }
        }
        else { //조회결과 없다(회원정보에 일치하는 아이디 없음 (로그인 실패))
            System.out.println("존재하지 않는 아이디입니다.");
            return null;
        }
    }
    public List<UserDTO> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(UserEntity userEntity: userEntityList) {
            userDTOList.add(UserDTO.toUserDTO(userEntity));
            // UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            // userEntityList.add(userDTO);
        }
        return userDTOList;
    }

    public UserDTO findByID(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public UserDTO updateForm(String myUserId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(myUserId);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public void update(UserDTO userDTO) {
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }
}
