package com.balceda.apps.platziteachers.dao.impl;

import java.util.Iterator;
import java.util.List;

import com.balceda.apps.platziteachers.dao.AbstractSession;
import com.balceda.apps.platziteachers.dao.exception.DAOException;
import com.balceda.apps.platziteachers.dao.interfaces.TeacherDAO;
import com.balceda.apps.platziteachers.model.Teacher;
import com.balceda.apps.platziteachers.model.TeacherSocialMedia;

public class TeacherDAOImpl extends AbstractSession implements TeacherDAO {

	public TeacherDAOImpl() {
	}

	@Override
	public void save(Teacher t) throws DAOException {
		getSession().persist(t);
	}

	@Override
	public List<Teacher> findAll() throws DAOException {
		return (List<Teacher>) getSession().createQuery("from Teacher").list();
	}

	@Override
	public void deleteById(Long id) throws DAOException {
		Teacher teacher = findById(id);
		if (teacher != null) {
			Iterator<TeacherSocialMedia> iterator = teacher.getTeacheSocialMedias().iterator();
			while (iterator.hasNext()) {
				TeacherSocialMedia tsm = (TeacherSocialMedia) iterator.next();
				iterator.remove();
				getSession().delete(tsm);
			}
			teacher.getTeacheSocialMedias().clear();
			getSession().delete(teacher);
		}
	}

	@Override
	public void update(Teacher t) throws DAOException {
		getSession().update(t);
	}

	@Override
	public Teacher findById(long id) throws DAOException {
		return (Teacher) getSession().get(Teacher.class, id);
	}

	@Override
	public Teacher findByName(String name) throws DAOException {
		return (Teacher) getSession().createQuery("from Teacher where name = :name")
				.setParameter("name", name)
				.uniqueResult();
	}

}
