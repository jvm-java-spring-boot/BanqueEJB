package metier.session;

import java.util.List;

import javax.ejb.Remote;

import metier.entities.Compte;
@Remote
public interface IBanqueRemote {  //méthodes accessibles à distance via un client java si l'appli a besoin d'un client "lourd" sinon webservices
  public void addCompte(Compte c);
  public List<Compte> getAllComptes();
  public Compte getCompte(Long code);
  public void verser(double mt, Long code);
  public void retirer(double mt, Long code);
  public void virement(double mt, Long cpte1, Long cpte2);
  public void updateCompte(Compte c);
  public void supprimerCompte(Long code);
}
