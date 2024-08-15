package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Skills;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.repository.SkillsRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private final UserRepository userRepository;
    private SubscriberRepository subscriberRepository;
    private SkillsRepository skillsRepository;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillsRepository skillsRepository, UserRepository userRepository) {
        this.subscriberRepository = subscriberRepository;
        this.skillsRepository = skillsRepository;
        this.userRepository = userRepository;
    }

    public Subscriber handleCreate(Subscriber subscriber) {
        if (this.subscriberRepository.existsByEmail(subscriber.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        Subscriber newSubscriber = new Subscriber();
        newSubscriber.setName(subscriber.getName());
        newSubscriber.setEmail(subscriber.getEmail());
        List<Long> skillID = subscriber.getSkills().stream().map(item -> item.getId()).toList();
        List<Skills> skillsList = this.skillsRepository.findByIdIn(skillID);
        newSubscriber.setSkills(skillsList);
        return subscriberRepository.save(newSubscriber);

    }

    public Subscriber handleUpdate(Subscriber subscriber) {
        Optional<Subscriber> currentSubscriber = this.subscriberRepository.findById((int) subscriber.getId());
        if (!currentSubscriber.isPresent()) {
            throw new RuntimeException("Subscriber not found");
        }

        List<Long> skillID = subscriber.getSkills().stream().map(item -> item.getId()).toList();
        List<Skills> skillsList = this.skillsRepository.findByIdIn(skillID);
        currentSubscriber.get().setSkills(skillsList);
        return subscriberRepository.save(currentSubscriber.get());

    }
}
