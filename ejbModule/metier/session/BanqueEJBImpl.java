package metier.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import metier.entities.Compte;
@Stateless(name="BK")     //creer une seule instance pr tous, @stateful pr chaque client il y aura une instance, et @singleton une seule instance peu importe le nombres de clients
public class BanqueEJBImpl implements IBanqueLocal, IBanqueRemote{
    @PersistenceContext(unitName="UP_BANQUE") //utilise l'unite de persistence UP_BANQUE qui est dans persistence.xml (si plusieurs bdd il faut differents em)
	private EntityManager em;
	@Override
	public void addCompte(Compte c) {
	   em.persist(c);
	}

	@Override
	public List<Compte> getAllComptes() {
		Query req=em.createQuery("select c from Compte c where c.active=true");
		return req.getResultList();
	}

	@Override
	public Compte getCompte(Long code) {
		Compte cp=em.find(Compte.class, code);
		if(cp==null) throw new RuntimeException("Compte introuvable");
		return cp;
	}

	@Override
	public void verser(double mt, Long code) {
		Compte cp=getCompte(code);
		cp.setSolde(cp.getSolde()+mt);
		em.persist(cp);
	}

	@Override
	public void retirer(double mt, Long code) {
		Compte cp=getCompte(code);
		cp.setSolde(cp.getSolde()-mt);
		
	}

	@Override
	public void virement(double mt, Long cpte1, Long cpte2) { //separation du code du code technique=>les transactions sont gérées implicitement par EJB
		retirer(mt, cpte1);
		verser(mt, cpte2);
	}

	@Override
	public void updateCompte(Compte c) {
		em.merge(c);
		
	}

	@Override
	public void supprimerCompte(Long code) {
		Compte cp=getCompte(code);
		em.remove(cp);
		
	}

}
