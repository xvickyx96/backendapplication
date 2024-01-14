package com.qussai.security.webSecurity.token;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {


  // Custom query to find all valid tokens associated with a user.
  // It selects tokens based on the user ID and checks if they are not expired or revoked
  @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(Integer id);

  // Custom query to find a token by its token string.
  Optional<Token> findByToken(String token);
}
