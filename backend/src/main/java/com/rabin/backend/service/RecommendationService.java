    package com.rabin.backend.service;

    import com.rabin.backend.dto.response.EventResponseDTO;
    import com.rabin.backend.model.Event;
    import com.rabin.backend.repo.EventRepository;
    import com.rabin.backend.repo.EventTagMapRepository;
    import com.rabin.backend.repo.UserInterestRepository;
    import com.rabin.backend.util.ContentScoreCalculator;
    import com.rabin.backend.util.Haversine;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Set;
    import java.util.stream.Collectors;

    @Service
    public class RecommendationService {

        @Autowired
        private EventRepository eventRepository;

        @Autowired
        private UserInterestRepository userInterestRepository;

        @Autowired
        private EventTagMapRepository eventTagMapRepository;

        /**
         * Returns a list of recommended events for a user based on interest and distance.
         *
         * @param userId  The ID of the user
         * @param userLat User's latitude
         * @param userLon User's longitude
         * @param limit   Maximum number of events to return
         * @return List of EventResponseDTO sorted by relevance
         */
        public List<EventResponseDTO> getRecommendedEvents(Long userId, double userLat, double userLon, int limit) {

            // Fetch user interests
            Set<Long> userTagIds = Set.copyOf(userInterestRepository.findTagIdsByUserId(userId));

            // Fetch active events
            List<Event> allEvents = eventRepository.findActiveEvents();

            List<EventResponseDTO> scoredEvents = new ArrayList<>();

            for (Event event : allEvents) {
                // Fetch event tags
                List<Long> eventTagIds = eventTagMapRepository.findTagIdsByEventId(event.getId());

                // Compute scores
                double contentScore = ContentScoreCalculator.contentScore(userTagIds, Set.copyOf(eventTagIds));
                double distanceScore = 1 / (1 + Haversine.distance(userLat, userLon, event.getLatitude(), event.getLongitude()));

                // Map event to DTO
                EventResponseDTO dto = new EventResponseDTO();
                dto.setId(event.getId());
                dto.setTitle(event.getTitle());
                dto.setDescription(event.getDescription());
                dto.setEventImageUrl(event.getEventImageUrl());
                dto.setLatitude(event.getLatitude());
                dto.setLongitude(event.getLongitude());
                dto.setFinalScore(contentScore + distanceScore);

                scoredEvents.add(dto);
            }

            // Sort by finalScore descending and limit
            return scoredEvents.stream()
                    .sorted(Comparator.comparingDouble(EventResponseDTO::getFinalScore).reversed())
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        /**
         * Returns all active events sorted by event date descending for "Explore" feature.
         *
         * @return List of EventResponseDTO
         */
        public List<EventResponseDTO> exploreAllEvents() {
            List<Event> allEvents = eventRepository.findActiveEvents();

            return allEvents.stream()
                    .sorted(Comparator.comparing(Event::getEventDate).reversed())
                    .map(event -> {
                        EventResponseDTO dto = new EventResponseDTO();
                        dto.setId(event.getId());
                        dto.setTitle(event.getTitle());
                        dto.setDescription(event.getDescription());
                        dto.setEventImageUrl(event.getEventImageUrl());
                        dto.setLatitude(event.getLatitude());
                        dto.setLongitude(event.getLongitude());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
    }
