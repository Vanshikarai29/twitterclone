package com.example.twitterclone.security;

import com.example.twitterclone.service.UserDetailsServiceImpl;
import com.example.twitterclone.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter triggered for: " + request.getRequestURI());//checker


        // ✅ Skip JWT check for authentication endpoints
        if (request.getServletPath().startsWith("/auth") ) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwtToken);

            System.out.println("JWT: " + jwtToken);
            System.out.println("Username: " + username);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        System.out.println("Authentication in context: " + SecurityContextHolder.getContext().getAuthentication());// for checking**
        filterChain.doFilter(request, response);
    }
}


//package com.example.twitterclone.security;
//
//
//import com.example.twitterclone.service.UserDetailsServiceImpl;
//import com.example.twitterclone.utils.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter{
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
////        final String authHeader = request.getHeader("Authorization");
////        String username = null;
////        String jwtToken = null;
//@Override
//protected void doFilterInternal(HttpServletRequest request,
//                                HttpServletResponse response,
//                                FilterChain filterChain)
//        throws ServletException, IOException {
//    // ✅ Skip JWT check for authentication endpoints
//    if (request.getServletPath().contains("/api/auth")) {
//        filterChain.doFilter(request, response);
//        return;
//    }
//
//    final String authHeader = request.getHeader("Authorization");
//    String username = null;
//    String jwtToken = null;
//
//
//    if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwtToken = authHeader.substring(7);
//            username=jwtUtil.extractUsername(jwtToken);
//        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
//
//            if(jwtUtil.validateToken(jwtToken,userDetails.getUsername()))
//            {
//                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//
//        }
//        filterChain.doFilter(request,response);
//    }
//}
