package com.mahasiswa.security;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException{
        
        String requestValueTokenHeader = request.getHeader("Authorization");
        String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestValueTokenHeader != null) {
			jwtToken = requestValueTokenHeader;
                    try {
                            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                    } catch (ExpiredJwtException e) {
                            e.printStackTrace();
                    }

		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

                    // if token is valid configure Spring Security to manually set
                    // authentication
                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                            userDetails, null, userDetails.getAuthorities());
                            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            // After setting the Authentication in the context, we specify
                            // that the current user is authenticated. So it passes the
                            // Spring Security Configurations successfully.
                            SecurityContextHolder.getContext().setAuthentication(token);
                    }
		}
		chain.doFilter(request, response);
        
    }

}