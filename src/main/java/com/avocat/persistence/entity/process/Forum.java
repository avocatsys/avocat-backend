package com.avocat.persistence.entity.process;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forums")
@AttributeOverride(name = "id", column = @Column(name = "forum_id", nullable = false))
public class Forum extends BaseProcess{
}
