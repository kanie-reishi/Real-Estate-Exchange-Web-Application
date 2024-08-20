package devcamp.realestateexchange.services.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.projections.VideoProjection;
import devcamp.realestateexchange.repositories.media.IVideoRepository;

@Service
public class VideoService {
    @Autowired
    private IVideoRepository videoRepository;
    public VideoProjection getVideoById(Integer id) {
        return videoRepository.getVideoById(id);
    }
    public Page<VideoProjection> getVideos(Pageable pageable) {
        return videoRepository.findAllProjections(pageable);
    }
    public List<VideoProjection> getVideosByRealEstateId(Integer realEstateId) {
        return videoRepository.findUrlsByRealEstateId(realEstateId);
    }
    public List<VideoProjection> getVideosByProjectId(Integer projectId) {
        return videoRepository.findVideoUrlsByProjectId(projectId);
    }
}
