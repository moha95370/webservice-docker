package fr.greta95.cda;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import fr.greta95.cda.dao.InviteRepository;
import fr.greta95.cda.model.Invite;
@Component
public class HealthCheckBDD implements HealthIndicator {
private final String message_key = "Base de données:";
@Autowired // Injection du repository
private InviteRepository inviteRepository;
 @Override
 public Health health() {
 int errorCode = check();//perform some specific health check
 if (errorCode != 0) {
 return Health.down().withDetail(message_key, "Doublons détectés").build();
 }
 return Health.up().withDetail(message_key, "Pas de doublons détectés").build();
 }
 public int check() {
 //Our logic to check health
 Collection<Invite> doublons = inviteRepository.rechercherDoublons();
 if (doublons.size()==0)
 return 0;
 else return 1;
 }
}