    package com.rabin.backend.controller;

    import com.rabin.backend.dto.response.EventResponseDTO;
    import com.rabin.backend.dto.response.GenericApiResponse;
    import com.rabin.backend.service.RecommendationService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;

    @RestController
    @RequestMapping("/api/events")
    @Tag(name = "Event Management", description = "APIs for exploring and recommending events")
    public class EventController {

        @Autowired
        private RecommendationService recommendationService;

        @Operation(
                summary = "Get recommended events for a user",
                description = "Returns a list of events personalized based on user's interests and distance"
        )

        @GetMapping("/recommendations")
        public ResponseEntity<GenericApiResponse<List<EventResponseDTO>>> getRecommendations(
                @RequestParam Long userId,
                @RequestParam double latitude,
                @RequestParam double longitude,
                @RequestParam(defaultValue = "10") int limit){

            List<EventResponseDTO> events = recommendationService.getRecommendedEvents(userId,latitude,longitude,limit);
            GenericApiResponse<List<EventResponseDTO>> response  = new GenericApiResponse<>(
                    true,
                    "Recommended events fetched successfully",
                    events,
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }

        @Operation(
                summary = "Explore all active events",
                description = "Returns a list of all active events sorted by event date"
        )
        @GetMapping("/explore")
            public ResponseEntity<GenericApiResponse<List<EventResponseDTO>>> exploreEvents(){
            List<EventResponseDTO> events = recommendationService.exploreAllEvents();
            GenericApiResponse<List<EventResponseDTO>> response = new GenericApiResponse<>(
                    true,
                    "All active events fetched successfully",
                    events,
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        }
    }
