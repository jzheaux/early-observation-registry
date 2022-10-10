package com.example.fails;

import com.example.shared.Advised;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FailsApplicationTests {

	@TestConfiguration
	static class MockObservationHandlerConfig {
		private final ObservationHandler<Observation.Context> handler = mock(ObservationHandler.class);

		MockObservationHandlerConfig() {
			given(this.handler.supportsContext(any())).willReturn(true);
		}

		@Bean
		ObservationHandler<Observation.Context> handler() {
			return this.handler;
		}
	}

	@Autowired
	Advised advised;

	@Autowired
	ObservationHandler<Observation.Context> handler;

	@Test
	void fails() {
		this.advised.advised();
		verify(this.handler).onStart(any());
	}

}
