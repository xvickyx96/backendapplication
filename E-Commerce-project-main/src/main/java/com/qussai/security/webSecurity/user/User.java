package com.qussai.security.webSecurity.user;


import com.qussai.security.webSecurity.token.Token;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user") // Specifying the table name to avoid conflicts with reserved words.
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstname;
  private String lastname;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  // One-to-Many relationship with Token entities associated with the user.
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

//  @OneToMany(cascade = CascadeType.ALL)
//  private List<Address> addresslist= new ArrayList<>();



  // Implementation of the UserDetails interface methods.

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Delegating the retrieval of authorities to the role.
    return role.getAuthorities();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
