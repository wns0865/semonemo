package com.semonemo.spring_server.domain.elasticsearch.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.semonemo.spring_server.domain.elasticsearch.dto.AssetSearchResponseDto;
import com.semonemo.spring_server.domain.elasticsearch.document.AssetSellDocument;
import com.semonemo.spring_server.domain.elasticsearch.service.SearchService;
import com.semonemo.spring_server.domain.user.entity.Users;
import com.semonemo.spring_server.domain.user.service.UserService;
import com.semonemo.spring_server.global.common.CommonResponse;
import com.semonemo.spring_server.global.common.CursorResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class ElasticsearchController {

	private final SearchService searchService;
	private final UserService userService;
	@GetMapping("/asset")
	public CommonResponse<?> searchAsset(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam(required = false) String keyword,
		@RequestParam(required = false) Long cursorId ,
		@RequestParam(defaultValue = "10") int size) {
		Users users = userService.findByAddress(userDetails.getUsername());
		CursorResult<AssetSearchResponseDto> result =searchService.searchAsset(users.getId(), keyword,cursorId,size);

		return CommonResponse.success(result, "에셋 키워드 검색 성공");
	}
	@GetMapping("/all")
	public AssetSellDocument findById(@RequestParam(required = false) Long id) {
		return searchService.getAsset(id);
	}

}
