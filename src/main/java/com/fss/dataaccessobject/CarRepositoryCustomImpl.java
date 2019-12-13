package com.fss.dataaccessobject;

import com.fss.domainobject.CarDO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CarRepositoryCustomImpl implements CarRepositoryCustom
{
    @PersistenceContext
    EntityManager entityManager;


    /**
     * Method to scale any size of Query Params
     *
     * @param searchParameters
     * @return
     */
    @Override
    public List<CarDO> findCars(Map<String, String> searchParameters)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CarDO> criteriaQuery = criteriaBuilder.createQuery(CarDO.class);

        Root<CarDO> car = criteriaQuery.from(CarDO.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchParameters.containsKey("ratings"))
        {

            predicates.add(criteriaBuilder.ge(
                car.get("rating"),
                Integer.parseInt(searchParameters.get("rating"))));
        }

        if (searchParameters.containsKey("convertable"))
        {
            predicates.add(criteriaBuilder.equal(
                car.get("convertible"),
                Boolean.parseBoolean(searchParameters.get("convertible"))));
        }

        if (searchParameters.containsKey("license"))
        {
            predicates.add(criteriaBuilder.equal(car.get("licensePlate"), searchParameters.get("license")));
        }

        predicates.add(criteriaBuilder.equal(car.get("deleted"), Boolean.FALSE));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<CarDO> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
