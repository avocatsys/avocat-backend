package com.avocat.persistence.entity.process;

import com.avocat.persistence.entity.UserApp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "judicial_district")
@AttributeOverride(name = "id", column = @Column(name = "district_id", nullable = false))
public class JudicialDistrict extends BaseProcess {


}
