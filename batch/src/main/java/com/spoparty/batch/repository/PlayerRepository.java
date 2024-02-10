package com.spoparty.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoparty.batch.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {


}
