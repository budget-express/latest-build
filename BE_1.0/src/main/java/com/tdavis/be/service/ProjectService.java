package com.tdavis.be.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Project;
import com.tdavis.be.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

		@Autowired
		private ProjectRepository projectRepository;
		
		public Iterable<Project> findAll () {
			
			return projectRepository.findAll();
		}
		
		public Project findById(int id){
			return projectRepository.findById(id);
		}
		
		public void save(Project project) {
			

			projectRepository.save(project);
		}
}
