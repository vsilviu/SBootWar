package generic.module.two.entity;



import generic.module.two.common.IConstants;
import generic.module.two.entity.decorator.Identificable;
import generic.module.two.entity.decorator.Ownable;
import generic.module.two.entity.decorator.Securizable;
import generic.module.two.entity.decorator.SoftDelete;

import javax.persistence.*;

/**
 * Created by mitziuro on 8/24/16.
 */
@Entity
@Table(name="COMMUNITY")
@Securizable
public class Community extends Auditable implements Identificable, Ownable, SoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name= "DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name="FK_USER")
    private User owner;

    @Column(name = "DELETED")
    private Boolean deleted = IConstants.NOT_DELETED;

    @Override
    public User getOwner() {
        return owner;
    }

    @Override
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Boolean isDeleted() {
        return this.deleted;
    }
}
