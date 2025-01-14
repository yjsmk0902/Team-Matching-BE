package com.teammatching.demo.web.controller;

import com.teammatching.demo.domain.dto.*;
import com.teammatching.demo.result.ResponseMessage;
import com.teammatching.demo.result.ResponseResult;
import com.teammatching.demo.web.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "마이페이지 API", description = "마이페이지 관련 API")
@RequiredArgsConstructor
@RequestMapping("/my-page/{userId}")
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(
            summary = "마이페이지 조회",
            description = "유저의 정보를 제공합니다."
    )
    @GetMapping
    public ResponseResult<UserAccountDto> getMyPage(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        return ResponseResult.<UserAccountDto>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyPage(userId, principal.userId()))
                .build();
    }

    @Operation(
            summary = "마이페이지 수정",
            description = "요청받은 유저의 정보를 수정합니다."
    )
    @PatchMapping
    public ResponseResult<Objects> updateAccount(
            @PathVariable("userId") String userId,
            @RequestBody UserAccountDto.UpdateRequest request,
            @AuthenticationPrincipal TeamMatchPrincipal principal
    ) {
        myPageService.updateAccount(userId, request.toDto(), principal.userId());
        return ResponseResult.<Objects>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .build();
    }

    @Operation(
            summary = "마이페이지 게시글 조회",
            description = "유저가 작성한 게시글 리스트를 제공합니다."
    )
    @GetMapping("/posts")
    public ResponseResult<Page<PostDto.SimpleResponse>> getMyPosts(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal TeamMatchPrincipal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<PostDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyPosts(userId, principal.userId(), pageable)
                        .map(PostDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "마이페이지 댓글 조회",
            description = "유저가 작성한 댓글 리스트를 제공합니다."
    )
    @GetMapping("/comments")
    public ResponseResult<Page<CommentDto.SimpleResponse>> getMyComments(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal TeamMatchPrincipal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<CommentDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyComments(userId, principal.userId(), pageable)
                        .map(CommentDto.SimpleResponse::from))
                .build();
    }

    @Operation(
            summary = "마이페이지 팀 조회",
            description = "유저가 작성한 팀 리스트를 제공합니다."
    )
    @GetMapping("/teams")
    public ResponseResult<Page<TeamDto.SimpleResponse>> getMyTeams(
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal TeamMatchPrincipal principal,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseResult.<Page<TeamDto.SimpleResponse>>builder()
                .statusCode(HttpStatus.OK)
                .resultMessage(ResponseMessage.SUCCESS)
                .resultData(myPageService.getMyTeams(userId, principal.userId(), pageable)
                        .map(TeamDto.SimpleResponse::from))
                .build();
    }
}
