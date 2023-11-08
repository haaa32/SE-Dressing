package com.example.dressing.service;

import com.example.dressing.dto.UserDTO;
import com.example.dressing.entity.UserEntity;
import com.example.dressing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service //spring been 등록 (스프링이 관리해주는 객체)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입 로직
    public int join(UserDTO userDTO, String passwordCheck) {
        /* DB에 저장하기 위해: Repository의 save 메서드 호출 (조건. entity 객체를 넘겨줘야함)
         1. 아이디 중복 확인, 실패시 return -1
         2. 비밀번호 확인(추가), 실패시 return -2
         3. DTO -> ENTITY 변환
         4. Repository의 save 메서드 호출
         */

        Optional<UserEntity> byUserId = userRepository.findByUserId(userDTO.getUserId());
        if(byUserId.isPresent()) { //회원가입 실패, 아이디 중복
            return -1;
        }
        else if(!userDTO.getUserPassword().equals(passwordCheck)) { //회원가입 실패, 비밀번호 불일치
            return -2;
        }
        else { //회원가입 성공,
            UserEntity userEntity = UserEntity.toUserEntity(userDTO);
            userRepository.save(userEntity); //여기서 save는 Jpa 제공 메소드!!
            return 1;
        }
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

    //db에서 모든 유저를 리스트에 담아 DTO 리스트로 반환
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

    //성공: db에서 id를 이용해 찾은 유저의 DTO 리턴, 실패: null
    public UserDTO findByID(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) { //성공
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else { //유저가 없음 실패
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

    //db의 회원정보를 업데이트한다
    public void update(UserDTO userDTO) {
        //save는 db에 이미 id가 있는 엔터티가 넘어오면, 값이 수정되어 저장된다.
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO)); //업데이트 유저 DTO -> 유저 Entity
    }

    //db에 저장된 회원을 id를 이용해 없앤다
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public String idCheck(String userId) {
        Optional<UserEntity> byUserId = userRepository.findByUserId(userId);
        if (byUserId.isPresent()) {
            // 조회결과가 있다 -> 사용할 수 없음 (중복)
            return null;
        } else {
            // 조회결과가 없다 -> 사용할 수 있음
            return "ok";
        }
    }

    //모든 유저의 랭크를 업데이트
    public void updateRank() {

        LocalDateTime nowDateLime = LocalDateTime.now();

        List<UserEntity> userEntityList = userRepository.findAll();

        for (UserEntity userEntity : userEntityList) {
            // 사용자가 가입 후 지난 날짜 저장
            long between = ChronoUnit.DAYS.between(userEntity.getCreatedDate().toLocalDate(), nowDateLime.toLocalDate()); //현재 날짜 - 사용자 가입 날짜
            String tmpRank = "Bronze"; //update 할 rank 저장

            if(between >= 14 && between < 30) // 가입일 14일 이후면
                tmpRank = "Silver";
            else if (between >= 30 && between < 60) //가입일 30일 이후
                tmpRank = "Gold";
            else if (between >= 60) //가입일 60일 이후
                tmpRank = "Diamond";

            if(!tmpRank.equals(userEntity.getUserRank())) //rank가 변경되었다면 update
                userRepository.updateUserRank(tmpRank, userEntity.getId());
        }
        System.out.println("유저 rank 업데이트 완료");
    }
}
