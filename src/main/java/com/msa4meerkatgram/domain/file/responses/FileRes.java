package com.msa4meerkatgram.domain.file.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "업로드 완료된 파일 경로 응답 데이터")
@Builder
public record FileRes(
    @Schema(description = "서버에 업로드되어 접근 가능한 파일 URI", example = "/uploads/profiles/profile_meerkat.png")
    String fileUri
) {
}
