package com.spring.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.spring.security.entity.Department;

//@Service
public class ThreadSafeService {
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	public void run() {
		ExecutorService es = Executors.newFixedThreadPool(2);
		List<Future<?>> futures = new ArrayList<>();
		Future<?> f1 = es.submit(() -> {
			return runTask(entityManagerFactory, "1", "test 1");
		});
		Future<?> f2 = es.submit(() -> {
			return runTask(entityManagerFactory, "2", "test 2");
		});
		futures.add(f1);
		futures.add(f2);
		try {
			for (Future<?> future : futures) {
				future.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		nativeQuery(entityManagerFactory, "Select * from MyEntity");
		es.shutdown();
		entityManagerFactory.close();
	}

	private static String runTask(EntityManagerFactory emf, String id, String str) {
		System.out.printf("persisting id: %s str:%s%n", id, str);
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(createNewMyEntity(id, str));
		em.getTransaction().commit();
		em.close();

		return "done executing id: " + id;
	}

	private static Department createNewMyEntity(String id, String str) {
		Department entity = new Department();
		entity.setId(id);
		entity.setDeptName(str);
		return entity;
	}

	private static void nativeQuery(EntityManagerFactory emf, String s) {
		System.out.println("--------\n" + s);
		EntityManager em = emf.createEntityManager();
		Query query = em.createNativeQuery(s);
		@SuppressWarnings("unchecked")
		List<Department> list = (List<Department>) query.getResultList();
		for (Object o : list) {
			System.out.println(Arrays.toString((Object[]) o));
		}
	}
}
