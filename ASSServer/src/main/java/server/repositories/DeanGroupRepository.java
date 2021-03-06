package server.repositories;

import dtos.DeanGroupDto;
import entities.DeanGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeanGroupRepository extends JpaRepository<DeanGroup, Integer> {
    DeanGroupDto findDeanGroupById(Integer id);

    void deleteDeanGroupById(Integer id);

    @Query("SELECT NEW dtos.DeanGroupDto(d) FROM DeanGroup d")
    List<DeanGroupDto> findAllDeanGroupAsDto();
}
