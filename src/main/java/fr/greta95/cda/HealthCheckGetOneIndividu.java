package fr.greta95.cda;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.greta95.cda.model.Invite;

@Component
public class HealthCheckGetOneIndividu implements HealthIndicator {
	@Override
	public Health health() {
		RestTemplate restTemplate = new RestTemplate();
		String message_key = "URL par défaut:";
		Invite uninvite = restTemplate.getForObject("http://localhost:8080/defaut", Invite.class);
		if (uninvite.getNom().equals("Palmer") && uninvite.getPrenom().equals("Jack")) {
			return Health.up().withDetail(message_key, "fonctionne bien").build();
		} else
			return Health.down().withDetail(message_key, "erreur").build();
	}
}
