package com.projectx.repositories;

import com.projectx.entites.PostRT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRTRepository extends JpaRepository<PostRT, Long> {
}
