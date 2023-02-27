package org.sits.pr.api.repository;

import org.sits.pr.api.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
	
	@Query(value = "SELECT * FROM T101_USER T101 WHERE T101.C101_USER_NAME=?1 AND T101.C101_is_Enabled =1", nativeQuery = true)
	UserInfo findByUserName(String userName);
	
}
