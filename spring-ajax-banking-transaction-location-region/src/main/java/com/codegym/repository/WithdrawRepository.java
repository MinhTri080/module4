package com.codegym.repository;

import com.codegym.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawRepository extends JpaRepository<Withdraw,Long> {
}
