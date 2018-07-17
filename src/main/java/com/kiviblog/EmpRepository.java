package com.kiviblog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangzifeng
 */
@Repository
public interface EmpRepository extends JpaRepository<Emp, String> {
}
