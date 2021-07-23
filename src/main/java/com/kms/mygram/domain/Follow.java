package com.kms.mygram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"follower_user_id", "followee_user_id"}
        )
})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    private User follower;

    @ManyToOne
    private User followee;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void createTime() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void updateTime() {
        updatedAt = LocalDateTime.now();
    }
}
