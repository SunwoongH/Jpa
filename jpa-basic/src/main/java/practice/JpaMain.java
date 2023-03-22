package practice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

public class JpaMain {
    private static final Set<Class<?>> entities = Set.of(Member.class);

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        // jpa에서 데이터를 변경하는 모든 task는 트랜잭션 안에서 이루어져야 한다.
        save(entityManagerFactory, sampleDate(1L, "dog"));
        save(entityManagerFactory, sampleDate(2L, "cat"));
        update(entityManagerFactory, Member.class, 1L);
        remove(entityManagerFactory, Member.class, 2L);
        entityManagerFactory.close();
    }

    private static Member sampleDate(Long id, String name) {
        return new Member(id, name);
    }

    private static <T> void save(EntityManagerFactory entityManagerFactory, T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static <T> void update(EntityManagerFactory entityManagerFactory, Class<T> type, Long primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            if (!entities.contains(type)) {
                throw new IllegalArgumentException("not found.");
            }
            T entity = entityManager.find(type, primaryKey);
            sampleUpdateTest(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static <T> void sampleUpdateTest(T entity) {
        if (entity instanceof Member) {
            ((Member) entity).setName("cat");
        }
    }

    private static <T> void remove(EntityManagerFactory entityManagerFactory, Class<T> type, Long primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            if (!entities.contains(type)) {
                throw new IllegalArgumentException("not found.");
            }
            T entity = entityManager.find(type, primaryKey);
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}