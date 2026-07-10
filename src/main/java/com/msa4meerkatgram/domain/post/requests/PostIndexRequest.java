package com.msa4meerkatgram.domain.post.requests;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "게시글 목록 페이징 조회 요청 데이터")
public record PostIndexRequest(
    @Schema(description = "조회할 페이지 번호 (1부터 시작, 기본값: 1)", example = "1", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 1, message = "1 이상 숫자만 허용합니다.")
    Integer page, 
    @Schema(description = "한 페이지당 조회할 게시글 수 (기본값: 6)", example = "6", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Min(value = 1, message = "1 이상 숫자만 허용합니다.")
    Integer limit
) {
    public PostIndexRequest(Integer page, Integer limit){
        this.page = (page !=null && page > 0) ? page : 1;
        this.limit = (limit !=null && limit > 0) ? limit : 6;
    }
}
