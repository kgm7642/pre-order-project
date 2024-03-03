package com.kgm.preorder.controller;

import com.kgm.preorder.Dto.DefaultRes;
import com.kgm.preorder.Dto.NewsfeedDTO;
import com.kgm.preorder.Dto.ResponseDto.MyPostActionDTO;
import com.kgm.preorder.Dto.ResponseMessage;
import com.kgm.preorder.Dto.StatusCode;
import com.kgm.preorder.config.jwt.JwtUtil;
import com.kgm.preorder.repository.BlacklistTokenRepository;
import com.kgm.preorder.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/newsfeed")
@RequiredArgsConstructor
@Slf4j
public class NewsfeedController {

    private final NewsfeedService newsfeedService;
    private final BlacklistTokenRepository blacklistTokenRepository;
    private final JwtUtil jwtUtil;

    // 내가 팔로우한 유저들의 뉴스피드
    @GetMapping("/getNewsfeedTo")
    public ResponseEntity getNewsfeedTo(@RequestHeader("Authorization") String token) {
        log.info("뉴스피드 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        List<NewsfeedDTO> newsfeed = newsfeedService.getNewsfeedTo(email);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NEWSFEED_SUCCESS, newsfeed), HttpStatus.OK);
    }

    // 나를 팔로우한 유저들의 뉴스피드
    @GetMapping("/getNewsfeedFrom")
    public ResponseEntity<List<NewsfeedDTO>> getNewsfeedFrom(@RequestHeader("Authorization") String token) {
        log.info("뉴스피드 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        List<NewsfeedDTO> newsfeed = newsfeedService.getNewsfeedFrom(email);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.NEWSFEED_SUCCESS, newsfeed), HttpStatus.OK);
    }

    // 나의 포스트 활동
    @GetMapping("/MyPostAction")
    public ResponseEntity<List<MyPostActionDTO>> getMyPostAction(@RequestHeader("Authorization") String token) {
        log.info("포스트액션 컨트롤러 접근");
        if(blacklistTokenRepository.existsByToken(token)) {
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.TOEKN_EXPIRE), HttpStatus.OK);
        }
        String email = jwtUtil.getEmailFromToken(token);
        List<MyPostActionDTO> myPostActions = newsfeedService.getMyPostAction(email);
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.MY_POST_SUCCESS, myPostActions), HttpStatus.OK);
    }
}
