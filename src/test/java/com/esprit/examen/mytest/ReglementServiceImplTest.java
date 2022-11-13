package com.esprit.examen.mytest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esprit.examen.entities.Operateur;
import com.esprit.examen.entities.Reglement;
import com.esprit.examen.services.IReglementService;

import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ReglementServiceImplTest {
	@Autowired
	IReglementService reglementService;
	
	@Test
	public void testAddReglement() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Reglement reg=new Reglement();
		reg.setDateReglement(dateFormat.parse("28/09/2000"));
		reg.setMontantPaye(100);
		reg.setMontantRestant(50);
		reg.setPayee(true);
		Reglement savedReglement=reglementService.addReglement(reg);
		assertNotNull(savedReglement.getDateReglement());
		
	} 
	
	
	
	@Test
	public void testRetrieveAllReglement() throws ParseException  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Reglement> reglements = reglementService.retrieveAllReglements();
		int expected = reglements.size();
		Reglement reg=new Reglement();
		reg.setDateReglement(dateFormat.parse("28/09/2000"));
		reg.setMontantPaye(100);
		reg.setMontantRestant(50);
		reg.setPayee(true);
		Reglement savedReglement=reglementService.addReglement(reg);
		assertEquals(expected + 1, reglementService.retrieveAllReglements().size());

	}
}
