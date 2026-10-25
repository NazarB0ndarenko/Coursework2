package lt.viko.bondarenko.service;

import lt.viko.bondarenko.entity.Evidence;
import org.springframework.web.multipart.MultipartFile;

public interface EvidenceService {

    Evidence saveEvidence(MultipartFile file);
}
