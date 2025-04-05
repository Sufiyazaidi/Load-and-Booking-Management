package com.example.Load_Management.Entity;
import com.example.Load_Management.Enum.LoadStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.*;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Shipper ID is required")
    private String shipperId;

    @Embedded
    private Facility facility;

    @NotBlank(message = "Product Type is required")
    private String productType;

    @NotBlank(message = "Truck Type is required")
    private String truckType;

    @Min(value = 1, message = "At least 1 truck is required")
    private int noOfTrucks;

    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    private double weight;

    private String comment;
    private LocalDateTime datePosted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoadStatus status = LoadStatus.POSTED;
    public LoadStatus getStatus() {
        return status;
    }
    public void setStatus(LoadStatus status) {
        this.status = status;
    }
}
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class Facility {
    @NotBlank(message = "Loading Point is required")
    private String loadingPoint;

    @NotBlank(message = "Unloading Point is required")
    private String unloadingPoint;

    @Future(message = "Loading Date must be in the future")
    private LocalDateTime loadingDate;

    @Future(message = "Unloading Date must be in the future")
    private LocalDateTime unloadingDate;
}




