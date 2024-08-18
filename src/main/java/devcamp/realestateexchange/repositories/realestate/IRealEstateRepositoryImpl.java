package devcamp.realestateexchange.repositories.realestate;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import java.util.Date;
import java.math.BigDecimal;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.DistrictDto;

@Repository
public class IRealEstateRepositoryImpl implements RealEstateRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public List<RealEstateDto> search(String keyword) {
        SearchSession searchSession = Search.session(entityManager);

        List<RealEstateDto> result = searchSession.search(RealEstate.class)
                .select(f -> f.composite(
                    RealEstateDto::fromFields,
                    f.field("id", Integer.class).toProjection(),
                    f.field("title", String.class).toProjection(),
                    f.field("type", Integer.class).toProjection(),
                    f.field("request", Integer.class).toProjection(),
                    f.field("realEstateCode", String.class).toProjection(),
                    f.field("price", BigDecimal.class).toProjection(),
                    f.field("priceUnit", Integer.class).toProjection(),
                    f.field("priceTime", String.class).toProjection(),
                    f.field("acreage", Double.class).toProjection(),
                    f.field("acreageUnit", Integer.class).toProjection(),
                    f.field("bedroom", Integer.class).toProjection(),
                    f.field("verify", Integer.class).toProjection(),
                    f.field("createdAt", Date.class).toProjection(),
                    f.field("customer.id", Integer.class).toProjection()
                    ))
                .where(f -> f.match()
                        .fields("title", "realEstateCode")
                        .matching(keyword))
                .fetchHits(20);

        return result;
    }
}
