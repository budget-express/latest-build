package com.tdavis.be.service.initdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
//import com.tdavis.be.entity.Quote;
import com.tdavis.be.entity.Role;
import com.tdavis.be.entity.User;
import com.tdavis.be.repository.BudgetRepository;
import com.tdavis.be.repository.ProjectRepository;
import com.tdavis.be.repository.QuoteRepository;
import com.tdavis.be.repository.RoleRepository;
import com.tdavis.be.repository.UserRepository;



@Transactional
@Service
public class InitDBService {

	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired QuoteRepository quoteRepository;
	
	@Autowired ProjectRepository projectRepository;
	
	@PostConstruct
	public void init() throws IOException {
		
		if (userRepository != null && userRepository.count() == 0) {
		
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepository.save(roleUser);
			
			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
			
			User userAdmin = new User();
			userAdmin.setEnabled(true);
			userAdmin.setName("admin");
			userAdmin.setFname("admin");
			userAdmin.setLname("admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userAdmin.setAdmin(true);
			userRepository.save(userAdmin);
			
			User userUser = new User();
			userUser.setEnabled(true);
			userUser.setName("user");
			userUser.setFname("User");
			userUser.setLname("user");
			userUser.setPassword(encoder.encode("user"));
			List<Role> uroles = new ArrayList<Role>();
			uroles.add(roleUser);
			userUser.setRoles(uroles);
			userUser.setAdmin(false);
			userRepository.save(userUser);
			
			Project project = new Project();
			project.setName("2016 Financials");
			project.setDescription("2016 Budgets");
			project.setYear("2016");
			project.setEnabled(true);
			List<Budget> pbudget = new ArrayList<Budget>();
			
			Budget budget= new Budget();
			budget.setName("US Server Capacity");
			budget.setDescriptionShort("Additional servers for application growth.");
			budget.setDescriptionLong("");
			budget.setCategory("Datacenter");
			budget.setRegion("US");
			budget.setCriticality("1 - Lights Out");
			budget.setRanking(1);
			budget.setRequested_amount(50000);
			budget.setQ1(15000);
			budget.setQ2(15000);
			budget.setQ3(10000);
			budget.setQ4(10000);
			budget.setYear("2016");
			budgetRepository.save(budget);
			
			Budget budget1= new Budget();
			budget1.setName("Global Backup Refresh");
			budget1.setDescriptionShort("Backups to the Cloud");
			budget1.setDescriptionLong("Eliminate tape backups, and ustilize public cloud for archives.");
			budget1.setCategory("Datacenter");
			budget1.setRegion("Global Corporate Strategy");
			budget1.setCriticality("1 - Lights Out");
			budget1.setRanking(1);
			budget1.setRequested_amount(15000);
			budget1.setQ1(0);
			budget1.setQ2(10000);
			budget1.setQ3(2500);
			budget1.setQ4(2500);
			budget1.setYear("2016");
			budgetRepository.save(budget1);

			Budget budget2= new Budget();
			budget2.setName("US Storage Refresh");
			budget2.setDescriptionShort("Additional servers for application growth.");
			budget2.setDescriptionLong("");
			budget2.setCategory("Datacenter");
			budget2.setRegion("Global Corporate Strategy");
			budget2.setCriticality("1 - Lights Out");
			budget2.setRanking(3);
			budget2.setRequested_amount(25000);
			budget2.setQ1(5000);
			budget2.setQ2(5000);
			budget2.setQ3(10000);
			budget2.setQ4(5000);
			budget2.setYear("2016");
			budgetRepository.save(budget2);

			Budget budget3= new Budget();
			budget3.setName("Yammer PoC");
			budget3.setDescriptionShort("Social networking for internal CBRE.");
			budget3.setDescriptionLong("");
			budget3.setCategory("Software");
			budget3.setRegion("Global Corporate Strategy");
			budget3.setCriticality("1 - Lights Out");
			budget3.setRanking(2);
			budget3.setRequested_amount(10000);
			budget3.setQ1(2500);
			budget3.setQ2(0);
			budget3.setQ3(2500);
			budget3.setQ4(0);
			budget3.setYear("2016");
			budgetRepository.save(budget3);

			Budget budget4= new Budget();
			budget4.setName("US Server Capacity");
			budget4.setDescriptionShort("Additional servers for application growth.");
			budget4.setDescriptionLong("");
			budget4.setCategory("Datacenter");
			budget4.setRegion("US");
			budget4.setCriticality("1 - Lights Out");
			budget4.setRanking(1);
			budget4.setRequested_amount(50000);
			budget4.setQ1(15000);
			budget4.setQ2(15000);
			budget4.setQ3(10000);
			budget4.setQ4(10000);
			budget4.setYear("2015");
			budgetRepository.save(budget4);

			Budget budget5= new Budget();
			budget5.setName("Global Backup Refresh");
			budget5.setDescriptionShort("Backups to the Cloud");
			budget5.setDescriptionLong("Eliminate tape backups, and ustilize public cloud for archives.");
			budget5.setCategory("Datacenter");
			budget5.setRegion("Global Corporate Strategy");
			budget5.setCriticality("1 - Lights Out");
			budget5.setRanking(1);
			budget5.setRequested_amount(15000);
			budget5.setQ1(0);
			budget5.setQ2(10000);
			budget5.setQ3(2500);
			budget5.setQ4(2500);
			budget5.setYear("2015");
			budgetRepository.save(budget5);

			Budget budget6= new Budget();
			budget6.setName("US Storage Refresh");
			budget6.setDescriptionShort("Additional servers for application growth.");
			budget6.setDescriptionLong("");
			budget6.setCategory("Datacenter");
			budget6.setRegion("Global Corporate Strategy");
			budget6.setCriticality("1 - Lights Out");
			budget6.setRanking(3);
			budget6.setRequested_amount(25000);
			budget6.setQ1(5000);
			budget6.setQ2(5000);
			budget6.setQ3(10000);
			budget6.setQ4(5000);
			budget6.setYear("2015");
			budgetRepository.save(budget6);

			Budget budget7= new Budget();
			budget7.setName("Yammer PoC");
			budget7.setDescriptionShort("Social networking for internal CBRE.");
			budget7.setDescriptionLong("");
			budget7.setCategory("Software");
			budget7.setRegion("Global Corporate Strategy");
			budget7.setCriticality("1 - Lights Out");
			budget7.setRanking(2);
			budget7.setRequested_amount(10000);
			budget7.setQ1(2500);
			budget7.setQ2(0);
			budget7.setQ3(2500);
			budget7.setQ4(0);
			budget7.setYear("2015");
			budgetRepository.save(budget7);

			Budget budget8= new Budget();
			budget8.setName("US Server Capacity");
			budget8.setDescriptionShort("Additional servers for application growth.");
			budget8.setDescriptionLong("");
			budget8.setCategory("Datacenter");
			budget8.setRegion("US");
			budget8.setCriticality("1 - Lights Out");
			budget8.setRanking(1);
			budget8.setRequested_amount(50000);
			budget8.setQ1(15000);
			budget8.setQ2(15000);
			budget8.setQ3(10000);
			budget8.setQ4(10000);
			budget8.setYear("2014");
			budgetRepository.save(budget8);

			Budget budget9= new Budget();
			budget9.setName("Global Backup Refresh");
			budget9.setDescriptionShort("Backups to the Cloud");
			budget9.setDescriptionLong("Eliminate tape backups, and ustilize public cloud for archives.");
			budget9.setCategory("Datacenter");
			budget9.setRegion("Global Corporate Strategy");
			budget9.setCriticality("1 - Lights Out");
			budget9.setRanking(1);
			budget9.setRequested_amount(15000);
			budget9.setQ1(0);
			budget9.setQ2(10000);
			budget9.setQ3(2500);
			budget9.setQ4(2500);
			budget9.setYear("2015");
			budgetRepository.save(budget9);

			Budget budget10= new Budget();
			budget10.setName("US Storage Refresh");
			budget10.setDescriptionShort("Additional servers for application growth.");
			budget10.setDescriptionLong("");
			budget10.setCategory("Datacenter");
			budget10.setRegion("Global Corporate Strategy");
			budget10.setCriticality("1 - Lights Out");
			budget10.setRanking(3);
			budget10.setRequested_amount(25000);
			budget10.setQ1(5000);
			budget10.setQ2(5000);
			budget10.setQ3(10000);
			budget10.setQ4(5000);
			budget10.setYear("2015");
			budgetRepository.save(budget10);

			Budget budget11= new Budget();
			budget11.setName("Yammer PoC");
			budget11.setDescriptionShort("Social networking for internal CBRE.");
			budget11.setDescriptionLong("");
			budget11.setCategory("Software");
			budget11.setRegion("Global Corporate Strategy");
			budget11.setCriticality("1 - Lights Out");
			budget11.setRanking(2);
			budget11.setRequested_amount(10000);
			budget11.setQ1(2500);
			budget11.setQ2(0);
			budget11.setQ3(2500);
			budget11.setQ4(0);
			budget11.setYear("2015");
			budgetRepository.save(budget11);

			
			pbudget.add(budget);
			pbudget.add(budget1);
			pbudget.add(budget2);
			pbudget.add(budget3);
			pbudget.add(budget4);
			pbudget.add(budget5);
			pbudget.add(budget6);
			pbudget.add(budget7);
			pbudget.add(budget8);
			pbudget.add(budget9);
			pbudget.add(budget10);
			pbudget.add(budget11);
			project.setBudgets(pbudget);
			projectRepository.save(project);

		}
		
		
	}
	
}
