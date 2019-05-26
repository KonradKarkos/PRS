package requests;

import dtos.PresenceOnLectureDto;
import entities.PresenceOnLecture;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import parameters.Settings;

import java.util.List;

public class PresenceOnLectureRequest {
    public List<PresenceOnLectureDto> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<PresenceOnLectureDto>> response = restTemplate.exchange(
                Settings.URL + "/presenceOnLectures",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PresenceOnLectureDto>>() {
                });
        return response.getBody();
    }

    public PresenceOnLectureDto get(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PresenceOnLectureDto> responseEntity = restTemplate.getForEntity(Settings.URL + "/presenceOnLecture/" + id, PresenceOnLectureDto.class);
        return responseEntity.getBody();
    }

    public PresenceOnLecture save(PresenceOnLecture lecture) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PresenceOnLecture> requestUpdate = new HttpEntity<>(lecture, null);
        ResponseEntity<PresenceOnLecture> response = restTemplate.exchange(Settings.URL + "/presenceOnLecture", HttpMethod.POST, requestUpdate, PresenceOnLecture.class);
        return response.getBody();
    }

    public void delete(PresenceOnLecture lecture) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<PresenceOnLecture> requestUpdate = new HttpEntity<>(lecture, null);
        restTemplate.exchange(Settings.URL + "/presenceOnLecture/" + lecture.getId(), HttpMethod.DELETE, requestUpdate, PresenceOnLecture.class);
    }
}