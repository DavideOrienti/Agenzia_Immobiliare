package it.uniroma3.siw.validator;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Immobile;
import it.uniroma3.siw.service.ImmobileService;

@Component
public class ImmobileValidator implements Validator {

	 @Autowired
	 private ImmobileService immobileService;
	  private static final Logger logger = LoggerFactory.getLogger(ImmobileValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Immobile.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		if (!errors.hasErrors()) {
			 logger.debug("confermato: valori non nulli");
		 if(immobileService.alreadyExist((Immobile)obj)) {
			 logger.debug("e' un duplicato");
	            errors.reject("Immobile.duplicato");
	        }
		}
	}

}