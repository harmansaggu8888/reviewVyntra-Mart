package DAO;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import Model.User;
import DAO.User_DAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Repository
public class jwt_DAO_Imp implements jwt_DAO {

	private final static String Key = "axWDVrgnYJil";
	private final static String ISSUER = "ADMIN_SHOPPING";
	private final static String SUBJECT = "USER_SHOPPING";
	private final static String SES_EMAIL = "SESSION_EMAIL";
	private final static String SES_PASS = "SESSION_PASS";
	private final static String SES_TYPE = "SESSION_TYPE";

	@Autowired
	private User_DAO userRepo;

	@Override
	public String createToken(String session_email, String session_pass, String session_type) {
		System.out.println(session_email);
		Map<String, Object> map = new HashMap<>();
		map.put(SES_EMAIL, session_email);
		map.put(SES_PASS, session_pass);
		map.put(SES_TYPE, session_type);
		System.out.println("1");
		SignatureAlgorithm signAlg = SignatureAlgorithm.HS256;
		String br = Jwts.builder().setIssuer(ISSUER).setClaims(map).setSubject(SUBJECT).signWith(signAlg, Key)
				.compact();

		return br;
	}

	@Override
	public User checkToken(String token) {
		Claims claim = Jwts.parser().setSigningKey(Key).parseClaimsJws(token).getBody();
		User user = userRepo.findByEmailAndPasswordAndUsertype(claim.get(SES_EMAIL).toString(),
				claim.get(SES_PASS).toString(), claim.get(SES_TYPE).toString());
		return user;
	}

}