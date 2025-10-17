package com.api.ecommerce.repositories;

import com.api.ecommerce.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
