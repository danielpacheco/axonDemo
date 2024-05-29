package com.poc.axondemo;

import com.poc.axondemo.cqrs.command.AffiliateCommand;
import com.poc.axondemo.cqrs.event.AffiliateEvent;
import com.poc.axondemo.cqrs.aggregate.HospitalAggregate;
import com.poc.axondemo.model.Constants;
import com.poc.axondemo.model.Hospital;
import com.poc.axondemo.model.Physician;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ArcadedbDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	private FixtureConfiguration<HospitalAggregate> fixture;

	@BeforeEach
	public void setUp() {
		fixture = new AggregateTestFixture<>(HospitalAggregate.class);
	}

	@Test
	public void testAffiliation() {
		String id = UUID.randomUUID().toString();
		Physician danielP = new Physician(Constants.DANIEL_P);
		Hospital mayo = new Hospital("Mayo");
		fixture.givenNoPriorActivity()
				.when(new AffiliateCommand(id, danielP, mayo))
				.expectEvents(new AffiliateEvent(id, danielP, mayo));
	}

}
