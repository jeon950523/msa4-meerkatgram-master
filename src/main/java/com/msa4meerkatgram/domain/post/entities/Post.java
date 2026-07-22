package com.msa4meerkatgram.domain.post.entities;

import com.msa4meerkatgram.domain.user.entities.User;
import com.msa4meerkatgram.global.security.constant.ProviderPolicy;
import com.msa4meerkatgram.global.security.constant.RolePolicy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

@Entity // 해당 클래스가 JPA의 엔티티임을 선언
@EntityListeners(AuditingEntityListener.class) // 엔티티의 이벤트 리스너 지정
@Table(name = "posts") // 테이블명 매핑
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE id = ?") // soft delete
@SQLRestriction("deleted_at IS NULL") // 엔티티의 조회 시 항상 특정 조건을 추가하도록 지정
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 생성 전략을 설정
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        insertable = true, // INSERT할 때, user객체에 어떤 값을 넣더라도, INSERT문에 `user_id`컬럼을 포함
        updatable = false,  // UPDATE할 때, user객체에 어떤 값을 넣더라도, UPDATE문에 `user_id`컬럼을 포함하지않음
        referencedColumnName = "id",
//        foreignKey = @ForeignKey(name = "fk_posts_user_id") FK의 이름을 정하고싶을때
          foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT) //물리적 FK 생성 방지, 디폴트는 생성 
    )
    private User user;

    @Column(name = "content", nullable = false, length = 2000)
    private String content;

    @Column(name = "image", nullable = false, length = 100)
    private String image;

    @CreatedDate // 생성 시 자동으로 시간 입력
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 시 자동으로 시간 업데이트
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
