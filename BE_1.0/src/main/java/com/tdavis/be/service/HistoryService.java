package com.tdavis.be.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.History;
import com.tdavis.be.entity.Project;
import com.tdavis.be.repository.HistoryRepository;

@Service
@Transactional
public class HistoryService {

		@Autowired
		private HistoryRepository historyRepository;
		
		public Iterable<History> findAll () {
			
			return historyRepository.findAll();
		}
		
		
		public void save(History history) {
			

			historyRepository.save(history);
		}
		
		public void update(Project project, History history) {
			List<History> historys = project.getHistorys();
			historys.add(history);
			project.setHistorys(historys);
		}
		
		
}
