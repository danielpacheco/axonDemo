package com.shaqueiq.arcadedbdemo;

import com.shaqueiq.arcadedbdemo.cqrs.AffiliateCommand;
import com.shaqueiq.arcadedbdemo.cqrs.AffiliateEvent;
import com.shaqueiq.arcadedbdemo.cqrs.aggregate.HospitalAggregate;
import com.shaqueiq.arcadedbdemo.model.Hospital;
import com.shaqueiq.arcadedbdemo.model.Physician;
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
		Physician danielP = new Physician("Daniel P");
		Hospital mayo = new Hospital("Mayo");
		fixture.givenNoPriorActivity()
				.when(new AffiliateCommand(id, danielP, mayo))
				.expectEvents(new AffiliateEvent(id, danielP, mayo));
	}

}
