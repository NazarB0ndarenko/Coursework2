package lt.viko.bondarenko.entity;

import jakarta.persistence.JoinColumn;
import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "reporter_user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "accused_user_id")
    private User accused;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Evidence> evidences;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
