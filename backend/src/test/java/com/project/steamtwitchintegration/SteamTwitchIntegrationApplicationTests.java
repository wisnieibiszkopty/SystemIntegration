package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dto.AuthenticationResponse;
import com.project.steamtwitchintegration.dto.RegisterRequest;
import com.project.steamtwitchintegration.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("test")
class SteamTwitchIntegrationApplicationTests {

	@Autowired
	private AuthService authService;

	@Test
	void contextLoads() {
	}

	// works!!!
	@Test
	void testAuth(){
		RegisterRequest request1 = new RegisterRequest(
				"Makima",
				"makima@pollub.pl",
				"makima123");
		AuthenticationResponse response = authService.register(request1);

		System.out.println(response.getToken());

		assertThat(response.getToken()).isNotNull();
	}

}
