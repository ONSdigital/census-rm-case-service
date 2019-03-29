package uk.gov.ons.ctp.response.casesvc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.ons.ctp.response.casesvc.domain.dto.CategoryDTO;
import uk.gov.ons.ctp.response.casesvc.domain.model.Category;

/** JPA Data Repository. */
@Repository
public interface CategoryRepository extends JpaRepository<Category, CategoryDTO.CategoryName> {}
